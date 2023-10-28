import java.util.regex.Pattern;

public class RegexValidator {
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String DOB_PATTERN = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d{2}$";
    public static final String PHONE_NUMBER_PATTERN = "^[0-9]{10}$";
    public static final String NAME_PATTERN = "^[A-Za-z\\s]+$";

    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }

    public static boolean isDobValid(String dob) {
        Pattern pattern = Pattern.compile(DOB_PATTERN);
        return pattern.matcher(dob).matches();
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        return pattern.matcher(phoneNumber).matches();
    }

    public static boolean isNameValid(String name) {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        return pattern.matcher(name).matches();
    }
}
