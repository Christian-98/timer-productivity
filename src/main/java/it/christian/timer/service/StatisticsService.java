package it.christian.timer.service;

import java.time.LocalDateTime;
import java.util.List;

import it.christian.timer.dto.StatisticsDashboardDTO;
import it.christian.timer.dto.TimerTDO;
import it.christian.timer.entity.timerSession.TimerSession;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/*
 * Service class for handling statistics-related business logic.
 * This class is annotated with @Stateless, indicating that it is a stateless EJB (Enterprise Java Bean).
 * This means that instances of this class do not maintain any conversational state with clients.
 * Each method call is independent, and the container can create and destroy instances as needed to handle requests efficiently.
 * 
 * The implementation of the methods to perform various statistics-related operations will be added to this class.
 */

@Stateless
public class StatisticsService {

	@PersistenceContext(name = "TimerPU", unitName = "TimerPU")
	private EntityManager em;

	/*
	 * Retrieves the dashboard statistics.
	 * 
	 * @return a StatisticsDashboardDTO object containing the dashboard statistics
	 */
	public StatisticsDashboardDTO getDashboard() {
		StatisticsDashboardDTO dto = new StatisticsDashboardDTO();

		Long totalSessions = em.createQuery("SELECT COUNT(t) FROM TimerSession t", Long.class).getSingleResult();

		Long totalSeconds = em.createQuery("SELECT COALESCE(SUM(t.durationSeconds),0) FROM TimerSession t", Long.class)
				.getSingleResult();

		Double average = em.createQuery("SELECT COALESCE(AVG(t.durationSeconds),0) FROM TimerSession t", Double.class)
				.getSingleResult();

		Long best = em.createQuery("SELECT COALESCE(MAX(t.durationSeconds),0) FROM TimerSession t", Long.class)
				.getSingleResult();

		dto.setTotalSessions(totalSessions);
		dto.setTotalSeconds(totalSeconds);
		dto.setAverageSeconds(average.longValue());
		dto.setBestSessionSeconds(best);

		return dto;
	}

	/*
	 * Retrieves statistics for a specific period defined by the start and end
	 * dates.
	 * 
	 * @param startDate the start date of the period for which to retrieve
	 * statistics
	 * 
	 * @param endDate the end date of the period for which to retrieve statistics
	 * 
	 * @return a StatisticsDashboardDTO object containing the statistics for the
	 * specified period
	 */
	public StatisticsDashboardDTO getPeriod(LocalDateTime startDate, LocalDateTime endDate) {
		StatisticsDashboardDTO dto = new StatisticsDashboardDTO();

		Object[] row = em.createQuery(
				"SELECT COUNT(t), COALESCE(SUM(t.durationSeconds),0), COALESCE(AVG(t.durationSeconds),0), COALESCE(MAX(t.durationSeconds),0) "
						+ "FROM TimerSession t "
						+ "WHERE t.endDate IS NOT NULL AND t.startDate >= :startDate AND t.endDate <= :endDate",
				Object[].class).setParameter("startDate", startDate).setParameter("endDate", endDate).getSingleResult();

		dto.setTotalSessions(((Long) row[0]));
		dto.setTotalSeconds(((Long) row[1]));
		dto.setAverageSeconds(((Double) row[2]).longValue());
		dto.setBestSessionSeconds(((Long) row[3]));

		// Retrieves the detailed list of sessions for the specified period
		List<TimerSession> sessions = em.createQuery("SELECT t FROM TimerSession t "
				+ "WHERE t.endDate IS NOT NULL AND t.startDate >= :startDate AND t.endDate <= :endDate ORDER BY t.durationSeconds DESC",
				TimerSession.class)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getResultList();

		dto.setSessions(mapper(sessions));
		return dto;
	}

	/*
	 * Retrieves a summary of statistics.
	 * 
	 * @return a StatisticsSummary object containing the summary of statistics
	 */
	public List<TimerTDO> getSummary() {
		List<TimerSession> sessions = em.createNamedQuery("TimerSession.findAll", TimerSession.class).getResultList();
		return mapper(sessions);
	}
	
	/*
	 * Maps a list of TimerSession entities to a list of TimerTDO objects.
	 * 
	 * @param sessions the list of TimerSession entities to be mapped
	 * 
	 * @return a List of TimerTDO objects representing the mapped TimerSession
	 * entities
	 */
	private List<TimerTDO> mapper(List<TimerSession> sessions) {
		return sessions.stream().map(s -> {
			TimerTDO tdo = new TimerTDO();
			tdo.setTimerId(s.getId());
			tdo.setName(s.getName());
			tdo.setDurationSeconds(s.getDurationSeconds());
			tdo.setStatus(s.getCompleted());
			tdo.setNote(s.getNote());
			return tdo;
		}).toList();
	}
}
