/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
@WebServlet(urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet</title>");            
            out.println("</head>");
            out.println("<body>");
       
        out.println("<ul>");
        
        java.sql.Connection con = null;
               
        try {
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Park");  // nome del db?
        } 
    catch (java.sql.SQLException sqle) {
     con = DriverManager.getConnection("jdbc:derby://localhost:1527/Park;create=true");
     con.createStatement().executeUpdate("CREATE TABLE PROVA (nome VARCHAR(250), id INT NOT NULL, cognome VARCHAR(250))");
     //creazione altre tabelle
    }
    catch (ClassNotFoundException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    

//PreparedStatement s = con.prepareStatement("INSERT INTO PROVA (nome,id,cognome) VALUES(?,?,?)");
        
//s.setString(1, "Alessandro");
//s.setInt(2, 0);
//s.setString(3, "Roncato");
//s.executeUpdate();
//java.sql.ResultSet rs = s.executeUpdate();
  
        
if (con!=null){
    //connesso
    //out.println("<li>"+rs.getString("descrizione"));
    out.println("<li>"+"connesso"+"</li>");
}
else
{
    // no con al db
    out.println("<li>"+"NON connesso"+"</li>");
}
        
//con.createStatement().executeUpdate("CREATE TABLE PROVASS (nome VARCHAR(250), id INT NOT NULL, cognome VARCHAR(250))");

PreparedStatement s = con.prepareStatement("INSERT INTO PROVASS (nome,id,cognome) VALUES(?,?,?)");
        
s.setString(1, "Alessandrossss");
s.setInt(2, 0);
s.setString(3, "Roncatosss");
s.executeUpdate();
ResultSet rs = con.createStatement().executeQuery("select * from PROVASS");
while (rs.next()) {
       System.out.println("nome: " + rs.getString(1) + " id:" + rs.getInt(2));
     }

s = con.prepareStatement("INSERT INTO PARCHEGGI (name,id, status, total, free, tendence,lat,lng) VALUES(?,?,?,?,?,?,?,?)");
        
s.setString(1, "ombreggiato");
s.setInt(2, 5);
s.setInt(3, 7);
s.setInt(4, 9);
s.setInt(5, 11);
s.setInt(6, 13);
s.setDouble(7, 15.15);
s.setDouble(8, 17.17);

s.executeUpdate();


ResultSet rs2 = con.createStatement().executeQuery("select * from PARCHEGGI");
while (rs2.next()) {
    out.println("<li> name:"+rs2.getString(1)+"</li>");    
       
       System.out.println("nome: " + rs2.getString(1) + " id:" + rs2.getInt(2));
     }
try {//è una versione embedded quindi va chiusa quando l'applicazione termina
     DriverManager.getConnection("jdbc:derby://localhost:1527/Park;shutdown=true");
     } catch (SQLException se) {//Stato = 08006 chiusura OK, altrimenti si è verificato un errore
     if (!se.getSQLState().equals("08006")) {
         throw se;
        }
    }

       /* try{
          Class driver = Class.forName("sun.jdbc.odbc.JdbcOdbcConnection");
          java.sql.Connection c = java.sql.DriverManager.getConnection("jdbc:odbc:nome");
          java.sql.Statement s = c.createStatement();
          java.sql.ResultSet rs = s.executeQuery("SELECT * FROM dati");
          
          while (rs.next()){
            out.println("<li>"+rs.getString("descrizione"));
          }
        }
        catch (Exception e )
        {
            e.printStackTrace();
        }
        */
       
        out.println("</ul>");
               out.println("</body>");
        out.println("</html>");
        out.close();
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
