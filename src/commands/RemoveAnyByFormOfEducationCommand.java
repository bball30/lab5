package commands;

import data.FormOfEducation;
import data.StudyGroup;
import exceptions.CollectionIsEmptyException;
import exceptions.StudyGroupNotFoundException;
import exceptions.WrongAmountOfArgumentsException;
import util.CollectionManager;
import util.Console;

/**
 * 'remove_by_id' command. Removes the element by its ID.
 */
public class RemoveAnyByFormOfEducationCommand extends Command {
    private CollectionManager collectionManager;

    public RemoveAnyByFormOfEducationCommand(CollectionManager collectionManager) {
        super("remove_any_by_form_of_education formOfEducation", " удалить из коллекции один элемент, значение поля formOfEducation которого эквивалентно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            StudyGroup studyGroup = collectionManager.getByFormOfEducation(argument);
            if (studyGroup == null) throw new StudyGroupNotFoundException();
            collectionManager.removeFromCollection(studyGroup);
            Console.println("Группа успешно удалена!");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        } catch (StudyGroupNotFoundException exception) {
            Console.printerror("Группы с такой формой обучения в коллекции нет!");
        }
        return false;
    }
}
