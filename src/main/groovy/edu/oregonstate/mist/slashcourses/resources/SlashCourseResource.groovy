package edu.oregonstate.mist.slashcourses.resources

import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.slashcourses.core.ErrorPOJO
import edu.oregonstate.mist.slashcourses.core.SlashCourse
import edu.oregonstate.mist.slashcourses.db.SlashCourseDAO
import io.dropwizard.jersey.params.IntParam

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response
import javax.ws.rs.core.MediaType

/**
 * Sample resource class.
 */
@Path('/slash_courses/')
@Produces(MediaType.APPLICATION_JSON)
class SlashCourseResource extends Resource {

    private final SlashCourseDAO slashCourseDAO

    public SlashCourseResource(SlashCourseDAO slashCourseDAO){
        this.slashCourseDAO = slashCourseDAO
    }

    /**
     * Respond to GET requests and show course information according to course CRN.
     *
     * @param crn
     * @return founded course object or error message
     */

    @GET
    @Path('{crn: \\d+}')
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByCRN(@PathParam('crn') Integer crn) {
        Response returnResponse
        SlashCourse slashCourse = slashCourseDAO.getByCRN(crn)

        if (slashCourse == null) {
            returnResponse = notFound()
        } else {
            returnResponse = ok(slashCourse).build()
        }
            returnResponse
    }

    /**
     * Responds to POST requests by reading and returning a message.
     *
     * @param message
     * @return message
     */
//    @POST
//    @Consumes(MediaType.TEXT_PLAIN)
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response postMessage(String message, @Auth AuthenticatedUser authenticatedUser) {
//        ResponseBuilder responseBuilder = ok(message)
//        responseBuilder.build()
//    }
}
