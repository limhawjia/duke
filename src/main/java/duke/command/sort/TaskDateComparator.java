package duke.command.sort;

import duke.task.Task;

import java.util.Comparator;

public class TaskDateComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        return task1.getTaskTimeFrame().compareTo(task2.getTaskTimeFrame());
    }
}