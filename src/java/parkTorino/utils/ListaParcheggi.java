/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkTorino.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Giulio
 */
public class ListaParcheggi {
    
    private int ora;
    
    ArrayList<ParcheggioItem> parcheggiList = new ArrayList<ParcheggioItem>();
    
    /**
    *
    * @throws ParserConfigurationException
    * @throws IOException
    * @throws SAXException
    */
   public ListaParcheggi() throws ParserConfigurationException, IOException, SAXException{
        parse();
    }
    
    /**
    *
    * @return
    */
   public int getOra(){
        return ora;
    }
    
    /**
    *
    * @return
    */
   public ArrayList<ParcheggioItem> getList(){
        return parcheggiList;
    }
    
    private void parse() throws ParserConfigurationException, IOException, SAXException {
        
        ArrayList<ParcheggioItem> parcheggi = new ArrayList<ParcheggioItem>();
        URL url = new URL("http://opendata.5t.torino.it/get_pk");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(url.openStream()));
        doc.getDocumentElement().normalize();
        
        NodeList tList = doc.getElementsByTagName("td:traffic_data");
        Node tNode=tList.item(0);
        String time=tNode.getAttributes().getNamedItem("start_time").getNodeValue();
        String[] t=time.split("T");
        time=t[1].substring(0, 2);
        ora= Integer.parseInt(time);

        NodeList nodeList = doc.getElementsByTagName("td:PK_data");

        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            //Log.d(tag,node.getNodeName().toString());
            NamedNodeMap attributes = node.getAttributes();

            double latitudine = Double.parseDouble(attributes.getNamedItem("lat").getNodeValue());
            double longitudine = Double.parseDouble(attributes.getNamedItem("lng").getNodeValue());
            //LatLng position = new LatLng(latitudine,longitudine);
            String titolo = attributes.getNamedItem("Name").getNodeValue();

            int id=0;
            try
            {id = Integer.parseInt(attributes.getNamedItem("ID").getNodeValue());}
            catch(Exception e){id=-1;}

            int status=0;
            try
            {status = Integer.parseInt(attributes.getNamedItem("status").getNodeValue());}
            catch(Exception e){status=-99;}

            int parcheggiTotali = 0;
            try
            {parcheggiTotali = Integer.parseInt(attributes.getNamedItem("Total").getNodeValue());}
            catch(Exception e){parcheggiTotali=-1;}

            int parcheggiLiberi = 0;
            try
            {parcheggiLiberi = Integer.parseInt(attributes.getNamedItem("Free").getNodeValue());}
            catch(Exception e){parcheggiLiberi=-1;}

            int tendenza = 0;
            try
            {tendenza = Integer.parseInt(attributes.getNamedItem("tendence").getNodeValue());}
            catch(Exception e){tendenza=0;}


            //ParcheggioItem pi = new ParcheggioItem(titolo,parcheggiLiberi,parcheggiTotali,tendenza,latitudine,longitudine);

            //ParcheggioItem pi = new ParcheggioItem(titolo,parcheggiLiberi,parcheggiTotali,tendenza);
            ParcheggioItem pi = new ParcheggioItem(titolo,id, status, parcheggiLiberi,parcheggiTotali,tendenza,latitudine,longitudine);
            parcheggi.add(pi);
        }
        
        parcheggiList = parcheggi;
    }
    
}
