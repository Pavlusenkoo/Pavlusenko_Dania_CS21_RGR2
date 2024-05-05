import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class XMLParser {
    public static Document document;

    public static DataSheet parseXMLToDataSheet(String filePath){
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            DataHandler dataHandler = new DataHandler();
            InputStream xmlInputStream = new FileInputStream(filePath);
            saxParser.parse(xmlInputStream, dataHandler);

            return dataHandler.getDataSheet();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void parseDataSheetToXML(DataSheet dataSheet, String fileName){
        parseDataSheetToDocument(dataSheet);
        saveDocument(fileName);
    }

    private static void parseDataSheetToDocument(DataSheet dataSheet){
        createDocument();
        Element datasheet = document.createElement("datasheet");
        document.appendChild(datasheet);

        for (Data data : dataSheet.getDataArray()) {
            double x = data.getX();
            double y = data.getY();
            String date = data.getDate();
            Element element = newElement(date, x, y);
            addElement(element);
        }

        Element k = document.createElement("k");
        k.appendChild(document.createTextNode(dataSheet.getK() + ""));
        addElement(k);

        Element b = document.createElement("b");
        b.appendChild(document.createTextNode(dataSheet.getB() + ""));
        addElement(b);
    }

    private static Element newElement(String date, double x, double y){
        Element data = document.createElement("data");
        Attr attr = document.createAttribute("date");
        attr.setValue(date.trim());
        data.setAttributeNode(attr);

        Element elementX = document.createElement("x");
        elementX.appendChild(document.createTextNode(x + ""));
        data.appendChild(elementX);

        Element elementY = document.createElement("y");
        elementY.appendChild(document.createTextNode(y + ""));
        data.appendChild(elementY);

        return data;
    }

    private static void createDocument(){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void addElement(Element data){
        document.getDocumentElement().appendChild(data);
    }

    private static void saveDocument(String filePath){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer trf = transformerFactory.newTransformer();
            trf.setOutputProperty(OutputKeys.ENCODING, "Windows-1251");
            trf.setOutputProperty(OutputKeys.INDENT, "yes");
            trf.setOutputProperty("{http://xml.apache.org/xslt%7Dindent-amount", "4");
            DOMSource source = new DOMSource(document);
            File newXMLFile = new File(filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(newXMLFile);
            StreamResult result = new StreamResult(fileOutputStream);
            trf.transform(source, result);

            System.out.println("Document saved!!");
        } catch (FileNotFoundException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
