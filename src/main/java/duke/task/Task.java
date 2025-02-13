package duke.task;

import error.task.TaskCreationException;
import error.task.TaskModificationException;

import java.io.Serializable;
import java.util.UUID;

/**
 * Serializable abstract class to represent a task that is to be completed by the user. Its base attributes includes
 * 1. a UUID to uniquely identify each task
 * 2. a unique character code to represent each type of task
 * 3. a details String to serve as a descriptor for each task
 * 4. a TimeFrame within which the task is to be completed
 * 5. a boolean flag to represent if the task is completed.
 * The constructors of all tasks MUST follow the following formats
 * 1. Task(String details, LocalDateTime a, LocalDateTime b)
 * 2. Task(String details, LocalDateTime a).
 */
public abstract class Task implements Serializable, Cloneable {
    private static final long serialVersionUID = 6529685098267757690L;
    private static String TASK_IS_DONE_ICON = "✓";
    private static String TASK_NOT_DONE_ICON = "✘";

    private final UUID uuid;
    private final char uniqueCharCode;
    private String details;
    private TimeFrame timeFrame;
    private boolean isDone;

    /**
     * Constructor for the Task abstract class.
     * @param uniqueCharCode a unique char assigned to each task for identification.
     * @param details the details describing the task.
     * @param timeFrame the timeframe in which the task is to be completed.
     * @throws TaskCreationException if timeframe is not compatible with the task type.
     */
    public Task(char uniqueCharCode, String details, TimeFrame timeFrame) throws TaskCreationException {
        if (!this.isTimeFrameCompatible(timeFrame)) {
            throw new TaskCreationException("The given time frame is not compatible.");
        }

        this.uuid = UUID.randomUUID();
        this.uniqueCharCode = uniqueCharCode;
        this.details = details;
        this.timeFrame = timeFrame;
        this.isDone = false;
    }

    public final UUID getUuid() {
        return this.uuid;
    }

    public final char getUniqueCharCode() {
        return this.uniqueCharCode;
    }

    public String getTaskDetails() {
        return details;
    }

    public TimeFrame getTaskTimeFrame() {
        return this.timeFrame;
    }

    public boolean isTaskDone() {
        return this.isDone;
    }

    public void setTaskAsDone(boolean done) {
        this.isDone = done;
    }

    /**
     * Returns a nicely formatted description of the task that reflects all of its base attributes including:
     * 1. its unique character code
     * 2. whether it is done
     * 3. its details
     * 4. (optional) a description of its TimeFrame if it exists
     * @return the nicely formatted description of the task as a string.
     */
    public final String getTaskDescription() {
        String taskDescription = String.format("[%s][%s] %s",
                this.uniqueCharCode,
                this.isDone ? TASK_IS_DONE_ICON : TASK_NOT_DONE_ICON,
                this.details);

        if (!this.timeFrame.hasDescription()) {
            return taskDescription;
        }

        return String.format("%s (%s)", taskDescription, timeFrame.getDescription());
    }

    /**
     * Returns true if both tasks have all of the same fields..
     * @param o object to be compared to the Task instance.
     * @return true if the two tasks are the same.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Task) {
            return ((Task) o).getTaskDetails().equals(this.details)
                    && ((Task) o).getTaskTimeFrame().equals(this.timeFrame)
                    && ((Task) o).getUniqueCharCode() == this.uniqueCharCode;
        } else {
            return false;
        }
    }

    /**
     * Returns a deep copy of the task instance other than its UUID which is immutable regardless.
     * @return a deep copy of the task instance.
     * @throws CloneNotSupportedException if cloning of the task instance fails.
     */
    @Override
    public Task clone() throws CloneNotSupportedException {
        Task clone = (Task) super.clone();
        clone.timeFrame = new TimeFrame(timeFrame.getStart(), timeFrame.getEnd());
        return clone;
    }

    /**
     * Method used by task to check if a TimeFrame is compatible before any calls to setTimeFrame is carried out.
     * A task's TimeFrame should always follow a fixed pattern. For instance, a task to be completed at a particular
     * time should have the same start and end times, a task to be completed after a certain time should have its start
     * time as null.
     * @param timeframe new TimeFrame instance to update the task with.
     * @return true if the TimeFrame instance is compatible.
     */
    public abstract boolean isTimeFrameCompatible(TimeFrame timeframe);
}
