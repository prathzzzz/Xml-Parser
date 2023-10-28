import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.*;
import javax.swing.table.DefaultTableModel;


public class UpdateDialog extends JDialog {
    private JTextField nameField, phoneNoField, addressField, emailField, dobField;
    private JButton updateButton, cancelButton;
    private ArrayList<String> rowData;
    private DefaultTableModel model;
    private int selectedRow;

    UpdateDialog(JFrame parent, ArrayList<String> rowData, DefaultTableModel model, int selectedRow) {
        super(parent, "Update Contact", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        this.rowData = rowData;
        this.model = model;
        this.selectedRow = selectedRow;

        JPanel updatePanel = new JPanel(new GridLayout(0, 2, 5, 5));

        nameField = new JTextField(rowData.get(0));
        phoneNoField = new JTextField(rowData.get(1));
        addressField = new JTextField(rowData.get(2));
        emailField = new JTextField(rowData.get(3));
        dobField = new JTextField(rowData.get(4));

        updatePanel.add(new JLabel("Name"));
        updatePanel.add(nameField);

        updatePanel.add(new JLabel("Phone Number"));
        updatePanel.add(phoneNoField);

        updatePanel.add(new JLabel("Address"));
        updatePanel.add(addressField);

        updatePanel.add(new JLabel("Email ID"));
        updatePanel.add(emailField);

        updatePanel.add(new JLabel("Date of Birth"));
        updatePanel.add(dobField);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (validateAndUpdateContact()) {
                    dispose();
                }
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });

        updatePanel.add(updateButton);
        updatePanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(updatePanel, BorderLayout.CENTER);
    }

    private boolean validateAndUpdateContact() {
        String name = nameField.getText();
        String phoneNo = phoneNoField.getText();
        String address = addressField.getText();
        String email = emailField.getText();
        String dob = dobField.getText();

        // Perform validation for each field
        if (name.isEmpty() || phoneNo.isEmpty() || address.isEmpty() || email.isEmpty() || dob.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate the email using a regular expression
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate the date of birth using a regular expression (adjust the pattern as needed)
        if (!isValidDOB(dob)) {
            JOptionPane.showMessageDialog(this, "Invalid date of birth format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Update the contact data if all validations pass
        rowData.set(0, name);
        rowData.set(1, phoneNo);
        rowData.set(2, address);
        rowData.set(3, email);
        rowData.set(4, dob);

        model.setValueAt(name, selectedRow, 0);
        model.setValueAt(phoneNo, selectedRow, 1);
        model.setValueAt(address, selectedRow, 2);
        model.setValueAt(email, selectedRow, 3);
        model.setValueAt(dob, selectedRow, 4);

        // Save data to XML here if needed

        return true;
    }

    // Regular expression pattern for a valid email address
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    // Regular expression pattern for a valid date of birth (YYYY-MM-DD)
    private static final String DOB_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    // Check if the email is valid based on the regular expression
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Check if the date of birth is valid based on the regular expression
    private boolean isValidDOB(String dob) {
        Pattern pattern = Pattern.compile(DOB_REGEX);
        Matcher matcher = pattern.matcher(dob);
        return matcher.matches();
    }
}
