import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.ArrayList;
import java.io.*;
import java.util.regex.*;
import javax.swing.table.DefaultTableModel;

public class Ui extends JFrame implements ActionListener {
    JTable table;
    ArrayList<String> colheads;
    ArrayList<ArrayList<String>> data;

    JTabbedPane tabbedPane;
    JPanel addContactPanel;
    JPanel allContactsPanel;
    DefaultTableModel model;

    JLabel name, phoneNo, address, email_ID, dob, gender;
    JTextField txtFieldName, txtFieldPhoneNo, txtFieldAddress, txtFieldEmail_ID, txtFieldDob, searchField;
    JRadioButton maleRadioButton, femaleRadioButton;
    JButton submit, searchButton, updateButton, deleteButton;

    int selectedRowIndex;

    Ui() {
        super("Contact Details");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        colheads = new ArrayList<String>();
        colheads.add("Name");
        colheads.add("Phone Number");
        colheads.add("Address");
        colheads.add("Email ID");
        colheads.add("DOB");
        colheads.add("Gender");

        data = new ArrayList<ArrayList<String>>();

        model = new DefaultTableModel(colheads.toArray(new String[0]), 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        allContactsPanel = new JPanel(new BorderLayout());
        JScrollPane tableScrollPane = new JScrollPane(table);

        tabbedPane = new JTabbedPane();

        addContactPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        addContactPanel.add(new JLabel("Enter Your name"));
        addContactPanel.add(txtFieldName = new JTextField(10));

        addContactPanel.add(new JLabel("Enter Your Phone Number"));
        addContactPanel.add(txtFieldPhoneNo = new JTextField(10));

        addContactPanel.add(new JLabel("Enter Your Address"));
        addContactPanel.add(txtFieldAddress = new JTextField(10));

        addContactPanel.add(new JLabel("Enter Your Email ID"));
        addContactPanel.add(txtFieldEmail_ID = new JTextField(10));

        addContactPanel.add(new JLabel("Enter Your Date of Birth"));
        addContactPanel.add(txtFieldDob = new JTextField(10));

        addContactPanel.add(new JLabel("Gender"));

        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);

        addContactPanel.add(genderPanel);

        addContactPanel.add(submit = new JButton("Submit"));
        submit.addActionListener(this);

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(15);
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        updateButton.setActionCommand("Update");

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        deleteButton.setActionCommand("Delete");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        allContactsPanel.add(searchPanel, BorderLayout.NORTH);
        allContactsPanel.add(tableScrollPane, BorderLayout.CENTER);
        allContactsPanel.add(buttonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Add Contact", addContactPanel);
        tabbedPane.addTab("All Contacts", allContactsPanel);

        add(tabbedPane);
        loadDataFromXml();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Submit")) {
            dataSubmit();
        } else if (ae.getActionCommand().equals("Search")) {
            String searchText = searchField.getText();
            searchContacts(searchText);
        } else if (ae.getActionCommand().equals("Update")) {
            updateData();
        } else if (ae.getActionCommand().equals("Delete")) {
            deleteData();
        }
    }

    private void searchContacts(String searchText) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the table

        boolean contactFound = false; // Flag to check if any contact matches the search text

        for (ArrayList<String> rowData : data) {
            String name = rowData.get(0);
            if (name.equalsIgnoreCase(searchText)) { // Use equalsIgnoreCase for an exact case-insensitive match
                model.addRow(rowData.toArray());
                contactFound = true; // Set the flag to true when a contact is found
            }
        }

