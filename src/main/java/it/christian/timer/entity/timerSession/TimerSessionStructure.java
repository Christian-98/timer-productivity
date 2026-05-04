package it.christian.timer.entity.timerSession;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
public class TimerSessionStructure implements Serializable{
	
	/**
	 * Serial class ID (required)
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    protected int id;
	
	@Column(name = "name")
	@JsonProperty("name")
	protected String name;

	@Column(name = "start_date")
	@JsonProperty("start_date")
	@NotNull
    protected LocalDateTime startDate;

	@Column(name = "end_date")
    @JsonProperty("end_date")
    protected LocalDateTime endDate;
    
    @Column(name = "duration_seconds")
    @JsonProperty("duration_seconds")
    protected long durationSeconds;

    @Column(name = "completed")
    @JsonProperty("completed")
    @NotNull
    protected String completed;
    
    @Column(name = "note")
    @JsonProperty("note")
    protected String note;

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
	 * @return the startDate
	 */
	public LocalDateTime getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the durationSeconds
	 */
	public long getDurationSeconds() {
		return durationSeconds;
	}

	/**
	 * @param durationSeconds the durationSeconds to set
	 */
	public void setDurationSeconds(long durationSeconds) {
		this.durationSeconds = durationSeconds;
	}

	/**
	 * @return the completed
	 */
	public String getCompleted() {
		return completed;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(String completed) {
		this.completed = completed;
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
