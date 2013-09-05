/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkTorino;

import parkTorino.utils.ParcheggioItem;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Servlet;
import javax.xml.parsers.ParserConfigurationException;
import json.JSONArray;
import json.JSONObject;
import org.xml.sax.SAXException;
import parkTorino.utils.ListaParcheggi;

/**
 * 
 * @author Giulio
 */
public class DataManager {
    
    //private String urlDB="jdbc:derby://localhost:1527/Park;";  // NON TOCCARE!
    private String urlDB="jdbc:derby://localhost:1527/ProvaDB1;";
    
    
    private static final int MAX_INT_JDBC = java.lang.Integer.MAX_VALUE;
    
    private boolean DEBUG = true;
    private java.sql.Connection con = null;
    
    
   // LastStor ls = new LastStor(); // ultimo storico , per evitare di aggiornare lo storico ad ogni aggiornamento dei dati
    private int fascia;
    //private int ultimaFasciaStor;
             
    
    /**
    * Connette al DB, se il DB non esiste lo crea
    * e crea anche le tabelle
    * @throws SQLException Se ci sono problemi con la creazione / connesione al DB
    */
   public DataManager() throws SQLException{
               
        try {
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection(urlDB);
        } 
        catch (java.sql.SQLException sqle) {
            //con = DriverManager.getConnection("jdbc:derby://localhost:1527/Park;create=true");
            con = DriverManager.getConnection(urlDB +"create=true");         
                        
            con.createStatement().executeUpdate("CREATE TABLE PARCHEGGI"            
                    + "(id INT NOT NULL PRIMARY KEY, "
                    + "name VARCHAR(50),"
                    + "status INT, "
                    + "total INT, free INT, tendence INT,"
                    + "lat DOUBLE,lng DOUBLE)");
            
            con.createStatement().executeUpdate("CREATE TABLE STORICI"
                    + " (id INT NOT NULL PRIMARY KEY, "
                    + "mattinaMed FLOAT, mattinaNum INT, pranzoMed FLOAT, pranzoNum INT, "
                    + "pomeMed FLOAT, pomeNum INT, seraMed FLOAT, seraNum INT )");                        
            
            //creazione tabelle in caso il db non esista
                
            }
        
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
       
 /**
 * Esegue il parsing dell'xml contenente i dati attuali dei parcheggi di Torino
 * e aggiorna sia i dati attuali che gli storici, se già presenti, altrimenti li inserisce
 *
 * @throws SQLException Se si verifica un errore con una query
 */
   public void parseAndInsert() throws SQLException{
        /*
        Valori attuali:
        Faccio una update, se mi ritorna il valore zero allora faccio una insert, voleva dire
        che nessun record è stato modificato e che quindi il record relativo a quel parecheggio non esisteva
        Storici: 
        se trovo in storici un record con l'id attuale faccio la update calcolando la media, 
        altrimenti inserisco nella fascia oraria attuale e -1 nelle altre
        
        */ 
                
        ListaParcheggi lp=null;
        
        try {
            
            lp = new ListaParcheggi();
        } catch (SAXException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<ParcheggioItem> parkList=lp.getList();   
        
        fascia = getFascia( lp.getOra() ); // fascia regolerà in che fascia oraria andrò a modificare la media               
        
        int res;
        PreparedStatement insert = con.prepareStatement("INSERT INTO PARCHEGGI (id,name, status, total, free, tendence,lat,lng) VALUES(?,?,?,?,?,?,?,?)");                
        PreparedStatement update = con.prepareStatement("UPDATE PARCHEGGI SET status=?, total=?, free=?, tendence=? WHERE id = ?");
        
        PreparedStatement oldStor = con.prepareStatement("select * from STORICI WHERE id = ?");
        
        PreparedStatement insertStor = con.prepareStatement("INSERT INTO STORICI (id, mattinamed, mattinanum, pranzomed, pranzonum, pomemed, pomenum, seramed, seranum) VALUES(?,?,?,?,?,?,?,?,?)");                
        //PreparedStatement updateStorVecchio = con.prepareStatement("UPDATE STORICI SET mattinamed=?, mattinanum=? WHERE id = ?");               
        
        String[] updateFasce = new String[4];
        updateFasce[0]="UPDATE STORICI SET mattinamed=?, mattinanum=? WHERE id = ?";
        updateFasce[1]="UPDATE STORICI SET pranzomed=?, pranzonum=? WHERE id = ?";
        updateFasce[2]="UPDATE STORICI SET pomemed=?, pomenum=? WHERE id = ?";
        updateFasce[3]="UPDATE STORICI SET seramed=?, seranum=? WHERE id = ?";
        
        
        /*
        
        Object obj = getServletContext().getAttribute("obj");  
        if(obj==null || (!obj instanceof my.package.name.MyClass)) { // If object is null reinitialize it  
           obj = new MyClass();  
           getServletContext().setAttribute("obj", obj);  
        }   
         
        */
        
            // where id = ? e poi grazie al punto di domanda faccio variare l'id per fare tutti gli update
        for(int i=0;i<parkList.size();i++) {
                
             update.setInt(1, parkList.get(i).getStatus());
             update.setInt(2, parkList.get(i).getTotale());
             update.setInt(3, parkList.get(i).getDisp());
             update.setInt(4, parkList.get(i).getTend());              
                
             update.setInt(5, parkList.get(i).getId());
                        
             res=update.executeUpdate();
                
             if(DEBUG ){
                 System.out.println("PARK: " + parkList.get(i).getTitolo()+", UPDATE query res : "+res ); 
             }
                
             if (res==0){
                 // non ha aggiornato alcun record!!! allora faccio una insert
                    
                 insert.setInt(1, parkList.get(i).getId());                    
                 insert.setString(2, parkList.get(i).getTitolo());
                 insert.setInt(3, parkList.get(i).getStatus());                
                 insert.setInt(4, parkList.get(i).getTotale());
                 insert.setInt(5, parkList.get(i).getDisp());
                 insert.setInt(6, parkList.get(i).getTend());
                 insert.setDouble(7, parkList.get(i).getLat());
                 insert.setDouble(8, parkList.get(i).getLng());
                    
                 res=insert.executeUpdate();
                    
                 if(DEBUG ){
                     System.out.println("PARK: " + parkList.get(i).getTitolo()+", INSERT query res: "+res ); 
                 }
             }
             //fascia=3; // test
             
             
             // STORICI
             // orario -1= periodo non monitorato per lo storico
             if(fascia != -1  ){    // se l'orario è in una fascia monitorata aggiorno lo storico
                 
                res=0;
                
                oldStor.setInt(1, parkList.get(i).getId()); // select * from STORICI WHERE id = ?
                ResultSet rsOld=oldStor.executeQuery();

                // calcolo media posti liberi
                if (rsOld.next()){

                    float vecchiaMedia= rsOld.getFloat(fascia*2+2); // media
                    int num= rsOld.getInt(fascia*2+3);   // num valori
                    float nuovaMedia = (vecchiaMedia*num + parkList.get(i).getDisp() ) / (num+1);
                    
                    if( num >= MAX_INT_JDBC){
                        num =1;
                    }
                    
                    PreparedStatement updateStor = con.prepareStatement( updateFasce[fascia]);
        
                    updateStor.setFloat(1, nuovaMedia);
                    updateStor.setInt(2, num+1);
                    updateStor.setInt(3, parkList.get(i).getId());

                    res=updateStor.executeUpdate();
                                        
                    if(DEBUG ){
                        System.out.println("STOR: " + parkList.get(i).getTitolo()+", UPDATE query res : "+res ); 
                    }

                }


                if (res==0){
                    // non ha aggiornato alcun record!!! allora faccio una insert

                    insertStor.setInt(1, parkList.get(i).getId());
                    insertStor.setFloat(2, -1f ); //mattina media
                    insertStor.setInt(3, 0);
                    insertStor.setFloat(4, -1f); // pranzo media
                    insertStor.setInt(5, 0);
                    insertStor.setFloat(6, -1f); // pome media
                    insertStor.setInt(7, 0);
                    insertStor.setFloat(8, -1f); // sera media
                    insertStor.setInt(9, 0);

                    insertStor.setFloat(fascia*2+2, parkList.get(i).getDisp() );
                    insertStor.setInt(fascia*2+3, 1);

                    res=insertStor.executeUpdate();

                    if(DEBUG ){
                        System.out.println("STOR: " + parkList.get(i).getTitolo()+", INSERT query res: "+res ); 
                    }
                }
             }                     
        }// FINE CICLO FOR
                
    }
    
   /**
    * Fornisce la fascia oraria a partire dall'ora del giorno.
    * 
    * @param ora L'ora da esaminare
    * @return Un intero indicante la fascia oraria
    */ 
   
    private int getFascia( int ora){
        
        if (ora>=7 && ora<=11)
            return 0;
        
        if (ora>=12 && ora<=14)
            return 1;
        
        if (ora>=15 && ora<=18)
            return 2;
        
        if (ora>=19 && ora<=23) 
            return 3;
        
        return -1; // -1 fascia non monitorata
        
    }
     
    /**
    * Genera un JSONObject contenente tutti i dati contenuti nel DB
    * per fornirli all'app Android.
    * 
    * @return Un JSONObject contenente tutti i dati contenuti nel DB
    * @throws SQLException
    */
   public JSONObject getJson() throws SQLException{
        
        //ResultSet rs= query("select id,name,total,free,tendence,lat,lng from APP.PARCHEGGI");
            // join di parcheggi e storici per un unico json contenente tutto
            // select PARCHEGGI.id,name,total,free,tendence,lat,lng,mattinamed,pranzomed,pomemed,seramed from PARCHEGGI left join STORICI on PARCHEGGI.id = STORICI.id
            // gli storici non presenti sono null, ma con il left join mi ritorna i parcheggi anche se il relativo storico non è presente
        
        ResultSet rs= query("select PARCHEGGI.id,name,total,free,tendence,lat,lng,mattinamed,pranzomed,pomemed,seramed from PARCHEGGI left join STORICI on PARCHEGGI.id = STORICI.id");
        
        JSONArray jParkArray = new JSONArray();
        int i=0;
        JSONObject jPark;
                
        while (rs.next()) { // ATTENZIONE QUERY
            jPark  = new JSONObject();
                        
            jPark.put("id", rs.getInt(1));
            jPark.put("name", rs.getString(2));    
            jPark.put("total", rs.getInt(3));    
            jPark.put("free", rs.getInt(4));
            jPark.put("tendence", rs.getInt(5));
            jPark.put("lat", rs.getDouble(6));
            jPark.put("lng", rs.getDouble(7));    
            
            
            jPark.put("mattina", rs.getFloat(8));
            jPark.put("pranzo", rs.getFloat(9));
            jPark.put("pome", rs.getFloat(10));
            jPark.put("sera", rs.getFloat(11));
                                    
            jParkArray.put(i,jPark);
            i++;                               
        }
            
        rs.close ();                
        //return jParkArray;
        
        jPark = new JSONObject();
        jPark.put("parcheggi", jParkArray);
        return jPark;
                 
        /* //come mostrarlo in una servlet
        
        response.setContentType("application/json");
        // Get the printwriter object from response to write the required json object to the output stream      
        PrintWriter out = response.getWriter();
        // Assuming your json object is **jsonObject**, perform the following, it will return your json object  
        out.print(jsonObject);
        out.flush();
          
         */
        
    }
        
    /**
    * Esegue la query q nel DB e ritorna il ResultSet corrispondente
    * @param q La SQLQuery da eseguire
    * @return Il ResultSet corrispondente alla query eseguita
    * @throws SQLException
    */
   public ResultSet query( String q) throws SQLException{
        
        ResultSet rs = con.createStatement().executeQuery(q);
        
        return rs;
    }
    
    /**
    * Ritorna true se la tabella in esame è vuota
    * 
    * @param table Il nome della tabella da analizzare
    * @return Un booleano che risponde alla domanda: la tabella è vuota?
    * @throws SQLException
    */
   
   public boolean isEmpty( String table) throws SQLException{              
        ResultSet rs;
        rs = query("select COUNT(*) from "+table);
        
        rs.next();
        if( rs.getInt(1) == 0){
            return true;
        }
        
        rs.close();
        
        return false;
    }
    
    /**
    * Chiude la connessione al DB
    * @throws SQLException Se ci sono problemi nella disconnesione
    */
   public void close() throws SQLException{
        
        // DISCONNECT
        try {//è una versione embedded quindi va chiusa quando l'applicazione termina
           DriverManager.getConnection(urlDB +"shutdown=true");              
           
        } catch (SQLException se) {//Stato = 08006 chiusura OK, altrimenti si è verificato un errore
           
           // XJ015 (with SQLCODE 50000) is the expected (successful) SQLSTATE for complete system shutdown. 
           //08006 (with SQLCODE 45000), on the other hand, is the expected SQLSTATE for shutdown of only an individual database.
           
            if ( !se.getSQLState().equals("08006") || ! !se.getSQLState().equals("XJ015")  ) {
                throw se;
            }
        
            
        }
        
        con = null;
        
    }
    
    /*
    private int updateStor( int fascia, int id, float media, int num) throws SQLException {
        
        String[] updateFasce = new String[4];
        updateFasce[0]="UPDATE STORICI SET mattinamed=?, mattinanum=? WHERE id = ?";
        updateFasce[1]="UPDATE STORICI SET pranzomed=?, pranzonum=? WHERE id = ?";
        updateFasce[2]="UPDATE STORICI SET pomemed=?, pomenum=? WHERE id = ?";
        updateFasce[3]="UPDATE STORICI SET seramed=?, seranum=? WHERE id = ?";
                
        PreparedStatement updateStor = con.prepareStatement( updateFasce[fascia]);
        
        updateStor.setFloat(1, media);
        updateStor.setInt(2, num);
        updateStor.setInt(3, id);
        
        return updateStor.executeUpdate();
        
    }
    */
    
   
}
