package forex.trainer.ac.za.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ErrorResponse> handleRequestException(RequestException ex) {
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(),ex.getMessage(),""), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<Object> handleSystemException(SystemException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getExceptionNumber(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Inner class to hold exception response details
    public static class ExceptionResponse {
        private final int exceptionNumber;
        private final String message;

        public ExceptionResponse(int exceptionNumber, String message) {
            this.exceptionNumber = exceptionNumber;
            this.message = message;
        }

        public int getExceptionNumber() {
            return exceptionNumber;
        }

        public String getMessage() {
            return message;
        }
    }
}
