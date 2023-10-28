import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class XmlDataSaver {
    public static void saveDataToXml(ArrayList<ArrayList<String>> data) {
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
}
