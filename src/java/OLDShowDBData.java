/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gcasarin
 */
@WebServlet(urlPatterns = {"/OLDShowDBData"})
public class OLDShowDBData extends HttpServlet {

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
            throws ServletException, IOException { // AGGIUNTO IO SQL-EXCEPTION !!!!!!
        // HO  AGGIUNTO IO SQL-EXCEPTION !!!!!! ANCHE IN DOGET E DOPOST !|!!
              
        
        // CONNECT TO DB!!
        java.sql.Connection con = null;               
        try {
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Park");  // nome del db?
        } 
        catch (java.sql.SQLException sqle) {
            
            try { // messo io un altro try e catch.. mah
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Park;create=true");
            con.createStatement().executeUpdate("CREATE TABLE PROVA (nome VARCHAR(250), id INT NOT NULL, cognome VARCHAR(250))");
            //creazione altre tabelle, creazione tabelle default in caso il db sia vuoto
            }
            catch(java.sql.SQLException sqlexc){
                // do nothing
                }
            }
        
        
        catch (ClassNotFoundException ex) {
            Logger.getLogger(javax.servlet.Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShowDBData</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShowDBData at " + request.getContextPath() + "</h1>");
            out.println("<ul>");
            
            if (con!=null){
                //connesso
                out.println("<li>"+"connesso"+"</li>");
            }
            else{
                // no con al db
                out.println("<li>"+"NON connesso"+"</li>");
            }
            
            try{ // try-catch aggiunto da me
                ResultSet rs2 = con.createStatement().executeQuery("select * from PARCHEGGI");
                while (rs2.next()) {
                    out.println("<li> name:"+rs2.getString(1)+"</li>");
                    //System.out.println("nome: " + rs2.getString(1) + " id:" + rs2.getInt(2));
                    }
            }
            catch (java.sql.SQLException sqle){
                // do nothing
            }
            
            
            
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
        
        
        // DISCONNECT
        try {//è una versione embedded quindi va chiusa quando l'applicazione termina
            DriverManager.getConnection("jdbc:derby://localhost:1527/Park;shutdown=true");
        } catch (SQLException se) {//Stato = 08006 chiusura OK, altrimenti si è verificato un errore
            if (!se.getSQLState().equals("08006")) {
                //throw se; // COMMENTATO IO!!! RINCONTROLLARE !!!!!!!@@!!!!!!!!##!!!!!!!àà!!!!!èè!!!!!!!!
            }
        
        }
        
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
}
