public enum CommandType {
    LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE;

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
