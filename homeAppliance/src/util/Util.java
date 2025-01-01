package util;

import exceptions.InvalidUserIdException;

public class Util {
    public static int parseUserId(String userIdString) throws InvalidUserIdException {
        try {
            return Integer.parseInt(userIdString);
        } catch (NumberFormatException e) {
            throw new InvalidUserIdException("Invalid user ID. Please enter a valid number.");
        }
    }
}
