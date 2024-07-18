import java.util.regex.Pattern;

public class RegexValidator {
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String DOB_PATTERN = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d{2}$";
    private static final String PHONE_NUMBER_PATTERN = "^[0-9]{10}$";
    private static final String NAME_PATTERN = "^[A-Za-z\\s]+$";

    public static boolean isEmailValid(String email) {
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }

    public static boolean isDobValid(String dob) {
        return Pattern.compile(DOB_PATTERN).matcher(dob).matches();
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        return Pattern.compile(PHONE_NUMBER_PATTERN).matcher(phoneNumber).matches();
    }

    public static boolean isNameValid(String name) {
        return Pattern.compile(NAME_PATTERN).matcher(name).matches();
    }
}