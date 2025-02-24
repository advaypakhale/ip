package bob.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class IllegalCommandExceptionTest {

    @Test
    void shouldFormatMessageWithDashes() {
        // Given
        String errorMessage = "Invalid command format";
        String expectedFormattedMessage = "-----------------------------\n" + "Invalid command format\n"
                + "-----------------------------";

        // When
        IllegalCommandException exception = new IllegalCommandException(errorMessage);

        // Then
        assertEquals(expectedFormattedMessage, exception.getMessage());
    }

    @Test
    void shouldHandleEmptyMessage() {
        // Given
        String errorMessage = "";
        String expectedFormattedMessage = "-----------------------------\n" + "\n" + "-----------------------------";

        // When
        IllegalCommandException exception = new IllegalCommandException(errorMessage);

        // Then
        assertEquals(expectedFormattedMessage, exception.getMessage());
    }

    @Test
    void shouldHandleMultilineMessage() {
        // Given
        String errorMessage = "Line 1\nLine 2\nLine 3";
        String expectedFormattedMessage = "-----------------------------\n" + "Line 1\nLine 2\nLine 3\n"
                + "-----------------------------";

        // When
        IllegalCommandException exception = new IllegalCommandException(errorMessage);

        // Then
        assertEquals(expectedFormattedMessage, exception.getMessage());
    }

    @Test
    void shouldPreserveMessageInCause() {
        // Given
        String errorMessage = "Test message";
        Exception cause = new RuntimeException("Original cause");

        // When
        IllegalCommandException exception = new IllegalCommandException(errorMessage);
        exception.initCause(cause);

        // Then
        assertNotNull(exception.getCause());
        assertEquals("Original cause", exception.getCause().getMessage());
    }
}