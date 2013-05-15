/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkTorino;

import parkTorino.utils.ParcheggioItem;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;


/**
 *
 * @author gcasarin
 */
public class Parser {
    private static Socket socket;
    private static URL url;
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        try{
        Utils.updateParcheggi();
                
        ArrayList<ParcheggioItem> parkList=Utils.getParcheggi();
        
        for(int i=0;i<parkList.size();i++) {
            System.out.println("t: " + parkList.get(i).getTitolo() );
            
            
            
        }
        
        }
        
        catch (Exception e ) { 
                System.out.println("Qualcosa non va:");e.printStackTrace();
            }
        
        /*
        
            // TODO code application logic here
        try{
        URL url = new URL("http://opendata.5t.torino.it/get_pk");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(url.openStream()));
        doc.getDocumentElement().normalize();
        
        NodeList nodeList = doc.getElementsByTagName("td:PK_data");
        
        for (int j = 0; j < nodeList.getLength(); j++)
        {
            Node node = nodeList.item(j);
            //Log.d(tag,node.getNodeName().toString());
            NamedNodeMap attributes = node.getAttributes();

            double latitudine = Double.parseDouble(attributes.getNamedItem("lat").getNodeValue());
            double longitudine = Double.parseDouble(attributes.getNamedItem("lng").getNodeValue());
            //LatLng position = new LatLng(latitudine,longitudine);
            String titolo = attributes.getNamedItem("Name").getNodeValue();
            System.out.println("t: " + titolo);

        }
    }
        catch (Exception e ) { 
                System.out.println("Qualcosa non va:");e.printStackTrace();
            }
  
        */
        
        
        /*
           int i=0;
            
       // for(i=0;i<10000000;i++){
           
       //    ArrayList<ParcheggioItem> parcheggi=Utils.getParcheggi();
       //    System.out.println( parcheggi.get(0).getTitolo() );
           
            try {
                //url = new URL("http://api.openweathermap.org/data/2.1/weather/city/"+i);
                
                url = new URL("http://opendata.5t.torino.it/get_pk");
                
                InputStream in = url.openStream();
                
                
                BufferedReader bin= new BufferedReader(new InputStreamReader(in));
              
               
               String pagina="";
               String dati=bin.readLine();
                while(dati!=null){
                    pagina+=dati;
                    //System.out.println(dati);
                    dati=bin.readLine();
                }
                
                System.out.println(pagina);
                
                XMLTokener xmlT= new XMLTokener(pagina);
                                
                
                if(dati!=null&&!dati.equals("{\"message\":\"Error: Not found city\",\"cod\":\"404.1\"}")){
                JSONTokener jtoken= new JSONTokener(dati);//ok genero un jsontokener da una stringa
               // System.out.println(jtoken.toString());
                JSONObject jobject = new JSONObject(jtoken);//ok non da errore
                //socket.close();
                
                System.out.println("Id : "+i+" Citta: "+(String)jobject.get("name"));
                }
                
                  
            }catch (IOException e ) { 
                System.out.println("Qualcosa non va:");e.printStackTrace();
            }
         //}
            
         */   
}
    
}

/*
package meteo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.Calendar;
import meteo.json.HTTPTokener;
import meteo.json.JSONArray;
import meteo.json.JSONObject;
import meteo.json.JSONTokener;


public class Citta {
    private static Socket socket;
    private static URL url;
   
    
    public static void main(String[] args) {
        
            // TODO code application logic here
           
           int i;
           
            
        for(i=0;i<10000000;i++){
           
            try {
                url = new URL("http://api.openweathermap.org/data/2.1/weather/city/"+i);
                
                
                InputStream in = url.openStream();
                
                
                BufferedReader bin= new BufferedReader(new InputStreamReader(in));
              
               
               String pagina="";
               String dati=bin.readLine();
                /*while(dati!=null){
                    pagina+=dati;
                    //System.out.println(dati);
                    dati=bin.readLine();
                }*/
    /*
               //System.out.println(dati);
                if(dati!=null&&!dati.equals("{\"message\":\"Error: Not found city\",\"cod\":\"404.1\"}")){
                JSONTokener jtoken= new JSONTokener(dati);//ok genero un jsontokener da una stringa
               // System.out.println(jtoken.toString());
                JSONObject jobject = new JSONObject(jtoken);//ok non da errore
                //socket.close();
                
                System.out.println("Id : "+i+" Citta: "+(String)jobject.get("name"));
                }
                
                  
            }catch (IOException e ) { System.out.println("Qualcosa non va:");e.printStackTrace();}
        }
        }   
        /*try {
            url = new URL("http://api.openweathermap.org/data/2.1/weather/city/524901");
            BufferedInputStream page = new BufferedInputStream(url.openStream());
            
            XPathFactory factory = XPathFactory.newInstance();
            XPath xPath=factory.newXPath();
            String pattern = "//body/a";
            NodeList nodes = (NodeList)xPath.evaluate(pattern, response, XPathConstants.NODESET);
            
            for (int i = 0; i < nodes.getLength(); i++) {
                System.out.println((String) nodes.item(i).getNodeValue());
            }
        } catch (MalformedURLException ex) {
            
        }catch (IOException ex) {
            
        }*/
        
      
       /* 
        
        
    }
    
*/

