package forex.trainer.ac.za.exception;

public class SystemException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final int exceptionNumber; // Add an exception number field

    public SystemException(int exceptionNumber, String message) {
        super(message);
        this.exceptionNumber = exceptionNumber;
    }

    public SystemException(int exceptionNumber, String message, Throwable cause) {
        super(message, cause);
        this.exceptionNumber = exceptionNumber;
    }

    public int getExceptionNumber() {
        return exceptionNumber; // Getter for the exception number
    }
}
