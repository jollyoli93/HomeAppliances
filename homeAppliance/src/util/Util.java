package util;

import exceptions.InvalidUserIdException;

/**
 * The Util class provides utility methods for common operations that can be used throughout the application.
 * It includes functionality such as parsing user input and handling errors related to user data.
 * 
 * @author 24862664
 */
public class Util {

    /**
     * Parses a user ID from a string and converts it into an integer.
     * If the string is not a valid integer, an InvalidUserIdException is thrown.
     * 
     * @param userIdString The string representation of the user ID.
     * @return The parsed user ID as an integer.
     * @throws InvalidUserIdException If the user ID is not a valid integer.
     */
    public static int parseUserId(String userIdString) throws InvalidUserIdException {
        try {
            return Integer.parseInt(userIdString);
        } catch (NumberFormatException e) {
            throw new InvalidUserIdException("Invalid user ID. Please enter a valid number.");
        }
    }
}
