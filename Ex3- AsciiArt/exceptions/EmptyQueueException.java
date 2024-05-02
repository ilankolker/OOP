package exceptions;

/**
 * Custom exception class for handling empty queue.
 * Exception thrown when attempting to perform an operation on an empty queue.
 */
public class EmptyQueueException extends Exception {
    /**
     * Constructs a new EmptyQueueException with the specified error message.
     *
     * @param message the error message
     */
    public EmptyQueueException(String message) {
        super(message);
    }
}
