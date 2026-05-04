package it.christian.timer.restcontroller.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T, M> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JsonInclude(Include.NON_NULL)
	private T data;

	@JsonInclude(Include.NON_NULL)
	private M meta;
	
	@JsonInclude(Include.NON_EMPTY)
    private ErrorBean errors;

	public BaseResponse() {
	}

	public BaseResponse(T data, M meta) {
		this.data = data;
		this.meta = meta;
	}
	
	public BaseResponse(T data) {
        this.data = data;
    }

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public M getMeta() {
		return meta;
	}

	public void setMeta(M meta) {
		this.meta = meta;
	}
	
	/**
     * <p>Return errors value or reference.</p>
     *
     * @return errors value or reference.
     */
    @JsonGetter("errors")
    public ErrorBean getErrors() {
        return errors;
    }
	
	/**
     * <p>Set errors value or reference.</p>
     *
     * @param errors Value to set.
     */
    @JsonSetter("errors")
    public void setErrors(ErrorBean errors) {
        this.errors = errors;
    }
    
    
}