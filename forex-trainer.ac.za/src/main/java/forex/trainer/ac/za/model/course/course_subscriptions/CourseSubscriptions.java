package forex.trainer.ac.za.model.course.course_subscriptions;

import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.base.AbstractPersistenceEntity;
import forex.trainer.ac.za.model.course.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CourseSubscriptions extends AbstractPersistenceEntity
{
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

}
