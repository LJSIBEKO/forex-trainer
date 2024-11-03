package forex.trainer.ac.za.repository.cource;

import forex.trainer.ac.za.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourceRepositorty extends JpaRepository<Course, UUID>
{
    Course findByName(String name);
}
