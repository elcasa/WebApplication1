/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkTorino;

import parkTorino.utils.ParcheggioItem;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Paint;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
//import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.soap.Node;

import org.w3c.dom.Document;

import org.w3c.dom.Node;


import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author gcasarin
 */
public class Utils {
  
    /*
     
    NodeList updateData = doc.getElementsByTagName("td:traffic_data");	
Node info = null;
if(updateData.getLength()>0)
{
info = updateData.item(0);
ultimoAggiornamento = info.getAttributes().getNamedItem("generation_time").getNodeValue();
//String items[] = ultimoAggiornamento.split("+");
//Log.d(Riepilogo.tag,items[0]);
String time[] = ultimoAggiornamento.split("T");
if(time.length>1)
ultimoAggiornamento = "Dati aggiornati il "+time[0]+" alle "+time[1];
Log.d(Riepilogo.tag,"update:"+ultimoAggiornamento);
* 
} 
    
     */
private static ArrayList<ParcheggioItem> parcheggiList = new ArrayList<ParcheggioItem>();

public static String splitString(String s)
{
String text = s;
if(text.length()>15)
{
String[] items = text.split(" ");
int i=0;
if(items.length>2)
{
//se ho 2+ elementi
if((items[0]+items[1]).length()<15)
{
text = items[0]+" "+items[1]+"#";
i = 2;
}
}
else if(items.length>1&&items[0].length()<15)
{
text = items[0]+"#";
i=1;
}

for(;i<items.length;i++)
{
text+=items[i];
}
}
return text;
}



public static void updateParcheggi() throws SAXException, IOException, ParserConfigurationException
{
ArrayList<ParcheggioItem> parcheggi = new ArrayList<ParcheggioItem>();
URL url = new URL("http://opendata.5t.torino.it/get_pk");
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
DocumentBuilder db = dbf.newDocumentBuilder();
Document doc = db.parse(new InputSource(url.openStream()));
doc.getDocumentElement().normalize();

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


//Collections.sort(parcheggiList, new ParcheggioItemComparator());
}

public static ArrayList<ParcheggioItem> getParcheggi()
{
return parcheggiList;
}



}