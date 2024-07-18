import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class ContactSearch {
    public static void searchContacts(ArrayList<ArrayList<String>> data, JTable table, String searchText) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        boolean contactFound = false;

        for (ArrayList<String> rowData : data) {
            String name = rowData.get(0);
            if (name.equalsIgnoreCase(searchText)) {
                model.addRow(rowData.toArray());
                contactFound = true;
            }
        }

        if (!contactFound) {
            JOptionPane.showMessageDialog(null, "No contact found with the given name.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}