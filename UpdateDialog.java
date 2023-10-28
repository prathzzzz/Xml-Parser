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
    private ArrayList<ArrayList<String>> data;
    

    public UpdateDialog(JFrame parent, ArrayList<String> rowData, DefaultTableModel model, int selectedRow, ArrayList<ArrayList<String>> data) {
        super(parent, "Update Contact", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        this.rowData = rowData;
        this.model = model;
        this.selectedRow = selectedRow;
        this.data = data;


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

    public boolean validateAndUpdateContact() {
        String name = nameField.getText();
        String phoneNo = phoneNoField.getText();
        String address = addressField.getText();
        String email = emailField.getText();
        String dob = dobField.getText();

        if (name.isEmpty() || phoneNo.isEmpty() || address.isEmpty() || email.isEmpty() || dob.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!RegexValidator.isDobValid(dob)) {
            JOptionPane.showMessageDialog(this, "Invalid date of birth format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

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

        XmlDataSaver.saveDataToXml(data);

        return true;
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(RegexValidator.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
