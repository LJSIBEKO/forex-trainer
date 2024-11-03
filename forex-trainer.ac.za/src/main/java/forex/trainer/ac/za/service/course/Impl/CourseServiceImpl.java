package forex.trainer.ac.za.service.course.Impl;

import forex.trainer.ac.za.exception.RequestException;
import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.course.Course;
import forex.trainer.ac.za.model.course.course_subscriptions.CourseSubscriptions;
import forex.trainer.ac.za.repository.account.UserAccountRepository;
import forex.trainer.ac.za.repository.cource.CourceRepositorty;
import forex.trainer.ac.za.repository.cource.CourseSubscriptionsRepository;
import forex.trainer.ac.za.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {


    @Autowired
    private CourceRepositorty courceRepositorty;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private CourseSubscriptionsRepository courseSubscriptionsRepository;

    @Override
    public Course getCourse(UUID id) {
        return courceRepositorty.getReferenceById(id);
    }

    @Override
    public Course createCourse(Course course) {
        // Check if a course with the same name already exists
        Course existingCourse = courceRepositorty.findByName(course.getName());
        if (existingCourse != null) {
            throw new RequestException("Course already exists");
        }

        if(course.getAmount()<1)
            throw new RequestException("Please enter a positive amount");

        // Save the new course
        return courceRepositorty.save(course);
    }

    @Override
    public Course updateCourse(Course course) {
        // Check if the course exists
        if (!courceRepositorty.existsById(course.getId())) {
            throw new RequestException("Course not found");
        }

        // Update the course and save it
        return courceRepositorty.save(course);
    }

    @Override
    public void deleteCourse(UUID id) {
        // Check if the course exists
        if (!courceRepositorty.existsById(id)) {
            throw new RequestException("Course not found");
        }


        courceRepositorty.deleteById(id);
    }

    @Override
    public List<Course> getAllCourses() {
        // Retrieve all courses
        return courceRepositorty.findAll();
    }

    @Override
    public CourseSubscriptions subscribeCourse(UUID userId, UUID courseId) {

        UserAccount user = userAccountRepository.findById(userId).orElseThrow(() -> new RequestException("User not found"));
        Course course = courceRepositorty.findById(courseId).orElseThrow(() -> new RequestException("Course not found"));

        if(course.getCurrentStudentRegistered()>=course.getNumberOfStudentsAllowed())
            throw new RequestException("Course has enough student registered");

        CourseSubscriptions subscriptions = new CourseSubscriptions();
        subscriptions.setCourse(course);
        subscriptions.setUserAccount(user);

        course.setCurrentStudentRegistered(course.getCurrentStudentRegistered()+1);
        courceRepositorty.save(course);

        return courseSubscriptionsRepository.save(subscriptions);
    }

    @Override
    public List<CourseSubscriptions> getUserCourseSubscriptions(UUID userId){
        UserAccount user = userAccountRepository.findById(userId).orElseThrow(() -> new RequestException("User not found"));
        return courseSubscriptionsRepository.findByUserAccount(user);
    }
}
