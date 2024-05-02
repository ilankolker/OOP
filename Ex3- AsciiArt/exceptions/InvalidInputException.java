package exceptions;

/**
 * Custom exception class for handling invalid input.
 * This exception is thrown when an operation receives input that is not valid or in the expected format.
 */
public class InvalidInputException extends Exception {
    /**
     * the errorMessage of some invalid input.
     */
    private String errMessage;
    /**
     * Constructs a new InvalidInputException with the specified error message.
     * @param message the error message
     */
    public InvalidInputException(String message) {
        this.errMessage = message;
    }

    /**
     * Sets the error message for this exception.
     * @param errMessage the error message
     */
    public void setMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    /**
     * Returns the error message associated with this exception.
     * @return the error message
     */
    @Override
    public String getMessage() {
        return errMessage;
    }
}
