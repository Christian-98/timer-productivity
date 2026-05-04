package it.christian.timer.restcontroller.response;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "errorCode", "message", "fields" })
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * <p>ErrorBean property "errorCode" of type int.</p>
	 * <p>Contains the HTTP error code.</p>
	 */
	@JsonProperty("code")
	private int errorCode;

	/*
	 * <p>ErrorBean property "fields" of type <b>List&lt;ErrorMessage&gt;.</b></p>
	 * <p>Contains the list of error messages.</p>
	 */
	@JsonProperty("message")
	private String message;

	/*
	 * <p>ErrorBean property "params" of type <b>Map&lt;String, Object&gt;.</b></p>
	 * <p>Add optional parameters.</p>
	 */
	@JsonProperty("fields")
	private Map<String, Object> params;

	public ErrorBean() {

	}
	
	public ErrorBean(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}
	
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	@Override
	public String toString() {
		return "ErrorBean [errorCode=" + errorCode + ", message=" + message + ", params=" + params + "]";
	}

}
