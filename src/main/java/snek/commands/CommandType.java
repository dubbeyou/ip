package snek.commands;

import snek.data.exception.InvalidCommandSnekException;

/**
 * Enum representing the types of commands in the Snek application.
 */
public enum CommandType {
    LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE;

    /**
     * Returns the CommandType corresponding to the given token.
     *
     * @param token The string token representing the command type.
     * @return The CommandType corresponding to the token.
     * @throws InvalidCommandSnekException If the token does not correspond to any CommandType.
     */
    public static CommandType from(String token) throws InvalidCommandSnekException {
        if (token == null || token.trim().isEmpty()) {
            throw new InvalidCommandSnekException();
        }

        try {
            return CommandType.valueOf(token.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandSnekException();
        }
    }
}
