package forex.trainer.ac.za.service.course;

import forex.trainer.ac.za.model.course.Course;
import forex.trainer.ac.za.model.course.course_subscriptions.CourseSubscriptions;

import java.util.List;
import java.util.UUID;

public interface CourseService
{
    Course getCourse(UUID id);
    Course createCourse(Course course);
    Course updateCourse(Course course);
    void deleteCourse(UUID id);
    List<Course> getAllCourses();
    CourseSubscriptions subscribeCourse(UUID userId, UUID courseId);
    List<CourseSubscriptions> getUserCourseSubscriptions(UUID userId);
}
