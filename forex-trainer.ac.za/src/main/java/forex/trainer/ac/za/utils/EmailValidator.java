package forex.trainer.ac.za.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator
{
    // Regular expression for validating email
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Validates the email address format.
     *
     * @param email The email address to validate.
     * @return true if the email format is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false; // Null or empty emails are not valid
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches(); // Returns true if the email matches the regex
    }
}
