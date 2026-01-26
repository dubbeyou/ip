package snek.data.tasks;

import snek.data.exception.InvalidCommandSnekException;

/**
 * Enum representing the types of tasks.
 */
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");
    
    private final String code;

    TaskType(String code) { 
        this.code = code;
    }

    /**
     * Gets the code associated with the task type.
     *
     * @return The code of the task type.
     */
    public String getCode() { 
        return code;
    }

    /**
     * Returns the TaskType corresponding to the given code.
     *
     * @param code The code representing the task type.
     * @return The TaskType corresponding to the code.
     * @throws InvalidCommandSnekException If the code does not correspond to any TaskType.
     */
    public static TaskType fromCode(String code) throws InvalidCommandSnekException{
        for (TaskType type : TaskType.values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new InvalidCommandSnekException();
    }
}