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
                deleteSelectedRows(table, data, selectedRows);
                XmlDataSaver.saveDataToXml(data);
                JOptionPane.showMessageDialog(null, "Contact(s) deleted successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select one or more contacts to delete.", "ERROR",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void deleteSelectedRows(JTable table, ArrayList<ArrayList<String>> data, int[] selectedRows) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            int selectedRowIndex = selectedRows[i];
            data.remove(selectedRowIndex);
            model.removeRow(selectedRowIndex);
        }
    }
}