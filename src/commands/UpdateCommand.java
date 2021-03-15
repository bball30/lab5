package commands;

import data.Coordinates;
import data.FormOfEducation;
import data.Person;
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
 * 'update' command. Updates the information about selected studyGroup.
 */
public class UpdateCommand extends Command {
    private final CollectionManager collectionManager;
    private final Interactor interactor;

    public UpdateCommand(CollectionManager collectionManager, Interactor interactor) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
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
            if (argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            Integer id = Integer.parseInt(argument);
            StudyGroup studyGroup = collectionManager.getById(id);
            if (studyGroup == null) throw new StudyGroupNotFoundException();

            String name = studyGroup.getName();
            Coordinates coordinates = studyGroup.getCoordinates();
            LocalDateTime creationDate = studyGroup.getCreationDate();
            long studentsCount = studyGroup.getStudentsCount();
            long shouldBeExpelled = studyGroup.getShouldBeExpelled();
            int averageMark = studyGroup.getAverageMark();
            FormOfEducation formOfEducation = studyGroup.getFormOfEducation();
            Person groupAdmin = studyGroup.getGroupAdmin();

            collectionManager.removeFromCollection(studyGroup);

            if (interactor.askQuestion("Хотите изменить имя группы?"))
                name = interactor.askGroupName();
            if (interactor.askQuestion("Хотите изменить координаты группы?"))
                coordinates = interactor.askCoordinates();
            if (interactor.askQuestion("Хотите изменить количество студентов?"))
                studentsCount = interactor.askStudentsCount();
            if (interactor.askQuestion("Хотите изменить количество студентов, которые должны быть отчислены?"))
                shouldBeExpelled = interactor.askShouldBeExpelled();
            if (interactor.askQuestion("Хотите изменить средний балл студентов?"))
                averageMark = interactor.askAverageMark();
            if (interactor.askQuestion("Хотите изменить форму обучения?"))
                formOfEducation = interactor.askFormOfEducation();
            if (interactor.askQuestion("Хотите изменить админа группы?"))
                groupAdmin = interactor.askGroupAdmin();

            collectionManager.addToCollection(new StudyGroup(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    studentsCount,
                    shouldBeExpelled,
                    averageMark,
                    formOfEducation,
                    groupAdmin
            ));
            Console.println("Группа успешно изменена!");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            Console.printerror("ID должен быть представлен числом!");
        } catch (StudyGroupNotFoundException exception) {
            Console.printerror("Группы с таким ID в коллекции нет!");
        } catch (IncorrectInputInScriptException exception) {
            Console.printerror("Не удалось выполнить скрипт.");
        }
        return false;
    }
}
