package snek.commands;

import snek.data.exception.InvalidCommandSnekException;

public enum CommandType {
    LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, BYE;

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
