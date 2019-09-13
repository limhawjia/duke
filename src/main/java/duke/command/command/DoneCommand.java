package duke.command.command;

import duke.command.Command;
import duke.command.CommandType;
import duke.command.UndoAction;
import duke.task.Task;
import error.command.CommandCreationException;
import error.ui.UiException;

import java.util.Optional;

/**
 * Command to mark tasks as done.
 */
public class DoneCommand extends Command {
    private int completedTaskIndex;
    private Optional<Task> done;
    private static final String INVALID_INDEX_MESSAGE = "☹ OOPS!!! PLease enter a valid index! :-(";

    /**
     * Constructor for DoneCommand.
     * @param doneIndex index of task to be marked as done
     * @throws CommandCreationException if index argument is invalid
     */
    public DoneCommand(String doneIndex) throws CommandCreationException {
        super(CommandType.DONE);
        try {
            completedTaskIndex = Integer.parseInt(doneIndex) - 1;
        } catch (NumberFormatException e) {
            throw new CommandCreationException(INVALID_INDEX_MESSAGE);
        }
    }

    /**
     * Sets task to done.
     */
    @Override
    public void execute() throws UiException {
        done = tasksController.setTaskToDone(completedTaskIndex);
    }

    /**
     * Returns UndoAction to unmark task as done.
     * @return empty if task was not successfully marked
     */
    @Override
    public Optional<UndoAction> getUndoAction() {
        if (done.isEmpty()) {
            return Optional.empty();
        } else {
            Task restore = done.get();
            return Optional.of(() -> tasksController.setTaskToUndoneByUuid(restore.getUuid()));
        }
    }
}
