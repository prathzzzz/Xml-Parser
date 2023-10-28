import java.util.regex.Pattern;

public class RegexValidator {
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String DOB_PATTERN = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d{2}$";

    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }

    public static boolean isDobValid(String dob) {
        Pattern pattern = Pattern.compile(DOB_PATTERN);
        return pattern.matcher(dob).matches();
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        // Regex for a valid phone number (10 digits)
        String regex = "^[0-9]{10}$";
        return phoneNumber.matches(regex);
    }

    public static boolean isNameValid(String name) {
        // Regex for a full name (alphabets with spaces)
        String regex = "^[A-Za-z\\s]+$";
        return name.matches(regex);
    }
}
