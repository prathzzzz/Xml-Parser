import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class XmlDataSaver {
    private static final String FILE_NAME = "contact_data.xml";

    public static void saveDataToXml(ArrayList<ArrayList<String>> data) {
        try {
            File file = createOrGetFile();
            writeXmlContent(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File createOrGetFile() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    private static void writeXmlContent(File file, ArrayList<ArrayList<String>> data) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<contact_list>\n");

            for (ArrayList<String> rowData : data) {
                writeContactXml(writer, rowData);
            }

            writer.write("</contact_list>");
        }
    }

    private static void writeContactXml(FileWriter writer, ArrayList<String> rowData) throws IOException {
        writer.write("\t<contact>\n");
        writeXmlElement(writer, "name", rowData.get(0));
        writeXmlElement(writer, "phone", rowData.get(1));
        writeXmlElement(writer, "address", rowData.get(2));
        writeXmlElement(writer, "email", rowData.get(3));
        writeXmlElement(writer, "dob", rowData.get(4));
        writeXmlElement(writer, "gender", rowData.get(5));
        writer.write("\t</contact>\n");
    }

    private static void writeXmlElement(FileWriter writer, String elementName, String value) throws IOException {
        writer.write(String.format("\t\t<%s>%s</%s>\n", elementName, value, elementName));
    }
}