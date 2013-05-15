/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkTorino;

/**
 *
 * @author Giulio
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.Calendar;
import json.HTTPTokener;
import json.JSONArray;
import json.JSONObject;
import json.JSONTokener;


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
        
      
       
        
        
    } 