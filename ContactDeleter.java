import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ContactDeleter {
    public static void deleteData(JTable table, ArrayList<ArrayList<String>> data) {
        int[] selectedRows = table.getSelectedRows();
        int numRowsToDelete = selectedRows.length;

        if (numRowsToDelete > 0) {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete the selected contact(s)?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                for (int i = numRowsToDelete - 1; i >= 0; i--) {
                    int selectedRowIndex = selectedRows[i];
                    data.remove(selectedRowIndex);
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(selectedRowIndex);
                }

                Ui.saveDataToXml(data); // Call saveDataToXml from the Ui class with the data parameter

                JOptionPane.showMessageDialog(null, "Contact(s) deleted successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select one or more contacts to delete.", "ERROR",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
