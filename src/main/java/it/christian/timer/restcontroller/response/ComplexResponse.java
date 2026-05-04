package it.christian.timer.restcontroller.response;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

public final class ComplexResponse<D, M> {

	/**
	 * <p>Default constructor with no arguments.</p>
	 *
	 */
	private ComplexResponse() {

	}

	/*
	 * <p>Creates a response instance with status code 200(OK) with content.</p>
	 * @param <D> generic type parameter for the data field in the response.
	 * @param data of type <b>Object</b>, holds the response data.
	 * 
	 * @return response instance with status code 200(OK) with content
	 */
	public static <D> Response buildOkResponse(final D data) {
		System.out.println("Complex Resposne");
		final BaseResponse document = new BaseResponse<>(data);
		final ResponseBuilder builder = Response.ok(document);
		return builder.build();
	}

	/**
	 * <p>Creates a response instance with the status code specified in 
	 * @param <code>status</code>.</p>
	 * 
	 * @param status       of type <b>Status</b>
	 * @param reasonPhrase of type <b>String</b>
	 * @return an single error response instance with the specified status code and message
	 */
	public static Response buildSingleErrorResponse(Status status, String reasonPhrase) {
		final ResponseBuilder builder = Response.status(status);
		final BaseResponse document = new BaseResponse<>();
		final ErrorBean error = new ErrorBean();
		error.setErrorCode(status.getStatusCode());
		error.setMessage(reasonPhrase);
		document.setErrors(error);
		return builder.entity(document).build();
	}

	/**
	 * <p>Creates a 404 Not Found error response.</p> 
	 * @return a 404 Not Found error response instance.
	 */
	public static Response buildNotFoundResponse() {
		return buildSingleErrorResponse(Status.NOT_FOUND, Status.NOT_FOUND.getReasonPhrase());
	}

	/**
	 * <p>Creates a bad request error response instance.</p>
	 *
	 * @return a bad request error response instance.
	 */
	public static Response buildBadRequestResponse() {
		return buildSingleErrorResponse(Status.BAD_REQUEST, Status.BAD_REQUEST.getReasonPhrase());
	}

	/**
	 * <p>Creates a 500 SERVER ERROR response.</p>
	 * 
	 * @return a 500 SERVER ERROR response instance.
	 */
	public static Response buildServerErrorResponse() {
		return buildSingleErrorResponse(Status.INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
	}
	
	/**
     * <p>Creates a 500 SERVER ERROR response, with the exception message.</p>
     * 
     * @param exception of type <b>Exception</b>
     * @return a 500 SERVER ERROR response instance.
     */
    public static Response buildServerErrorResponse(Exception exception) {
        return buildSingleErrorResponse(Status.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
}
