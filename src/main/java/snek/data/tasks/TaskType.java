package snek.data.tasks;

public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");
    
    private final String code;

    TaskType(String code) { 
        this.code = code;
    }

    public String getCode() { 
        return code;
    }

    public static TaskType fromCode(String code) throws InvalidCommandSnekException{
        for (TaskType type : TaskType.values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new InvalidCommandSnekException();
    }
}