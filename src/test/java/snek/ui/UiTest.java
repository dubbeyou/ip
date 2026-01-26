package snek.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static snek.common.Messages.MESSAGE_BYE;
import static snek.common.Messages.MESSAGE_HELLO;
import static snek.common.Messages.MESSAGE_LINEBREAK;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UiTest {
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private Ui ui;

    @BeforeEach
    public void setUp() {
        ui = new Ui();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void print_printsFormattedMessage() {
        String testInput = "Hello, World!";
        String expected = MESSAGE_LINEBREAK + "\n" + testInput + "\n" + MESSAGE_LINEBREAK + System.lineSeparator();
        ui.print(testInput);
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void printError_printsFormattedErrorMessage() {
        String testInput = "Error occurred!";
        String expected = MESSAGE_LINEBREAK + "\n" + testInput + "\n" + MESSAGE_LINEBREAK + System.lineSeparator();
        ui.printError(testInput);
        assertEquals(expected, errContent.toString());
    }

    @Test
    public void printHelloMessage_printsHelloMessage() {
        String expected = MESSAGE_LINEBREAK + "\n" + MESSAGE_HELLO + "\n" + MESSAGE_LINEBREAK + System.lineSeparator();
        ui.printHelloMessage();
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void printByeMessage_printsByeMessage() {
        String expected = MESSAGE_LINEBREAK + "\n" + MESSAGE_BYE + "\n" + MESSAGE_LINEBREAK + System.lineSeparator();
        ui.printByeMessage();
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void readCommand_readsInputCommand() {
        String expected = "test command";
        String input = "test command" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Ui testUi = new Ui();
        String actual = testUi.readCommand();
        assertEquals(expected, actual);
        System.setIn(System.in);
    }
}
