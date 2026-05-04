package it.christian.timer.entity.timerInterval;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class TimerIntervalStructure implements Serializable {

	/**
	 * Serial class ID (required)
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected int id;

	@Column(name = "session_id")
	@JsonProperty("session_id")
	protected int sessionID;
	
	@Column(name = "start_date")
	@JsonProperty("start_date")
	protected LocalDateTime startDate;
	
	@Column(name = "end_date")
	@JsonProperty("end_date")
	protected LocalDateTime endDate;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the sessionID
	 */
	public int getSessionID() {
		return sessionID;
	}

	/**
	 * @param sessionID the sessionID to set
	 */
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * @return the startDate
	 */
	public LocalDateTime getStartTime() {
		return startDate;
	}

	/**
	 * @param string the startDate to set
	 */
	public void setStartTime(LocalDateTime string) {
		this.startDate = string;
	}

	/**
	 * @return the endDate
	 */
	public LocalDateTime getEndTime() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endDate = endTime;
	}
}
