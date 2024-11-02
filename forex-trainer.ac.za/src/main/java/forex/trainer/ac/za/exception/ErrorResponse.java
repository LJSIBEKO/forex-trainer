package forex.trainer.ac.za.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timeStamp;
    private String message;
    private String details;

}
