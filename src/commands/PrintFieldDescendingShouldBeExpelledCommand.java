package commands;

import data.StudyGroup;
import exceptions.CollectionIsEmptyException;
import exceptions.StudyGroupNotFoundException;
import exceptions.WrongAmountOfArgumentsException;
import util.CollectionManager;
import util.Console;

public class PrintFieldDescendingShouldBeExpelledCommand extends Command {
    private CollectionManager collectionManager;

    public PrintFieldDescendingShouldBeExpelledCommand(CollectionManager collectionManager) {
        super("print_field_descending_should_be_expelled", " вывести значения поля shouldBeExpelled всех элементов в порядке убывания");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            System.out.println("значения поля shouldBeExpelled всех элементов в порядке убывания: " + collectionManager.sortedByShouldBeExpelled());
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            System.out.println("У этой команды нет параметров!");
        }
        return false;
    }
}
