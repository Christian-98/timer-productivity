package it.christian.timer.time;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import jakarta.annotation.PostConstruct;

/**
 * TimeProvider class that provides the current date and time based on a configurable time zone.
 * The time zone can be set through the system property "timer.timezone". If the property is not set,
 * it defaults to the system's default time zone.
 */
public class TimeProvider {

	/*
	 * TimeProvider class that provides the current date and time based on a
	 * configurable time zone. The time zone can be set through the system property
	 * "timer.timezone". If the property is not set, it defaults to the system's
	 * default time zone.
	 */
	private ZoneId zoneId;
	private Clock clock;

	/**
	 * Initializes the TimeProvider by setting up the clock based on the configured
	 * time zone. The time zone is read from the system property "timer.timezone".
	 * If not set, it defaults to the system's default time zone.
	 */
	@PostConstruct
	public void init() {
		String timeZone = System.getProperty("timer.timezone", null);
		this.zoneId = ZoneId.of(timeZone);
		this.clock = Clock.system(zoneId);
	}

	/**
	 * Returns the current date and time based on the configured clock.
	 *
	 * @return the current LocalDateTime
	 */
	public LocalDateTime now() {
		return LocalDateTime.now(clock);
	}
}
