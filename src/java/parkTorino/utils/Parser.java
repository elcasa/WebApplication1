/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkTorino.utils;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import parkTorino.DataManager;

/**
 * Runnable che sfrutta la clase DataManager per connettersi al DB
 * fare il parsing dei dati e inserirli nel DB
 * @author Giulio
 */
public class Parser implements Runnable{
    
    public void run(){
        
        DataManager dm =null;
        
        try {
            dm = new DataManager();
        } catch (SQLException ex) {
            Logger.getLogger("Parser").log(Level.SEVERE, null, ex);
        }
        
        try {
            dm.parseAndInsert();
        } catch (SQLException ex) {
            Logger.getLogger("Parser").log(Level.SEVERE, null, ex);
        }          
                
        try {
            dm.close();
        } catch (SQLException ex) {
            Logger.getLogger("Parser").log(Level.SEVERE, null, ex);
        }
    }
        
}
