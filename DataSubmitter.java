import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DataSubmitter {
    public static boolean submitData(ArrayList<ArrayList<String>> data, DefaultTableModel model, String name, String phoneNo, String address, String email, String dob, boolean isMale) {
        String validationResult = validateData(name, phoneNo, email, dob);
        if (validationResult.equals("VALID")) {
            ArrayList<String> rowData = createRowData(name, phoneNo, address, email, dob, isMale);
            data.add(rowData);
            model.addRow(rowData.toArray());
            return true;
        } else {
            JOptionPane.showMessageDialog(null, validationResult, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private static ArrayList<String> createRowData(String name, String phoneNo, String address, String email, String dob, boolean isMale) {
        ArrayList<String> rowData = new ArrayList<>();
        rowData.add(name);
        rowData.add(phoneNo);
        rowData.add(address);
        rowData.add(email);
        rowData.add(dob);
        rowData.add(isMale ? "Male" : "Female");
        return rowData;
    }

    private static String validateData(String name, String phoneNo, String email, String dob) {
        if (!RegexValidator.isNameValid(name)) {
            return "Invalid Name. Please enter a valid name containing only letters and spaces.";
        }
        if (!RegexValidator.isPhoneNumberValid(phoneNo)) {
            return "Invalid Phone Number. Please enter a 10-digit phone number.";
        }
        if (!RegexValidator.isEmailValid(email)) {
            return "Invalid Email. Please enter a valid email address.";
        }
        if (!RegexValidator.isDobValid(dob)) {
            return "Invalid Date of Birth. Please enter a date in the format DD/MM/YYYY.";
        }
        return "VALID";
    }
}