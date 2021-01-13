package XMLUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLUtil
{
    public static String[] getHdfsConf() throws Exception {
        DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dFactory.newDocumentBuilder();
        Document doc;
        doc = builder.parse(new File("config.xml"));

        String[] strings=new String[2];

        NodeList nl = doc.getElementsByTagName("ip");
        Node classNode = nl.item(0).getFirstChild();
        String ip = classNode.getNodeValue();
        strings[0]=ip;

        nl=doc.getElementsByTagName("port");
        classNode=nl.item(0).getFirstChild();
        String port=classNode.getNodeValue();
        strings[1]=port;

        return strings;
    }
}
