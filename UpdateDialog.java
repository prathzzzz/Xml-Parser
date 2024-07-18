import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class UpdateDialog extends JDialog {
    private JTextField nameField, phoneNoField, addressField, emailField, dobField;
    private JButton updateButton, cancelButton;
    private ArrayList<String> rowData;
    private DefaultTableModel model;
    private int selectedRow;
    private ArrayList<ArrayList<String>> data;

    public UpdateDialog(JFrame parent, ArrayList<String> rowData, DefaultTableModel model, int selectedRow, ArrayList<ArrayList<String>> data) {
        super(parent, "Update Contact", true);
        this.rowData = rowData;
        this.model = model;
        this.selectedRow = selectedRow;
        this.data = data;

        initializeComponents();
        layoutComponents();
        addListeners();
    }

    private void initializeComponents() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());

        nameField = new JTextField(rowData.get(0));
        phoneNoField = new JTextField(rowData.get(1));
        addressField = new JTextField(rowData.get(2));
        emailField = new JTextField(rowData.get(3));
        dobField = new JTextField(rowData.get(4));

        updateButton = new JButton("Update");
        cancelButton = new JButton("Cancel");
    }

    private void layoutComponents() {
        JPanel updatePanel = new JPanel(new GridLayout(0, 2, 5, 5));

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
        updatePanel.add(updateButton);
        updatePanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(updatePanel, BorderLayout.CENTER);
    }

    private void addListeners() {
        updateButton.addActionListener(e -> {
            if (validateAndUpdateContact()) {
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    private boolean validateAndUpdateContact() {
        String name = nameField.getText();
        String phoneNo = phoneNoField.getText();
        String address = addressField.getText();
        String email = emailField.getText();
        String dob = dobField.getText();

        String validationResult = validateData(name, phoneNo, email, dob);
        if (validationResult.equals("VALID")) {
            updateContactData(name, phoneNo, address, email, dob);
            XmlDataSaver.saveDataToXml(data);
            return true;
        } else {
            JOptionPane.showMessageDialog(this, validationResult, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void updateContactData(String name, String phoneNo, String address, String email, String dob) {
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
    }

    private String validateData(String name, String phoneNo, String email, String dob) {
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