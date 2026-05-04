package it.christian.timer.restcontroller;

import java.util.List;

import org.jboss.logging.Logger;

import it.christian.timer.dto.TimerTDO;
import it.christian.timer.entity.timerInterval.TimerInterval;
import it.christian.timer.entity.timerSession.TimerSession;
import it.christian.timer.restcontroller.request.BaseRequest;
import it.christian.timer.restcontroller.response.ComplexResponse;
import it.christian.timer.service.TimerActionService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/*
 * Controller for handling timer-related RESTful API endpoints.
 * This class is annotated with @Stateless, indicating that it is a stateless EJB (Enterprise Java Bean).
 * The @Path annotation specifies the base URI path for this controller, which is "/timerAction".
 * This means that any RESTful API endpoints defined in this class will be accessible under this path.
 * 
 * For example, if you define a method with @POST and @Path("/session/start"), it would be accessible at "/timerAction/session/start".
 * 
 * The implementation of the methods to handle specific timer-related requests will be added to this class.
 */

@Path("/timerAction")
@Stateless
public class TimerControllerImpl {

	/*
	 * Logger instance for logging information, warnings, and errors related to the operations of this controller.
	 */
	protected Logger logger = Logger.getLogger(TimerControllerImpl.class);

	@EJB
	private TimerActionService timerService;

	/*
	 * Starts a timer and returns a response indicating the result.
	 * 
	 * @return a Response object with a success message if the timer started
	 * successfully, or an error message if an exception occurred
	 */
	@POST
	@Path("/session/start")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response startTimer(@Valid BaseRequest<TimerTDO> request) {
		try {
			logger.info("Starting timer");
			TimerTDO data = request.getData();
			TimerSession ts = timerService.startTime(data.getName());
			return ComplexResponse.buildOkResponse(ts);
		} catch (Exception e) {
			logger.error("Error starting timer: " + e.getMessage(), e);
			return ComplexResponse.buildServerErrorResponse(e);
		}
	}

	/*
	 * Stops the timer with the specified ID and returns a response indicating the
	 * result.
	 * 
	 * @param id the ID of the timer session to be stopped, provided as a path
	 * parameter
	 * 
	 * @return a Response object with a success message if the timer stopped
	 * successfully, or an error message if an exception occurred
	 */
	@POST
	@Path("/session/{id}/stop")
	@Produces(MediaType.APPLICATION_JSON)
	public Response stopTimer(@Valid @NotNull @PathParam("id") int id) {
		logger.info("Stopping timer for session: " + id);

		try {
			TimerInterval stoppedInterval = timerService.stopTimer(id);
			if (stoppedInterval != null) {
				return ComplexResponse.buildOkResponse(stoppedInterval);
			} else {
				return ComplexResponse.buildBadRequestResponse();
			}
		} catch (Exception e) {
			logger.error("Errore imprevisto durante lo stop del timer", e);
			return ComplexResponse.buildServerErrorResponse(e);
		}
	}

	/*
	 * Resumes the timer with the specified ID and returns a response indicating the
	 * result.
	 * 
	 * @param id the ID of the timer session to be resumed, provided as a path
	 * parameter
	 * 
	 * @return a Response object with a success message if the timer resumed
	 * successfully, or an error message if an exception occurred
	 */
	@POST
	@Path("/session/{id}/resume")
	@Produces(MediaType.APPLICATION_JSON)
	public Response resumeTimer(@Valid @NotNull @PathParam("id") int id) {
		try {
			logger.info("Resuming timer");
			timerService.resumeTimer(id);
			return ComplexResponse.buildOkResponse("Timer resumed successfully");
		} catch (Exception e) {
			logger.error("Error resuming timer: " + e.getMessage(), e);
			return ComplexResponse.buildServerErrorResponse(e);
		}
	}

	/*
	 * Ends the timer with the specified ID and returns a response indicating the
	 * result.
	 * 
	 * @param id the ID of the timer session to be ended, provided as a path
	 * parameter
	 * 
	 * @return a Response object with a success message if the timer ended
	 * successfully, or an error message if an exception occurred
	 */
	@POST
	@Path("/session/{id}/end")
	@Produces(MediaType.APPLICATION_JSON)
	public Response endTimer(@Valid @NotNull @PathParam("id") int id) {
		try {
			logger.info("Ending timer session with ID: " + id);
			timerService.endTimer(id);
			return ComplexResponse.buildOkResponse("Timer ended successfully");
		} catch (Exception e) {
			logger.error("Error ending timer: " + e.getMessage(), e);
			return ComplexResponse.buildServerErrorResponse(e);
		}
	}

	/*
	 * Deletes the timer session with the specified ID and returns a response
	 * indicating the result.
	 * 
	 * @param id the ID of the timer session to be deleted, provided as a path
	 * parameter
	 * 
	 * @return a Response object with a success message if the timer session deleted
	 * successfully, or an error message if an exception occurred
	 */
	@POST
	@Path("/session/{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTimer(@Valid @NotNull @PathParam("id") int id) {
		try {
			logger.info("Deleting timer session with ID: " + id);
			timerService.deleteTimer(id);
			return ComplexResponse.buildOkResponse("Timer session deleted successfully");
		} catch (Exception e) {
			logger.error("Error deleting timer session: " + e.getMessage(), e);
			return ComplexResponse.buildServerErrorResponse(e);
		}
	}
	
	/*
	 * Adds a note to the timer session with the specified ID and returns a response
	 * indicating the result.
	 * 
	 * @param id the ID of the timer session to which the note will be added, provided
	 * as a path parameter
	 * @param request a BaseRequest object containing the note to be added, provided
	 * in the request body as JSON
	 * 
	 * @return a Response object with a success message if the note added
	 * successfully, or an error message if an exception occurred
	 */
	@POST
	@Path("/session/{id}/addNote")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNote(@PathParam("id") int id, @Valid BaseRequest<String> request) {
		try {
			String note = request.getData();
			if (note == null || note.trim().isEmpty()) {
				logger.warn("Attempted to add an empty note to timer session with ID: " + id);
				return ComplexResponse.buildBadRequestResponse();
			}
			logger.info("Adding note to timer session with ID: " + id);
			timerService.addNote(id, note);
			return ComplexResponse.buildOkResponse("Note added successfully");
		} catch (Exception e) {
			logger.error("Error adding note to timer session: " + e.getMessage(), e);
			return ComplexResponse.buildServerErrorResponse(e);
		}
	}

	/*
	 * Retrieves all timer sessions from the database.
	 * 
	 * @return a list of TimerSession objects in JSON format
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		try {
			logger.info("Retrieving all timer sessions");
			List<TimerSession> sessions = timerService.findAll();
			return ComplexResponse.buildOkResponse(sessions);
		} catch (Exception e) {
			logger.error("Error retrieving timer sessions: " + e.getMessage(), e);
			return ComplexResponse.buildServerErrorResponse(e);
		}
	}
}
