public enum Command {
    LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE;

    public static Command from(String token) throws InvalidCommandSnekException {
        if (token == null || token.trim().isEmpty()) {
            throw new InvalidCommandSnekException(token);
        }
        
        try {
            return Command.valueOf(token.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandSnekException(token);
        }
    }
}
