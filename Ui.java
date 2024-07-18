import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.ArrayList;

public class Ui extends JFrame implements ActionListener {
    private JTable table;
    private ArrayList<String> colheads;
    private ArrayList<ArrayList<String>> data;

    private JTabbedPane tabbedPane;
    private JPanel addContactPanel;
    private JPanel allContactsPanel;
    private DefaultTableModel model;

    private JTextField txtFieldName, txtFieldPhoneNo, txtFieldAddress, txtFieldEmail_ID, txtFieldDob, searchField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private JButton submit, searchButton, updateButton, deleteButton, refreshButton;

    private int selectedRowIndex;
    public Ui() {
        super("Contact Details");
        initializeComponents();
        layoutComponents();
        addListeners();
        loadDataFromXml();
        setVisible(true);
    }

    private void initializeComponents() {
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        colheads = new ArrayList<>();
        colheads.add("Name");
        colheads.add("Phone Number");
        colheads.add("Address");
        colheads.add("Email ID");
        colheads.add("DOB");
        colheads.add("Gender");

        data = new ArrayList<>();

        model = new DefaultTableModel(colheads.toArray(new String[0]), 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        tabbedPane = new JTabbedPane();

        txtFieldName = new JTextField(10);
        txtFieldPhoneNo = new JTextField(10);
        txtFieldAddress = new JTextField(10);
        txtFieldEmail_ID = new JTextField(10);
        txtFieldDob = new JTextField(10);
        searchField = new JTextField(15);

        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");

        submit = new JButton("Submit");
        searchButton = new JButton("Search");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");

        new XmlDataSaver();
    }

    private void layoutComponents() {
        setLayout(new FlowLayout());

        addContactPanel = createAddContactPanel();
        allContactsPanel = createAllContactsPanel();

        tabbedPane.addTab("Add Contact", addContactPanel);
        tabbedPane.addTab("All Contacts", allContactsPanel);

        add(tabbedPane);
    }

    private JPanel createAddContactPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));

        panel.add(new JLabel("Enter Your name"));
        panel.add(txtFieldName);
        panel.add(new JLabel("Enter Your Phone Number"));
        panel.add(txtFieldPhoneNo);
        panel.add(new JLabel("Enter Your Address"));
        panel.add(txtFieldAddress);
        panel.add(new JLabel("Enter Your Email ID"));
        panel.add(txtFieldEmail_ID);
        panel.add(new JLabel("Enter Your Date of Birth"));
        panel.add(txtFieldDob);
        panel.add(new JLabel("Gender"));
        panel.add(createGenderPanel());
        panel.add(submit);

        return panel;
    }

    private JPanel createGenderPanel() {
        JPanel genderPanel = new JPanel();
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        return genderPanel;
    }

    private JPanel createAllContactsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane tableScrollPane = new JScrollPane(table);

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void addListeners() {
        submit.addActionListener(this);
        searchButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        refreshButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            dataSubmit();
        } else if (ae.getSource() == searchButton) {
            searchContacts();
        } else if (ae.getSource() == updateButton) {
            updateData();
        } else if (ae.getSource() == deleteButton) {
            ContactDeleter.deleteData(table, data);
        } else if (ae.getSource() == refreshButton) {
            loadDataFromXml();
        }
    }

    private void loadDataFromXml() {
        data.clear();
        model.setRowCount(0);
        DataXmlLoader.loadDataFromXml(data, model);
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
            XmlDataSaver.saveDataToXml(data);
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

        UpdateDialog updateDialog = new UpdateDialog(this, rowData, model, selectedRowIndex, data);
        updateDialog.setVisible(true);

        XmlDataSaver.saveDataToXml(data);
    }

    private void searchContacts() {
        String searchText = searchField.getText();
        ContactSearch.searchContacts(data, table, searchText);
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

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Ui();
            }
        });
    }
}