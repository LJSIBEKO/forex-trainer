package forex.trainer.ac.za.dtos.base;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public abstract class DTO
{
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime updated;
}
