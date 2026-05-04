package it.christian.timer.restcontroller.request;

/**
 * Builder class for timer requests, encapsulating the request data and providing a fluent interface for constructing timer-related requests.
 *
 * @param <D> the type of the request, which must extend BaseRequest
 */
public class TimerRequestBuilder<D extends BaseRequest<?>> {
	
	private D request;
	private String name;
	
	/**
     * <p>Constructor with parameter D.</p>
     * @param request D Generic
     */
    public TimerRequestBuilder(D request) {
        super();
        this.request = request;
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
	 * @return the request
	 */
	public D getRequest() {
		return request;
	}
}
