/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkTorino;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import parkTorino.utils.Parser;

/**
 * Configura lo scheduling del parsing dei dati
 * Un thread del runnable Parser viene eseguito ogni MINUTE_SCHEDULING minuti
 * 
 * @author Giulio
 */

@WebListener
public class Config implements ServletContextListener {

    private ScheduledExecutorService scheduler;
    private static final int MINUTE_SCHEDULING=15; // 15 aggiornamento xml parcheggi torino ogni 5 minuti, io ogni MINUTE_SCHEDULING

    /**
    *
    * @param event
    */
   @Override
    public void contextInitialized(ServletContextEvent event) {
            
      scheduler = Executors.newSingleThreadScheduledExecutor();
      scheduler.scheduleAtFixedRate(new Parser(), MINUTE_SCHEDULING, MINUTE_SCHEDULING, TimeUnit.MINUTES);
            
    }

    /**
    *
    * @param event
    */
   @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

}
