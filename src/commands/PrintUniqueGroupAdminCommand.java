package commands;

import exceptions.WrongAmountOfArgumentsException;
import util.CollectionManager;

public class PrintUniqueGroupAdminCommand extends Command{
    private CollectionManager collectionManager;

    public PrintUniqueGroupAdminCommand(CollectionManager collectionManager) {
        super("print_unique_group_admin", " вывести уникальные значения поля groupAdmin всех элементов в коллекции");
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
            System.out.println("уникальные значения поля groupAdmin всех элементов в коллекции: " + collectionManager.uniqueGroupAdmin());
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            System.out.println("У этой команды нет параметров!");
        }
        return false;
    }
}
