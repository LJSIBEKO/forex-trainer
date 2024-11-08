package forex.trainer.ac.za.service.event.impl;

import forex.trainer.ac.za.exception.RequestException;
import forex.trainer.ac.za.model.course.Course;
import forex.trainer.ac.za.model.event.CourseEvent;
import forex.trainer.ac.za.model.event.EventStatus;
import forex.trainer.ac.za.repository.event.CourseEventRepository;
import forex.trainer.ac.za.service.course.CourseService;
import forex.trainer.ac.za.service.event.CourseEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseEventServiceImpl implements CourseEventService {

    @Autowired
    CourseEventRepository courseEventRepository;

    @Autowired
    CourseService courseService;


    @Override
    public CourseEvent createEvent(CourseEvent event, String courseId) {
        Course course = courseService.getCourse(UUID.fromString(courseId));

        if(course == null)
            throw new RequestException("Course not found");


        System.out.println(event.getEventName());
        event.setStatus(EventStatus.PLANNED);
        event.setIsMandatory(true);
        event.setCourse(course);
        return courseEventRepository.save(event);
    }

    @Override
    public List<CourseEvent> getEventsForCourse(String courseId) {
        System.out.println("getting events");
        return courseEventRepository.findByCourseId(UUID.fromString(courseId));
    }

    @Override
    public CourseEvent cancelEvent(String eventId) {
        Optional<CourseEvent> eventOptional = courseEventRepository.findById(UUID.fromString(eventId));

        if(eventOptional.isEmpty())
            throw new RequestException("Event not found");
        else {
            CourseEvent event = eventOptional.get();

            event.setStatus(EventStatus.CANCELLED);

            courseEventRepository.save(event);

            return event;
        }

    }

    @Override
    public List<CourseEvent> getEventsForUser(UUID userId) {
        return List.of();
    }


}
