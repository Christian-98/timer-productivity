package it.christian.timer.support;

public class Constants {
	
	/**
	 * Timer status constants
	 */
	
	/**
	 * ACTIVE: timer is active and running
	 */
	public static final String TIMER_STATUS_ACTIVE = "ACTIVE";
	
	/**
	 * PAUSED: timer is paused, not running but can be resumed
	 */
	public static final String TIMER_STATUS_COMPLETED = "COMPLETED";
	
	/**
	 * CLOSE: timer is closed, not running and cannot be resumed
	 */
	public static final String TIMER_STATUS_CLOSE = "CLOSE";
	
	/**
	 * CLOSE_SYSTEM: timer is closed by the system, not running and cannot be resumed
	 */
	public static final String TIMER_STATUS_CLOSE_SYSTEM = "CLOSE_SYSTEM";
	
}
