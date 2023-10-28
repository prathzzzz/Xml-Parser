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
    XmlDataSaver xmlDataSaver = new XmlDataSaver();


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
        DataXmlLoader.loadDataFromXml(data, model);;
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Submit")) {
            dataSubmit();
        } else if (ae.getActionCommand().equals("Search")) {
            String searchText = searchField.getText();
            ContactSearch.searchContacts(data, table, searchText);
        } else if (ae.getActionCommand().equals("Update")) {
            updateData();
        } else if (ae.getActionCommand().equals("Delete")) {
            ContactDeleter.deleteData(table, data);
        }
    }

    
   
    private void dataSubmit() {
        String name = txtFieldName.getText();
        String phoneNo = txtFieldPhoneNo.getText();
        String address = txtFieldAddress.getText();
        String email = txtFieldEmail_ID.getText();
        String dob = txtFieldDob.getText();
        boolean isMale = maleRadioButton.isSelected();

        boolean dataSubmitted = DataSubmitter.submitData(data, model, name, phoneNo, address, email, dob, isMale);

        if (dataSubmitted) {
            // Data submitted successfully
            xmlDataSaver.saveDataToXml(data);
            clearForm();
            showDataSubmittedDialog();
        }
    }



    private void updateData() {
        int[] selectedRows = table.getSelectedRows();

        if (selectedRows.length != 1) {
            JOptionPane.showMessageDialog(this, "Please select one contact to update.", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int selectedRow = selectedRows[0];
        ArrayList<String> rowData = data.get(selectedRow);

      
        UpdateDialog updateDialog = new UpdateDialog(this, rowData,model, selectedRowIndex,data);

        updateDialog.setVisible(true);

        xmlDataSaver.saveDataToXml(data);

    }
  

    public void addContactData(ArrayList<String> rowData) {
        data.add(rowData);
        model.addRow(rowData.toArray());
        xmlDataSaver.saveDataToXml(data);
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
