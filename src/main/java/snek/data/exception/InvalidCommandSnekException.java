package snek.data.exception;

import static snek.common.Messages.MESSAGE_INVALID_COMMAND;

public class InvalidCommandSnekException extends SnekException {
    public InvalidCommandSnekException() {
        super(MESSAGE_INVALID_COMMAND);
    }
}
