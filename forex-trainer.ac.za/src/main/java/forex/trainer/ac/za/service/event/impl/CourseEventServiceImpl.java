package forex.trainer.ac.za.service.event.impl;

import forex.trainer.ac.za.exception.RequestException;
import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.booking.Booking;
import forex.trainer.ac.za.model.course.Course;
import forex.trainer.ac.za.model.event.CourseEvent;
import forex.trainer.ac.za.model.event.EventStatus;
import forex.trainer.ac.za.repository.account.UserAccountRepository;
import forex.trainer.ac.za.repository.booking.BookingRepository;
import forex.trainer.ac.za.repository.event.CourseEventRepository;
import forex.trainer.ac.za.service.course.CourseService;
import forex.trainer.ac.za.service.event.CourseEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CourseEventServiceImpl implements CourseEventService {

    @Autowired
    CourseEventRepository courseEventRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    UserAccountRepository accountRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    BookingRepository bookingRepository;


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

        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(() -> new RequestException("User not found"));

        List<Booking> booking =  bookingRepository.getUserBookingThatHaveNotExpired(userAccount.getId(), LocalDateTime.now());

        Set<UUID> courseIds = new HashSet<>();
        if(booking.isEmpty()){
            return List.of();
        }else{


            booking.forEach(booking1 -> {
                courseIds.add(booking1.getCource().getId());
            });
        }

        List<CourseEvent> events = new ArrayList<>();

        courseIds.forEach(courseId -> {
            events.addAll(courseEventRepository.findByCourseId(courseId));
        });

        return events;
    }


}
