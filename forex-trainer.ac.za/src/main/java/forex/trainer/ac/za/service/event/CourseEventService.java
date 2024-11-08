package forex.trainer.ac.za.service.event;

import forex.trainer.ac.za.model.event.CourseEvent;

import java.util.List;
import java.util.UUID;

public interface CourseEventService {
    CourseEvent createEvent(CourseEvent event, String courseId);

    List<CourseEvent> getEventsForCourse(String courseId);

    CourseEvent cancelEvent(String eventId);

    List<CourseEvent> getEventsForUser(UUID userId);
}
