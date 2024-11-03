package forex.trainer.ac.za.dtos.cource;

import forex.trainer.ac.za.model.course.Course;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class UserSubscribe
{
    private UUID userAccountId;
    private UUID courseId;
}
