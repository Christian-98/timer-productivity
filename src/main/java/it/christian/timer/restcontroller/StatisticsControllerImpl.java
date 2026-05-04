package it.christian.timer.restcontroller;

import java.util.List;

import org.jboss.logging.Logger;

import it.christian.timer.dto.StatisticsDashboardDTO;
import it.christian.timer.dto.TimerTDO;
import it.christian.timer.restcontroller.request.BaseRequest;
import it.christian.timer.restcontroller.response.ComplexResponse;
import it.christian.timer.service.StatisticsService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/*
 * Controller for handling statistics-related RESTful API endpoints.
 * This class is annotated with @Stateless, indicating that it is a stateless EJB (Enterprise Java Bean).
 * The @Path annotation specifies the base URI path for this controller, which is "/statisticsAction".
 * This means that any RESTful API endpoints defined in this class will be accessible under this path.
 * 
 * For example, if you define a method with @GET and @Path("/summary"), it would be accessible at "/statisticsAction/summary".
 * 
 * The implementation of the methods to handle specific statistics-related requests will be added to this class.
 */

@Path("/statisticsAction")
@Stateless
public class StatisticsControllerImpl {

	/*
	 * Logger instance for logging information, warnings, and errors related to the
	 * operations of this controller.
	 */
	protected Logger logger = Logger.getLogger(StatisticsControllerImpl.class);

	@EJB
	private StatisticsService statisticsService;

	/*
	 * Retrieves the statistics dashboard and returns it in the response.
	 * 
	 * @return a Response object containing the statistics dashboard if successful,
	 * or an error message if an exception occurred
	 */
	@POST
	@Path("/stats/dashboard")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDashboard() {
		try {
			logger.info("Getting statistics dashboard");
			StatisticsDashboardDTO dto = statisticsService.getDashboard();
			return ComplexResponse.buildOkResponse(dto);
		} catch (Exception e) {
			logger.error("Error getting dashboard", e);
			return ComplexResponse.buildServerErrorResponse(e);
		}
	}

	/*
	 * Retrieves a summary of statistics and returns it in the response.
	 * 
	 * @return a Response object containing the summary of statistics if successful,
	 * or an error message if an exception occurred
	 */
	@POST
	@Path("/stats/summary")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSummary() {
		try {
			logger.info("Getting statistics summary");
			List<TimerTDO> summary = statisticsService.getSummary();
			return ComplexResponse.buildOkResponse(summary);
		} catch (Exception e) {
			logger.error("Error getting statistics summary", e);
			return ComplexResponse.buildServerErrorResponse(e);
		}
	}

	/*
	 * Retrieves statistics by period and returns it in the response.
	 * 
	 * @return a Response object containing the statistics by period if successful,
	 * or an error message if an exception occurred
	 */
	@POST
	@Path("/stats/byPeriod")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatsByPeriod(@Valid @NotNull BaseRequest<StatisticsDashboardDTO> request) {
		try {
			logger.info("Getting statistics by period");
			StatisticsDashboardDTO data = request.getData();
			StatisticsDashboardDTO dto = statisticsService.getPeriod(data.getStartDate(), data.getEndDate());
			return ComplexResponse.buildOkResponse(dto);
		} catch (Exception e) {
			logger.error("Error getting statistics by period", e);
			return ComplexResponse.buildServerErrorResponse(e);
		}
	}
}
