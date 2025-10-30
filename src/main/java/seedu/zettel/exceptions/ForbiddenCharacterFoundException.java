package seedu.zettel.exceptions;

/**
 * Exception thrown when a forbidden character is found.
 * This exception is typically thrown when parsing input.
 */
public class ForbiddenCharacterFoundException extends ZettelException {
    /**
     * Constructs a new ForbiddenCharacterFoundException with the specified detail message.
     *
     * @param message The detail message explaining which note was not found.
     */
    public ForbiddenCharacterFoundException(String message) {
        super(message);
    }
}

