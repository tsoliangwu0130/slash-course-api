package edu.oregonstate.mist.slashcourses.db

import edu.oregonstate.mist.slashcourses.core.SlashCourse
import edu.oregonstate.mist.slashcourses.mapper.SlashCourseMapper
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

/**
 * Slash Course DAO
 */
@RegisterMapper(SlashCourseMapper)
public interface SlashCourseDAO extends Closeable {

    /** Get slash course by CRN
     *
     *  @param crn
     *  @return Slash Course object
     */
    @SqlQuery("""
              SELECT *
              FROM COURSE
              WHERE CRN = :crn
              """ )
    SlashCourse getByCRN(@Bind("crn") Integer crn)

    @Override
    void close()
}