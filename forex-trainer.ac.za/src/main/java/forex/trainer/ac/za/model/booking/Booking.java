package forex.trainer.ac.za.model.booking;

import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.base.AbstractPersistenceEntity;
import forex.trainer.ac.za.model.course.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Booking extends AbstractPersistenceEntity
{
    @ManyToOne
    @JoinColumn(name = "cource_id")
    private Course cource;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserAccount owner;

    @Column(nullable = false)
    private double price;

    private boolean paid;

    private LocalDateTime startTime;
    private LocalDateTime endTime;



}
