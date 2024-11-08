package forex.trainer.ac.za.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class CreateBooking
{
    private UUID courseId;
    private UUID userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
