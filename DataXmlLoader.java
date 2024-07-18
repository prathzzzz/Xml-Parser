import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DataXmlLoader {
    private static final String FILE_NAME = "contact_data.xml";

    public static void loadDataFromXml(ArrayList<ArrayList<String>> data, DefaultTableModel model) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try {
            String xmlContent = readXmlFile(file);
            parseXmlContent(xmlContent, data, model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readXmlFile(File file) throws IOException {
        StringBuilder xmlContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                xmlContent.append(line);
            }
        }
        return xmlContent.toString();
    }

    private static void parseXmlContent(String xml, ArrayList<ArrayList<String>> data, DefaultTableModel model) {
        Pattern pattern = Pattern.compile("<contact>.*?</contact>");
        Matcher matcher = pattern.matcher(xml);

        while (matcher.find()) {
            String contactXml = matcher.group();
            ArrayList<String> rowData = extractContactData(contactXml);
            data.add(rowData);
            model.addRow(rowData.toArray());
        }
    }

    private static ArrayList<String> extractContactData(String contactXml) {
        ArrayList<String> rowData = new ArrayList<>();
        rowData.add(extractElementValue(contactXml, "name"));
        rowData.add(extractElementValue(contactXml, "phone"));
        rowData.add(extractElementValue(contactXml, "address"));
        rowData.add(extractElementValue(contactXml, "email"));
        rowData.add(extractElementValue(contactXml, "dob"));
        rowData.add(extractElementValue(contactXml, "gender"));
        return rowData;
    }

    private static String extractElementValue(String xml, String elementName) {
        Pattern pattern = Pattern.compile("<" + elementName + ">(.*?)</" + elementName + ">");
        Matcher matcher = pattern.matcher(xml);
        return matcher.find() ? matcher.group(1) : "";
    }
}