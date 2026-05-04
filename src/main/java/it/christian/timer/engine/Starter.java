package it.christian.timer.engine;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import it.christian.timer.entity.timerSession.TimerSession;
import it.christian.timer.support.Constants;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;

public class Starter {

	/**
	 * <p>
	 * This ejb name
	 * </p>
	 */
	public static final String EJB_NAME = "Starter";

	/**
	 * Configuration file name.
	 */
	private static final String CONF_FILE_NAME = "timerApp.conf";

	/**
	 * Configuration properties.
	 */
	private static Properties config = new Properties();

	/**
	 * Entity manager for database operations, injected by the container.
	 */
	@PersistenceContext(name = "TimerPU", unitName = "TimerPU")
	private EntityManager em;

	@Resource
	private UserTransaction userTransaction;

	/**
	 * Initializes the configuration by loading it from the specified file.
	 */
	@PostConstruct
	public void init() {
		try (var in = getClass().getClassLoader().getResourceAsStream(CONF_FILE_NAME)) {
			if (in == null) {
				System.out.println(" - Configuration file not found : " + CONF_FILE_NAME);
				return;
			}
			synchronized (config) {
				config.clear();
				config.load(in);
			}
			System.out.println(" - Configuration file loaded : " + CONF_FILE_NAME);
		} catch (Exception e) {
			System.out.println(" - Error loading configuration file : " + CONF_FILE_NAME);
			e.printStackTrace();
		}
	}

	/**
	 * Scheduled method that executes every minute at second 1. It checks if the
	 * timer scheduler is enabled and, if so, it performs the necessary actions.
	 */
	@Schedule(hour = "*", minute = "*/1", second = "1", persistent = false)
	private void execute() {
		boolean enabled = Boolean.parseBoolean(config.getProperty("timer.scheduler.enabled", "false"));
		if (!enabled) {
			System.out.println(" - Timer scheduler is disabled.");
			return;
		}

		System.out.println(" - Timer scheduler is executing...");

		int autocClosTimerSessions = Integer.parseInt(config.getProperty("timer.scheduler.autoCloseSessions", "0"));

		if (autocClosTimerSessions > 0) {
			System.out.println(" - Auto-closing timer sessions older than " + autocClosTimerSessions + " minutes.");
			closeOpenTimerSessions(autocClosTimerSessions);
		}
	}

	/*
	 * Closes open timer sessions that have been active for longer than the
	 * specified number of minutes. It finds all open timer sessions that started
	 * more than the specified number of minutes ago, and for each session, it
	 * checks if it has been open for longer than the threshold. If so, it sets the
	 * end time to the current time, calculates the duration in seconds, marks the
	 * session as completed, and merges the updated session back into the database.
	 * 
	 * @param autocClosTimerSessions the number of minutes after which open timer
	 * sessions should be auto-closed
	 */
	private void closeOpenTimerSessions(int autocClosTimerSessions) {
		// Find all open timer sessions that started more than the specified number of
		// minutes ago
		List<TimerSession> openSessions = em
				.createQuery("SELECT t FROM TimerSession t WHERE t.endDate IS NULL AND t.startDate < :threshold",
						TimerSession.class)
				.setParameter("threshold", LocalDateTime.now().minusMinutes(autocClosTimerSessions)).getResultList();

		long now = System.currentTimeMillis();
		long thresholdMillis = autocClosTimerSessions * 60 * 1000L;

		for (TimerSession session : openSessions) {
			System.out.println(" - Auto-closing timer session with ID: " + session.getId());
			if (session.getStartDate() == null) {
				System.out.println("   - Session has no start time, skipping.");
				continue;
			}
			long start = session.getStartDate().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
			// Check if the session has been open for longer than the threshold
			if (now - start >= thresholdMillis) {
				// Session is old enough to be auto-closed, proceed with closing it
				System.out.println("   - Session is old enough to auto-close, proceeding.");
				session.setEndDate(LocalDateTime.now());
				session.setDurationSeconds((System.currentTimeMillis() - now) / 1000);
				session.setCompleted(Constants.TIMER_STATUS_CLOSE_SYSTEM);
				try {
					userTransaction.begin();
					em.merge(session);
					userTransaction.commit();
					System.out.println(" - Timer session with ID " + session.getId() + " auto-closed successfully.");
				} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException
						| RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
					System.out.println(" - Error auto-closing timer session with ID " + session.getId());
					e.printStackTrace();
					try {
						userTransaction.rollback();
					} catch (IllegalStateException | SecurityException | SystemException rollbackEx) {
						System.out.println(
								" - Error rolling back transaction for timer session with ID " + session.getId());
						rollbackEx.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * Static method to retrieve a configuration property by key, with an optional
	 * default value if the key is not found. This method allows other parts of the
	 * application to access configuration properties loaded from the configuration
	 * file.
	 * 
	 * @param key          the key of the configuration property to retrieve
	 * @param defaultValue the default value to return if the key is not found in
	 *                     the configuration properties
	 * @return the value of the configuration property associated with the specified
	 *         key, or the default value if the key is not found
	 */
	public static String getProperty(String key, String defaultValue) {
		return config.getProperty(key, defaultValue);
	}
}
