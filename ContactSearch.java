

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

class ContactSearch {
    public static void searchContacts(ArrayList<ArrayList<String>> data, JTable table, String searchText) {
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
            JOptionPane.showMessageDialog(null, "No contact found with the given name.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}