package forex.trainer.ac.za.repository.cource;

import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.course.Course;
import forex.trainer.ac.za.model.course.course_subscriptions.CourseSubscriptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseSubscriptionsRepository extends JpaRepository<CourseSubscriptions, UUID>
{
    List<CourseSubscriptions> findByCourse(Course course);
    List<CourseSubscriptions> findByUserAccount(UserAccount user);
}
