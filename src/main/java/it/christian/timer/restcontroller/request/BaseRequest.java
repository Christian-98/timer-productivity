package it.christian.timer.restcontroller.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>Base Request class includes data, meta and filter and is the base of all requests in the console project.</p>
 * @param <T> decribe the generic param for base request
 */

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRequest<T> implements Serializable {

	/**
	 * Serial class ID (required)
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * <p>BaseRequest property "data" of type T, a generic parameter.</p>
     * 
     * <p>Contains the request data.</p>
     */
    @JsonInclude(Include.NON_EMPTY)
    private T data;
    
    /**
     * <p>Return data value or reference.</p>
     *
     * @return data value or reference.
     */
    @JsonProperty("data")
    public T getData() {
        return data;
    }

    /**
     * <p>Set data value or reference.</p>
     *
     * @param data Value to set.
     */
    @JsonProperty("data")
    public void setData(T data) {
        if (data != null) {
            this.data = data;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BaseRequest [data=");
        stringBuilder.append(data);
        return stringBuilder.toString();
    }

}
