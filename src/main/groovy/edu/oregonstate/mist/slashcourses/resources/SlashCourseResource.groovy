package edu.oregonstate.mist.slashcourses.resources

import com.google.common.base.Optional
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.slashcourses.core.Instructor
import edu.oregonstate.mist.slashcourses.core.SlashCourse
import edu.oregonstate.mist.slashcourses.db.InstructorDAO
import edu.oregonstate.mist.slashcourses.db.SlashCourseDAO
import org.skife.jdbi.v2.exceptions.UnableToCloseResourceException

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response
import javax.ws.rs.core.MediaType

/**
 * Slash Course Resource class.
 */
@Path('/slash_courses/')
@Produces(MediaType.APPLICATION_JSON)
class SlashCourseResource extends Resource {

    private final SlashCourseDAO slashCourseDAO
    private final InstructorDAO instructorDAO

    public SlashCourseResource (SlashCourseDAO slashCourseDAO, InstructorDAO instructorDAO) {
        this.slashCourseDAO = slashCourseDAO
        this.instructorDAO  = instructorDAO
    }

    /**
     * Respond to GET requests and show all courses information.
     *
     * @return founded course objects
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SlashCourse> getAll (@QueryParam("course_num") Optional<String> courseNum,
                                     @QueryParam("term") Optional<String> term,
                                     @QueryParam("department") Optional<String> department,
                                     @QueryParam("slash") Optional<Integer> slash) {

        // get all slash courses or filtered by parameters
        List<SlashCourse> slashCourseList = slashCourseDAO.getCoursesMatch(courseNum.or(""), term.or(""), department.or(""), slash.or(1))
        for (slashCourse in slashCourseList) {
            slashCourse.instructor  = instructorDAO.getByInstructorID(slashCourse.instructorId)
        }

        slashCourseList
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
    public Response getByCRN (@PathParam('crn') Integer crn) {
        Response returnResponse
        SlashCourse slashCourse = slashCourseDAO.getByCRN(crn)

        if (slashCourse == null) {
            returnResponse = notFound().build()
        } else {
            slashCourse.instructor  = instructorDAO.getByInstructorID(slashCourse.instructorId)
            returnResponse = ok(slashCourse).build()
        }
            returnResponse
    }

    /**
     * Respond to DELETE requests and removes a course object according to course CRN.
     *
     * @param crn
     * @return response containing the result or error message
     */
    @Path('{crn: \\d+}')
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteByCRN (@PathParam('crn') Integer crn) {
        slashCourseDAO.deleteByCRN(crn)
        Response.ok().build()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postCourse (@Valid SlashCourse newCourse) {

        Response returnResponse
        try {

            if (newCourse.getInstructor() != null) {
                instructorDAO.postInstructor(newCourse.getInstructor().getLastName(),
                                             newCourse.getInstructor().getFirstName())
                slashCourseDAO.postCourse(newCourse.getCrn(),
                                          newCourse.getCourseNum(),
                                          newCourse.getCourseName(),
                                          newCourse.getSlash(),
                                          newCourse.getTerm(),
                                          // retrieve the instructorId just created as instructorId in the new course object
                                          instructorDAO.getLatestInstructorId().toInteger(),
                                          newCourse.getDay(),
                                          newCourse.getTime(),
                                          newCourse.getLocation(),
                                          newCourse.getType(),
                                          newCourse.getInstructor())

                URI createdURI = URI.create("/" + instructorDAO.getLatestInstructorId())
                returnResponse = Response.created(createdURI).build()
            } else {
                slashCourseDAO.postCourse(newCourse.getCrn(),
                                          newCourse.getCourseNum(),
                                          newCourse.getCourseName(),
                                          newCourse.getSlash(),
                                          newCourse.getTerm(),
                                          newCourse.getInstructorId(),
                                          newCourse.getDay(),
                                          newCourse.getTime(),
                                          newCourse.getLocation(),
                                          newCourse.getType(),
                                          newCourse.getInstructor())
                returnResponse = Response.ok().build()
            }

        } catch (Exception e) {
            String constraintError = e.cause.toString()
            System.out.println("*** DEBUG " + constraintError + "***")

            if (constraintError.contains("COURSE_PK")) {
                returnResponse = badRequest("CRN is not unique").build()
            } else {
                System.out.println("*** Localized Message " + e.localizedMessage + "***")
                returnResponse = internalServerError("Internal server error").build()
            }
        }

        returnResponse
    }

}
