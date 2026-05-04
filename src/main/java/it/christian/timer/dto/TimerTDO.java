package it.christian.timer.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimerTDO implements Serializable {
	
	/*
	 * Serial class ID (required)
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Timer ID
	 */
	private int timerId;
	/*
	 * Timer status
	 */
	private String status;
	/*
	 * Timer name
	 */
	private String name;
	/*
	 * Timer duration in seconds
	 */
	private long DurationSeconds;
	/*
	 * Timer note
	 */
	private String note;
	
	/**
	 * @return the timerId
	 */	
	public int getTimerId() {
		return timerId;
	}
	/**
	 * @param timerId the timerId to set
	 */
	public void setTimerId(int timerId) {
		this.timerId = timerId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the durationSeconds
	 */
	public long getDurationSeconds() {
		return DurationSeconds;
	}
	/**
	 * @param durationSeconds the durationSeconds to set
	 */
	public void setDurationSeconds(long durationSeconds) {
		DurationSeconds = durationSeconds;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
}
