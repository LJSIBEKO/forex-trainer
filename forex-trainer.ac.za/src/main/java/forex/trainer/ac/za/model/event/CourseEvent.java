package forex.trainer.ac.za.model.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import forex.trainer.ac.za.model.base.AbstractPersistenceEntity;
import forex.trainer.ac.za.model.course.Course;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class CourseEvent extends AbstractPersistenceEntity {

    @Column(unique = true, nullable = false)
    String eventName;

    LocalDateTime startTime;
    LocalDateTime endTime;
    String description;

    @Enumerated(EnumType.STRING)
    EventStatus status;

    Boolean isMandatory;


    @JoinColumn(name = "course_id")
    @JsonIgnore
    @ManyToOne
    Course course;

}
