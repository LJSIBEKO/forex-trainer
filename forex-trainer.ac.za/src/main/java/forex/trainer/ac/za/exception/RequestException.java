package forex.trainer.ac.za.exception;

import lombok.Getter;

@Getter
public class RequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RequestException(String message) {
        super(message);
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
