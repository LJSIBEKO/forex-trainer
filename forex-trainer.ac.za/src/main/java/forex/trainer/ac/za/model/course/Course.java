package forex.trainer.ac.za.model.course;

import forex.trainer.ac.za.model.base.AbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Course extends AbstractPersistenceEntity
{

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private double amount;
    private CourseStatus status;
    private Integer numberOfStudentsAllowed;
    private Integer currentStudentRegistered;
}
