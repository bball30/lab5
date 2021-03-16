package run;

import commands.*;
import util.*;


import java.util.Scanner;

/**
 * Main application class. Creates all instances and runs the program.
 */
public class Main {
    public static final String INPUT_COMMAND = "$ ";
    public static final String INPUT_INFO = "> ";

    public static void main(String[] args) {
        try (Scanner userScanner = new Scanner(System.in)) {
            final String envVariable = "LAB5";

            Interactor interactor = new Interactor(userScanner);
            FileManager fileManager = new FileManager(envVariable);
            CollectionManager collectionManager = new CollectionManager(fileManager);
            CommandManager commandManager = new CommandManager(
                    new HelpCommand(),
                    new InfoCommand(collectionManager),
                    new ShowCommand(collectionManager),
                    new AddCommand(collectionManager, interactor),
                    new UpdateCommand(collectionManager, interactor),
                    new RemoveByIdCommand(collectionManager),
                    new ClearCommand(collectionManager),
                    new SaveCommand(collectionManager),
                    new ExecuteScriptCommand(),
                    new ExitCommand(),
                    new RemoveGreaterCommand(collectionManager, interactor),
                    new RemoveLowerCommand(collectionManager, interactor),
                    new HistoryCommand(),
                    new RemoveAnyByFormOfEducationCommand(collectionManager),
                    new PrintUniqueGroupAdminCommand(collectionManager),
                    new PrintFieldDescendingShouldBeExpelledCommand(collectionManager));
            Console console = new Console(commandManager, userScanner, interactor);
            console.interactiveMode();
        }
    }
}
