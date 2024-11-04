package forex.trainer.ac.za.service.event;

import forex.trainer.ac.za.model.event.CourseEvent;

import java.util.List;

public interface CourseEventService {
    CourseEvent createEvent(CourseEvent event, String courseId);

    List<CourseEvent> getEventsForCourse(String courseId);

    CourseEvent cancelEvent(String eventId);
}
