package chatbot;

import chatbot.exceptions.DeadlineMissingFieldException;
import chatbot.exceptions.DeleteMissingFieldException;
import chatbot.exceptions.EventMissingFieldException;
import chatbot.exceptions.FindMissingFieldException;
import chatbot.exceptions.InvalidTaskIndexException;
import chatbot.exceptions.MarkMissingFieldException;
import chatbot.exceptions.TodoMissingFieldException;
import chatbot.tasks.DeadlineTask;
import chatbot.tasks.EventTask;
import chatbot.tasks.Task;
import chatbot.tasks.ToDoTask;

/**
 * Class that contains static methods to help parse user commands.
 */
public class Parser {
    /**
     * Parse "find" command from the user.
     * @param command String which is the user's command
     * @return task name provided as the search string
     * @throws FindMissingFieldException when the command does not contain any task name
     */
    public static String parseFindCommand(String command) throws FindMissingFieldException {
        assert (command.substring(0, 4).equals("find"));
        try {
            String name = command.substring(5);
            if (name.isEmpty()) {
                throw new FindMissingFieldException();
            }
            return name;
        } catch (IndexOutOfBoundsException e) {
            throw new FindMissingFieldException();
        }
    }

    /**
     * Parse "mark" or "unmark" command from the user.
     * @param commandWords String array that contains the words in the command in order
     * @return index of the task to mark or unmark
     * @throws MarkMissingFieldException when the command is not in the expected format (2 words)
     * @throws InvalidTaskIndexException when the second word cannot be interpreted as an integer
     */
    public static int parseMarkCommand(String[] commandWords)
            throws MarkMissingFieldException, InvalidTaskIndexException {
        assert (commandWords[0].equals("mark") || commandWords[0].equals("unmark"));
        if (commandWords.length != 2) {
            throw new MarkMissingFieldException();
        }
        try {
            return Integer.parseInt(commandWords[1]);
        } catch (NumberFormatException e) {
            throw new InvalidTaskIndexException();
        }
    }

    /**
     * Parse "delete" command from the user.
     * @param commandWords String array that contains the words in the command in order
     * @return index of the task to delete
     * @throws DeleteMissingFieldException when the command is not in the expected format (2 words)
     * @throws InvalidTaskIndexException when the second word cannot be interpreted as an integer
     */
    public static int parseDeleteCommand(String[] commandWords)
            throws DeleteMissingFieldException, InvalidTaskIndexException {
        assert (commandWords[0].equals("delete"));
        if (commandWords.length != 2) {
            throw new DeleteMissingFieldException();
        }
        try {
            return Integer.parseInt(commandWords[1]);
        } catch (NumberFormatException e) {
            throw new InvalidTaskIndexException();
        }
    }

    /**
     * Parse "todo" command from the user.
     * @param command String which is the user's command
     * @return TodoTask instantiated with the name specified in the command
     * @throws TodoMissingFieldException when no task name is specified in the command
     */
    public static Task parseTodoTaskCommand(String command) throws TodoMissingFieldException {
        assert (command.substring(0, 4).equals("todo"));
        try {
            String name = command.substring(5);
            if (name.isEmpty()) {
                throw new TodoMissingFieldException();
            }
            return new ToDoTask(command.substring(5));
        } catch (IndexOutOfBoundsException e) {
            throw new TodoMissingFieldException();
        }
    }

    /**
     * Parse "deadline" command from the user.
     * @param command String which is the user's command
     * @return DeadlineTask instantiated with the name and deadline specified in the command
     * @throws DeadlineMissingFieldException when the command does not contain name or deadline
     */
    public static Task parseDeadlineTaskCommand(String command) throws DeadlineMissingFieldException {
        assert (command.substring(0, 8).equals("deadline"));
        int idOfBy = command.indexOf("/by");
        if (idOfBy == -1) {
            throw new DeadlineMissingFieldException();
        }
        try {
            String name = command.substring(9, idOfBy - 1);
            String deadline = command.substring(idOfBy + 4);
            if (name.isEmpty() || deadline.isEmpty()) {
                throw new DeadlineMissingFieldException();
            }
            return new DeadlineTask(name, deadline);
        } catch (IndexOutOfBoundsException e) {
            throw new DeadlineMissingFieldException();
        }
    }

    /**
     * Parse "event" command from the user.
     * @param command String which is the user's command
     * @return EventTask instantiated with the name, from (time), to (time) specified in the command
     * @throws EventMissingFieldException when the command does not contain name, from or to
     */
    public static Task parseEventTaskCommand(String command) throws EventMissingFieldException {
        assert (command.substring(0, 5).equals("event"));
        int idOfFrom = command.indexOf("/from");
        int idOfTo = command.indexOf("/to");
        if (idOfFrom == -1 || idOfTo == -1) {
            throw new EventMissingFieldException();
        }
        try {
            String name = command.substring(6, idOfFrom - 1);
            String from = command.substring(idOfFrom + 6, idOfTo - 1);
            String to = command.substring(idOfTo + 4);
            if (name.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new EventMissingFieldException();
            }
            return new EventTask(name, from, to);
        } catch (IndexOutOfBoundsException e) {
            throw new EventMissingFieldException();
        }
    }
}
