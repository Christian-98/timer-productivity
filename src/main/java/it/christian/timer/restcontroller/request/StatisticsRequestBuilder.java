package it.christian.timer.restcontroller.request;

public class StatisticsRequestBuilder<D extends BaseRequest<?>> {
	
	private D request;

	/**
	 * @return the request
	 */
	public D getRequest() {
		return request;
	}
}
