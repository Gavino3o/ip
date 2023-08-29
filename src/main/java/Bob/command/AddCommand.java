package Bob.command;

import Bob.enums.CommandType;
import Bob.exception.BobException;
import Bob.exception.BobInvalidCommandException;
import Bob.task.*;
import Bob.ui.TextUi;
import Bob.storage.StorageFile;

public class AddCommand extends Command {

    CommandType command;
    private String description, startDate, endDate;
    public AddCommand(CommandType command, String description, String startDate, String endDate) throws BobInvalidCommandException {
        if (command != CommandType.TODO && command != CommandType.DEADLINE && command != CommandType.EVENT) {
            throw new BobInvalidCommandException("You can only add tasks of type: Todo, Deadline and Event");
        }
        this.command = command;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void execute(TaskList taskList, StorageFile storageFile, TextUi ui) throws BobException {
        Task task = null;
        switch (command) {
            case TODO:
                task = new Todo((this.description));
                break;
            case DEADLINE:
                task = new Deadline(this.description, this.endDate);
                break;
            case EVENT:
                task = new Event(this.description, this.startDate, this.endDate);
                break;
        }
        taskList.addTask(task);
        assert task != null;
        ui.printAddMessage(task);
        storageFile.saveTasks(taskList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
