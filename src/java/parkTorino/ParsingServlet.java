package parkTorino;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import parkTorino.utils.Parser;

/**
 *
 * @author gcasarin
 */
@WebServlet(urlPatterns = {"/ParsingServlet"})
public class ParsingServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        /*
        try {
            parseAndInsert();
        } catch (SQLException ex) {
            Logger.getLogger(ParsingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
               
        
        Thread t = new Thread( new Parser());
        t.run();
        /*
        DataManager dm =null;
        
        try {
            dm = new DataManager();
        } catch (SQLException ex) {
            Logger.getLogger(ParsingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            dm.parseAndInsert();
        } catch (SQLException ex) {
            Logger.getLogger(ParsingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        */             
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ParsingServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ParsingServlet at " + request.getContextPath() + "</h1>");
            
            // OUTPUT QUERY
            out.println("<strong>fasullo packageParkTorino</strong>");
            
            
            
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
        /*
        try {
            dm.close();
        } catch (SQLException ex) {
            Logger.getLogger(ParsingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    /*
    private void parseAndInsert() throws SQLException{
        
        try {
            
            Utils.updateParcheggi();
        } catch (SAXException ex) {
            Logger.getLogger(ParsingServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParsingServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ParsingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<ParcheggioItem> parkList=Utils.getParcheggi();
        
        //CONNECT
        java.sql.Connection con = null;               
        try {
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            //con = DriverManager.getConnection("jdbc:derby://localhost:1527/Park");  // nome del db?
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Park");
        } 
        catch (java.sql.SQLException sqle) {
            //con = DriverManager.getConnection("jdbc:derby://localhost:1527/Park;create=true");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Park;create=true");
            con.createStatement().executeUpdate("CREATE TABLE PARCHEGGI (name VARCHAR(50),id INT, status INT, total INT, free INT, tendence INT,lat DOUBLE,lng DOUBLE)");
            //creazione altre tabelle, creazione tabelle default in caso il db sia vuoto
            }
        
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //con.createStatement().executeUpdate("CREATE TABLE PARCHEGGI (name VARCHAR(50),id INT, status INT, total INT, free INT, tendence INT,lat DOUBLE,lng DOUBLE)");
        
        PreparedStatement s = con.prepareStatement("INSERT INTO PARCHEGGI (name,id, status, total, free, tendence,lat,lng) VALUES(?,?,?,?,?,?,?,?)");
        //ResultSet rs=null;
        
        for(int i=0;i<parkList.size();i++) {
            System.out.println("T: " + parkList.get(i).getTitolo() );
            
            s.setString(1, parkList.get(i).getTitolo());
            s.setInt(2, parkList.get(i).getId());
            s.setInt(3, parkList.get(i).getStatus());
            s.setInt(4, parkList.get(i).getTotale());
            s.setInt(5, parkList.get(i).getDisp());
            s.setInt(6, parkList.get(i).getTend());
            s.setDouble(7, parkList.get(i).getLat());
            s.setDouble(8, parkList.get(i).getLng());

            s.executeUpdate();
                        
            
        }
        
        // DISCONNECT
        try {//è una versione embedded quindi va chiusa quando l'applicazione termina
            DriverManager.getConnection("jdbc:derby://localhost:1527/Park;shutdown=true");
        } catch (SQLException se) {//Stato = 08006 chiusura OK, altrimenti si è verificato un errore
            if (!se.getSQLState().equals("08006")) {
                throw se;
            }
        
        }        
        
    }*/
}
