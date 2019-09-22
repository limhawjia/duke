package duke.command;

import duke.command.entities.CommandType;
import duke.command.entities.UndoAction;
import duke.task.TasksController;
import error.command.CommandCreationException;
import error.ui.UiException;
import ui.Ui;

import java.util.Optional;

/**
 * Interface for commands to be executed by the program.
 */
public abstract class Command {
    private CommandType type;
    protected Ui ui;
    protected TasksController tasksController;

    protected Command(CommandType type, Ui ui, TasksController tasksController) throws CommandCreationException {
        this.type = type;
        this.ui = ui;
        this.tasksController = tasksController;
    }

    /**
     * Carries out command execution logic.
     * @throws UiException if ui fails unexpectedly
     */
    public abstract void execute() throws UiException;

    /**
     * Returns a functional interface that undoes the actions carried out by this command.
     * @return optional of the functional interface
     */
    public abstract Optional<UndoAction> getUndoAction();

}
