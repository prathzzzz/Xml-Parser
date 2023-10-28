import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DataXmlLoader {
    public static void loadDataFromXml(ArrayList<ArrayList<String>> data, DefaultTableModel model) {
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

    private static String extractElementValue(String xml, String elementName) {
        Pattern pattern = Pattern.compile("<" + elementName + ">(.*?)</" + elementName + ">");
        Matcher matcher = pattern.matcher(xml);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
