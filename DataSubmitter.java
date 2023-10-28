import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DataSubmitter {
    public static boolean submitData(ArrayList<ArrayList<String>> data, DefaultTableModel model, String name, String phoneNo, String address, String email, String dob, boolean isMale) {
        if (validateData(name, phoneNo, email, dob)) {
            ArrayList<String> rowData = new ArrayList<String>();
            rowData.add(name);
            rowData.add(phoneNo);
            rowData.add(address);
            rowData.add(email);
            rowData.add(dob);
            rowData.add(isMale ? "Male" : "Female");

            data.add(rowData);
            model.addRow(rowData.toArray());

            return true; // Data submitted successfully
        } else {
            JOptionPane.showMessageDialog(null, "Please enter valid data in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Data submission failed
        }
    }

    private static boolean validateData(String name, String phoneNo, String email, String dob) {
        // You can use the RegexValidator class or implement your validation logic here
        // For example, you can call RegexValidator methods:
        return RegexValidator.isNameValid(name) && RegexValidator.isPhoneNumberValid(phoneNo)
                && RegexValidator.isEmailValid(email) && RegexValidator.isDobValid(dob);
    }
}
