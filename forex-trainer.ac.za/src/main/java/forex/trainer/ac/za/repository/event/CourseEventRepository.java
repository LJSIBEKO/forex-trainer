package forex.trainer.ac.za.repository.event;

import forex.trainer.ac.za.model.event.CourseEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseEventRepository extends JpaRepository<CourseEvent, UUID> {

    List<CourseEvent> findByCourseId(UUID courseId);
}
