package it.christian.timer.entity.timerInterval;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "timer_interval")
public class TimerInterval extends TimerIntervalStructure {

	/**
	 * Default serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * No args constructor
	 */
	public TimerInterval() {
		// nothing to do here, required by jpa framework
		
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TimerInterval [id=").append(id)
			   .append(", sessionID=").append(sessionID)
			   .append(", startTime=").append(startDate)
			   .append(", endTime=").append(endDate)
			   .append("]");
		return builder.toString();
	}
}