package commands;

import data.StudyGroup;
import exceptions.CollectionIsEmptyException;
import exceptions.IncorrectInputInScriptException;
import exceptions.StudyGroupNotFoundException;
import exceptions.WrongAmountOfArgumentsException;
import util.CollectionManager;
import util.Console;
import util.Interactor;

import java.time.LocalDateTime;

/**
 * 'remove_greater' command. Removes elements greater than user entered.
 */
public class RemoveGreaterCommand extends Command {
    private CollectionManager collectionManager;
    private Interactor interactor;

    public RemoveGreaterCommand(CollectionManager collectionManager, Interactor interactor) {
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
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
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            StudyGroup studyGroupToFind = new StudyGroup(
                    -1,
                    interactor.askGroupName(),
                    interactor.askCoordinates(),
                    LocalDateTime.now(),
                    interactor.askStudentsCount(),
                    interactor.askShouldBeExpelled(),
                    interactor.askAverageMark(),
                    interactor.askFormOfEducation(),
                    interactor.askGroupAdmin()
            );
            StudyGroup studyGroup = collectionManager.getByValue(studyGroupToFind);
            if (studyGroup == null) throw new StudyGroupNotFoundException();
            int collectionSize = collectionManager.collectionSize();
            collectionManager.removeGreater(studyGroup);
            Console.print("Удалено групп: " + (collectionManager.collectionSize() - collectionSize));
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        } catch (StudyGroupNotFoundException exception) {
            Console.printerror("Группы с такими параметрами в коллекции нет!");
        } catch (IncorrectInputInScriptException exception) {
            Console.printerror("Не удалось выполнить скрипт.");
        }
        return false;
    }
}
