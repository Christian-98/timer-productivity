package it.christian.timer.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO class to return statistics for dashboard
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsDashboardDTO implements Serializable{
	
	/*
	 * Serial class ID (required)
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Total sessions, to calculate average and best session
	 */
	private long totalSessions;
	/*
	 * Total seconds of all sessions, to calculate average and best session
	 */
	private long totalSeconds;
    /*
	 * Average seconds of all sessions, calculated as totalSeconds / totalSessions
	 */
    private long averageSeconds;
    /*
     * Best session seconds, calculated as the maximum duration of all sessions
     */
    private long bestSessionSeconds;
    /**
	 * Start date of the period for which the statistics are calculated
	 */
    private LocalDateTime startDate;
    /*
     * End date of the period for which the statistics are calculated
     */
    private LocalDateTime endDate;
    /*
     * List of sessions, to calculate totalSeconds, averageSeconds and bestSessionSeconds
     */
    private List<TimerTDO> sessions;
        
	/**
	 * @return the totalSessions
	 */
	public long getTotalSessions() {
		return totalSessions;
	}
	
	/**
	 * @param totalSessions the totalSessions to set
	 */
	public void setTotalSessions(long totalSessions) {
		this.totalSessions = totalSessions;
	}
	
	/**
	 * @return the totalSeconds
	 */
	public long getTotalSeconds() {
		return totalSeconds;
	}
	
	/**
	 * @param totalSeconds the totalSeconds to set
	 */
	public void setTotalSeconds(long totalSeconds) {
		this.totalSeconds = totalSeconds;
	}
	
	/**
	 * @return the averageSeconds
	 */
	public long getAverageSeconds() {
		return averageSeconds;
	}
	
	/**
	 * @param averageSeconds the averageSeconds to set
	 */
	public void setAverageSeconds(long averageSeconds) {
		this.averageSeconds = averageSeconds;
	}
	
	/**
	 * @return the bestSessionSeconds
	 */
	public long getBestSessionSeconds() {
		return bestSessionSeconds;
	}
	
	/**
	 * @param bestSessionSeconds the bestSessionSeconds to set
	 */
	public void setBestSessionSeconds(long bestSessionSeconds) {
		this.bestSessionSeconds = bestSessionSeconds;
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
	 * @return the sessions
	 */
	public List<TimerTDO> getSessions() {
		return sessions;
	}
	
	/**
	 * @param sessions the sessions to set
	 */
	public void setSessions(List<TimerTDO> sessions) {
		this.sessions = sessions;
	}
}
