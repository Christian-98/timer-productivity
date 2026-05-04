package it.christian.timer.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import it.christian.timer.entity.timerInterval.TimerInterval;
import it.christian.timer.entity.timerSession.TimerSession;
import it.christian.timer.support.Constants;
import it.christian.timer.time.TimeProvider;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

/**
 * Service class for managing timer actions such as starting, stopping,
 * resuming, and ending timer sessions. This class interacts with the database
 * to persist and retrieve timer session and interval data.
 */

@Stateless
public class TimerActionService {

	@PersistenceContext(name = "TimerPU", unitName = "TimerPU")
	private EntityManager em;

	@Inject
	private TimeProvider timerProvider;

	/*
	 * Starts a timer session by creating a new TimerSession entity, setting its
	 * start time to the current time, and persisting it to the database.
	 */
	public TimerSession startTime(String nameTimer) {
		TimerSession ts = new TimerSession();

		ts.setStartDate(LocalDateTime.now());
		ts.setCompleted(Constants.TIMER_STATUS_ACTIVE);
		ts.setName(nameTimer);
		em.persist(ts);
		em.flush(); // Ensure the entity is persisted and the ID is generated

		TimerInterval ti = new TimerInterval();
		ti.setSessionID(ts.getId());
		ti.setStartTime(LocalDateTime.now());
		em.persist(ti);

		return ts;
	}

	/*
	 * Stops a timer session by finding the TimerSession entity with the specified
	 * ID, setting its end time to the current time, marking it as completed, and
	 * merging the updated entity back into the database.
	 * 
	 * @param id the ID of the timer session to be stopped
	 * 
	 * @return the updated TimerSession entity after stopping the timer, or null if
	 * the timer session with the specified ID was not found or is already completed
	 */
	public TimerInterval stopTimer(int id) {
		try {
			TimerInterval ti = em
					.createQuery("SELECT t FROM TimerInterval t WHERE t.sessionID = :id AND t.endDate IS NULL",
							TimerInterval.class)
					.setParameter("id", id).getSingleResult();

			ti.setEndTime(LocalDateTime.now());
			TimerInterval mergedInterval = em.merge(ti);

			TimerSession ts = em.find(TimerSession.class, id);
			if (ts != null) {
				ts.setDurationSeconds(calculateSessionDurationSeconds(id, false));
				em.merge(ts);
			}

			return mergedInterval;

		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * * Resumes a timer session by creating a new TimerInterval entity with the
	 * specified session ID, setting its start time to the current time, and
	 * persisting it to the database.
	 * 
	 * @param id the ID of the timer session to be resumed
	 * 
	 * @return void
	 */
	public void resumeTimer(int id) {
		TimerInterval ti = new TimerInterval();

		ti.setSessionID(id);
		ti.setStartTime(LocalDateTime.now());

		em.persist(ti);
	}

	/*
	 * Ends a timer session by stopping the timer, calculating the total duration of
	 * all timer intervals associated with the session, updating the TimerSession
	 * entity with the total duration and marking it as completed, and merging the
	 * updated entity back into the database.
	 * 
	 * @param id the ID of the timer session to be ended
	 * 
	 * @return the updated TimerSession entity after ending the timer session, or
	 * null if the timer session with the specified ID was not found or is already
	 * completed
	 */
	public TimerSession endTimer(int id) {

		stopTimer(id);

		TimerSession ts = em.find(TimerSession.class, id);

		ts.setDurationSeconds(calculateSessionDurationSeconds(id, false));
		ts.setEndDate(timerProvider.now());
		ts.setCompleted(Constants.TIMER_STATUS_CLOSE);
		em.merge(ts);

		return ts;
	}

	/*
	 * Deletes a timer session by finding the TimerSession entity with the specified
	 * ID and removing it from the database.
	 * 
	 * @param id the ID of the timer session to be deleted
	 * 
	 * @return void
	 */
	public void deleteTimer(int id) {
		TimerSession ts = em.find(TimerSession.class, id);
		if (ts != null) {
			em.remove(ts);
		}
	}

	/*
	 * Adds a note to a timer session by finding the TimerSession entity with the
	 * specified ID, setting its note property to the provided note, and merging the
	 * updated entity back into the database.
	 * 
	 * @param id the ID of the timer session to which the note should be added
	 * 
	 * @param note the note text to be added to the timer session
	 * 
	 * @return void
	 */
	public void addNote(int id, String note) {
		TimerSession ts = em.find(TimerSession.class, id);
		if (ts == null) {
			throw new EntityNotFoundException("Timer session with ID " + id + " not found.");
		}
		ts.setNote(note);
	}

	/**
	 * Retrieves a list of all TimerSession entities from the database.
	 * 
	 * @return a List of TimerSession entities representing all timer sessions in
	 *         the database
	 */
	public List<TimerSession> findAll() {
		return em.createNamedQuery("TimerSession.findAll", TimerSession.class).getResultList();
	}

	/*
	 * Calculates the total duration in seconds of all timer intervals associated
	 * with a given timer session ID. It retrieves all TimerInterval entities with
	 * the specified session ID, and for each interval, it calculates the duration
	 * between the start and end times. If an interval has an open end time (i.e.,
	 * end time is null), it can optionally include the duration from the start time
	 * to the current time based on the includeOpenInterval parameter.
	 * 
	 * @param sessionId the ID of the timer session for which to calculate the total
	 * duration of intervals
	 * 
	 * @param includeOpenInterval a boolean flag indicating whether to include the
	 * duration of any open intervals (intervals with a null end time) in the total
	 * duration calculation. If true, open intervals will be included; if false,
	 * they will be ignored.
	 * 
	 * @return the total duration in seconds of all timer intervals associated with
	 * the specified timer session ID, including open intervals if
	 * includeOpenInterval is true
	 */
	private long calculateSessionDurationSeconds(int sessionId, boolean includeOpenInterval) {
		return em.createQuery("SELECT t FROM TimerInterval t WHERE t.sessionID = :id", TimerInterval.class)
				.setParameter("id", sessionId).getResultList().stream().filter(t -> t.getStartTime() != null)
				.mapToLong(t -> {
					LocalDateTime end = t.getEndTime();
					if (end == null && !includeOpenInterval) {
						return 0L;
					}
					if (end == null) {
						end = timerProvider.now();
					}
					return Duration.between(t.getStartTime(), end).getSeconds();
				}).sum();
	}
}