        if (!contactFound) {
            JOptionPane.showMessageDialog(this, "No contact found with the given name.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteData() {
        int[] selectedRows = table.getSelectedRows();
        int numRowsToDelete = selectedRows.length;
        if (numRowsToDelete > 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete the selected contact(s)?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                for (int i = numRowsToDelete - 1; i >= 0; i--) {
                    int selectedRowIndex = selectedRows[i];
                    data.remove(selectedRowIndex);
                    model.removeRow(selectedRowIndex);
                }

                saveDataToXml();
                JOptionPane.showMessageDialog(this, "Contact(s) deleted successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select one or more contacts to delete.", "ERROR",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void dataSubmit() {
        // Retrieve the values entered in the form
        String name = txtFieldName.getText();
        String phoneNo = txtFieldPhoneNo.getText();
        String address = txtFieldAddress.getText();
        String email = txtFieldEmail_ID.getText();
        String dob = txtFieldDob.getText();
        String gender = maleRadioButton.isSelected() ? "Male" : "Female";

        // Validate the data (you may want to add more validation)
        if (isNameValid(name) && isPhoneNumberValid(phoneNo) && isEmailValid(email) && isDobValid(dob)) {
            // Create a new row of data
            ArrayList<String> rowData = new ArrayList<String>();
            rowData.add(name);
            rowData.add(phoneNo);
            rowData.add(address);
            rowData.add(email);
            rowData.add(dob);
            rowData.add(gender);

            // Add the row to the data list and table
            data.add(rowData);
            model.addRow(rowData.toArray());

            // Save the data to XML
            saveDataToXml();

            // Clear the form
            clearForm();

            // Show a success message
            showDataSubmittedDialog();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter valid data in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveDataToXml() {
        try {
            File file = new File("contact_data.xml");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter writer = new FileWriter(file);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<contact_list>\n");

            for (ArrayList<String> rowData : data) {
                writer.write("\t<contact>\n");
                writer.write("\t\t<name>" + rowData.get(0) + "</name>\n");
                writer.write("\t\t<phone>" + rowData.get(1) + "</phone>\n");
                writer.write("\t\t<address>" + rowData.get(2) + "</address>\n");
                writer.write("\t\t<email>" + rowData.get(3) + "</email>\n");
                writer.write("\t\t<dob>" + rowData.get(4) + "</dob>\n");
                writer.write("\t\t<gender>" + rowData.get(5) + "</gender>\n");
                writer.write("\t</contact>\n");
            }

            writer.write("</contact_list>");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isNameValid(String name) {
        // Regex for a full name (alphabets with spaces)
        String regex = "^[A-Za-z\\s]+$";
        return name.matches(regex);
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // Regex for a valid phone number (10 digits)
        String regex = "^[0-9]{10}$";
        return phoneNumber.matches(regex);
    }

    private boolean isEmailValid(String email) {
        // Regex for a valid email address
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }

    private boolean isDobValid(String dob) {
        // Regex for a valid date of birth (dd/mm/yyyy format)
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d{2}$";
        return dob.matches(regex);
    }

    private void updateData() {
        int[] selectedRows = table.getSelectedRows();

        if (selectedRows.length != 1) {
            JOptionPane.showMessageDialog(this, "Please select one contact to update.", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int selectedRow = selectedRows[0];
        ArrayList<String> rowData = data.get(selectedRow);

        // Corrected constructor call for UpdateDialog
        UpdateDialog updateDialog = new UpdateDialog(this, rowData, model, selectedRowIndex);

        updateDialog.setVisible(true);

        // After the dialog is closed, you can save the data to XML
        saveDataToXml();
    }
    private boolean isFieldValid(String fieldData) {
        return !fieldData.isEmpty();
    }

    public void addContactData(ArrayList<String> rowData) {
        data.add(rowData);
        model.addRow(rowData.toArray());
        saveDataToXml();
        clearForm();
    }

    private void clearForm() {
        txtFieldName.setText("");
        txtFieldPhoneNo.setText("");
        txtFieldAddress.setText("");
        txtFieldEmail_ID.setText("");
        txtFieldDob.setText("");
        maleRadioButton.setSelected(false);
        femaleRadioButton.setSelected(false);
    }

    private void showDataSubmittedDialog() {
        JOptionPane.showMessageDialog(this, "Data submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadDataFromXml() {
        try {
            File file = new File("contact_data.xml");
            if (!file.exists()) {
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            String xmlContent = "";

            while ((line = reader.readLine()) != null) {
                xmlContent += line;
            }

            reader.close();

            String xml = xmlContent;

            Pattern pattern = Pattern.compile("<contact>.*?</contact>");
            Matcher matcher = pattern.matcher(xml);

            while (matcher.find()) {
                String contactXml = matcher.group();
                String name = extractElementValue(contactXml, "name");
                String phoneNo = extractElementValue(contactXml, "phone");
                String address = extractElementValue(contactXml, "address");
                String email = extractElementValue(contactXml, "email");
                String dob = extractElementValue(contactXml, "dob");
                String gender = extractElementValue(contactXml, "gender");

                ArrayList<String> rowData = new ArrayList<String>();
                rowData.add(name);
                rowData.add(phoneNo);
                rowData.add(address);
                rowData.add(email);
                rowData.add(dob);
                rowData.add(gender);

                data.add(rowData);
                model.addRow(rowData.toArray());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String extractElementValue(String xml, String elementName) {
        Pattern pattern = Pattern.compile("<" + elementName + ">(.*?)</" + elementName + ">");
        Matcher matcher = pattern.matcher(xml);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Ui();
            }
        });
    }
}
