package commands;

import data.StudyGroup;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfArgumentsException;
import util.CollectionManager;
import util.Console;
import util.Interactor;

import java.time.LocalDateTime;

/**
 * 'add' command. Adds a new element to the collection.
 */
public class AddCommand extends Command {
    private final CollectionManager collectionManager;
    private final Interactor interactor;

    public AddCommand(CollectionManager collectionManager, Interactor interactor) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.interactor = interactor;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            collectionManager.addToCollection(
                    new StudyGroup(
                            collectionManager.generateNextId(),
                            interactor.askGroupName(),
                            interactor.askCoordinates(),
                            LocalDateTime.now(),
                            interactor.askStudentsCount(),
                            interactor.askShouldBeExpelled(),
                            interactor.askAverageMark(),
                            interactor.askFormOfEducation(),
                            interactor.askGroupAdmin()
                    )
            );
            Console.println("Группа успешно добавлена!");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (IncorrectInputInScriptException exception) {
            Console.printerror("Не удалось выполнить скрипт.");
        }
        return false;
    }
}
