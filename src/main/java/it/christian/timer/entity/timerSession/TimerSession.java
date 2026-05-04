package it.christian.timer.entity.timerSession;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "timer_session")
@NamedQueries({
	@NamedQuery(name = "TimerSession.findAll", query = "SELECT ts FROM TimerSession ts"),
	@NamedQuery(name = "TimerSession.avgDuration", query = "SELECT AVG(t.durationSeconds) FROM TimerSession t WHERE t.durationSeconds IS NOT NULL"),
	@NamedQuery(name = "TimerSession.findById", query = "SELECT ts FROM TimerSession ts WHERE ts.id = :id")
})
public class TimerSession extends TimerSessionStructure {

	/**
	 * Default serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * No args constructor
	 */
	public TimerSession() {
		// nothing to do here, required by jpa framework
		
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TimerSession [id=").append(id)
			   .append(", name=").append(name)
			   .append(", start_date=").append(startDate)
			   .append(", end_date=").append(endDate)
			   .append(", duration_seconds=").append(durationSeconds)
			   .append(", completed=").append(completed)
			   .append(", note=").append(note)
			   .append("]");
		return builder.toString();
	}	
}