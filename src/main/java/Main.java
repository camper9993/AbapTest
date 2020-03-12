import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL("http://www.cbr.ru/scripts/XML_daily.asp").openStream());
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("Valute");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getAttributes().item(0).getNodeValue().equals("R01200") && nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                double value = Double.parseDouble(element.getElementsByTagName("Value").
                        item(0).getTextContent().replace(',', '.'));
                int nominal = Integer.parseInt(element.getElementsByTagName("Nominal").item(0).getTextContent());
                double current_value = value / nominal;
                System.out.println("Текущий курс гонконгского доллара к рублю " + current_value);
            }
        }

    }
}
