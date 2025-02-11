package bob.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UiTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Ui ui;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        ui = new Ui();
    }

    @Test
    void getUserInput_emptyInput_returnsEmptyArray() {
        provideInput("\n");
        String[] result = ui.getUserInput();
        assertEquals(0, result.length);
    }

    @Test
    void getUserInput_singleWord_returnsArrayWithOneElement() {
        provideInput("test\n");
        String[] result = ui.getUserInput();
        assertEquals(1, result.length);
        assertEquals("test", result[0]);
    }

    @Test
    void getUserInput_multipleWords_returnsArrayWithCorrectElements() {
        provideInput("todo Buy groceries\n");
        String[] result = ui.getUserInput();
        assertEquals(3, result.length);
        assertEquals("todo", result[0]);
        assertEquals("Buy", result[1]);
        assertEquals("groceries", result[2]);
    }

    @Test
    void wrapText_stringBuilder_printsWithBorders() {
        StringBuilder message = new StringBuilder("Test message");
        ui = new Ui();
        ui.wrapText(message);

        String output = outContent.toString();
        assertTrue(output.contains("-----------------------------"));
        assertTrue(output.contains("Test message"));
        assertTrue(output.trim().split("\n").length >= 3);
    }

    @Test
    void wrapText_string_printsWithBorders() {
        ui = new Ui();
        ui.wrapText("Test message");

        String output = outContent.toString();
        assertTrue(output.contains("-----------------------------"));
        assertTrue(output.contains("Test message"));
        assertTrue(output.trim().split("\n").length >= 3);
    }

    @Test
    void greet_printsWelcomeMessage() {
        ui = new Ui();
        ui.greet();

        String output = outContent.toString();
        assertTrue(output.contains("Bob"));
        assertTrue(output.contains("Hello"));
        assertTrue(output.contains("What can I do for you"));
    }

    @Test
    void getUserInput_promptDisplayed() {
        provideInput("test\n");
        ui.getUserInput();
        assertTrue(outContent.toString().contains(">>>"));
    }
}