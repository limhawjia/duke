package task;

import java.util.List;

public class TaskListController {
    private List<Task> tasks;
    private TaskListView view;

    public TaskListController(List<Task> tasks) {
        this.tasks = tasks;
        view = new TaskListView();
    }

    public void addTask(Task task) {
        tasks.add(task);
        view.displayNewTask(task, tasks.size());
    }

    public void setTaskToDone(int index) {
        tasks.get(index).setDone(true);
        view.displayTaskDone(tasks.get(index));
    }

    public void displayAllTasks() {
        view.displayAllTasks(tasks);
    }

    public void deleteTask(int index) {
        Task deletedTask = tasks.get(index);
        tasks.remove(index);
        view.displayTaskDeleted(deletedTask, tasks.size());

    }
}
