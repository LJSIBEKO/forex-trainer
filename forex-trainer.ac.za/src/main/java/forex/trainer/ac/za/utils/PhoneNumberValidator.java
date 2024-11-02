package forex.trainer.ac.za.utils;

import forex.trainer.ac.za.exception.RequestException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator
{
    // Regular expression for validating South African phone numbers
    private static final String PHONE_NUMBER_REGEX = "^(\\+27|0)(\\d{9})$";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    /**
     * Validates and formats the phone number.
     *
     * @param phoneNumber The phone number to validate.
     * @return The formatted phone number starting with '0', or null if invalid.
     */
    public static String validateAndFormatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return null; // Null or empty phone numbers are not valid
        }

        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        if (matcher.matches()) {
            // If the phone number starts with +27, replace it with 0
            if (phoneNumber.startsWith("+27")) {
                return "0" + phoneNumber.substring(3); // Remove +27 and add 0
            }
            return phoneNumber; // If it starts with 0, return as is
        }

        throw new RequestException("Phone number is not valid");
    }
}
