package parkTorino;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.JSONArray;
import json.JSONObject;

/**
 *
 * @author gcasarin
 */
@WebServlet(urlPatterns = {"/ShowDBData"})
public class ShowDBData extends HttpServlet {

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
        
        
        DataManager dm =null;
        
        try {
            dm = new DataManager();
        } catch (SQLException ex) {
            Logger.getLogger(ParsingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs=null;
        try {
            rs = dm.query("select id,name,total,free,tendence,lat,lng from PARCHEGGI");
            // join di parcheggi e storici per un unico json contenente tutto
            // select PARCHEGGI.id,name,total,free,tendence,lat,lng,mattinamed,pranzomed,pomemed,seramed from PARCHEGGI left join STORICI on PARCHEGGI.id = STORICI.id
            // gli storici non presenti sono null, ma con il left join mi ritorna i parcheggi anche se il relativo storico non è presente
        } catch (SQLException ex) {
            Logger.getLogger(ParsingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // CREAZIONE JSON
        JSONArray jParkArray = new JSONArray();
        int i=0;
        JSONObject jPark;
        
        try{
            while (rs.next()) { // ATTENZIONE QUERY
                
                jPark  = new JSONObject();
                jPark.put("id", rs.getInt(1));
                jPark.put("name", rs.getString(2));
                jPark.put("total", rs.getInt(3));
                jPark.put("free", rs.getInt(4));
                jPark.put("tendence", rs.getInt(5));
                jPark.put("lat", rs.getDouble(6));
                jPark.put("lng", rs.getDouble(7));
                /*
                jPark.put("mattina", rs.getFloat(8));
                jPark.put("pranzo", rs.getFloat(9));
                jPark.put("pome", rs.getFloat(10));
                jPark.put("sera", rs.getFloat(11));
                */
                jParkArray.put(i,jPark);
                i++;                               
            }
            
            //rs.close ();
            
            
        }
        catch (java.sql.SQLException sqle){
                // do nothing
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
            
            try{ // try-catch aggiunto da me
                rs = dm.query("select * from PARCHEGGI");
                while (rs.next()) {
                    
                    out.println("<li> name:"+rs.getString(1)+"</li>");
                    //out.println("<li> name:"+rs.getInt(1)+"</li>");
                    
                    //System.out.println("nome: " + rs2.getString(1) + " id:" + rs2.getInt(2));
                    }
                                
            }
            catch (java.sql.SQLException sqle){
                // do nothing
            }
            out.println("</ul>");
                        
                        
            out.println("</body>");
            out.println("</html>");
            
            out.println("JSON:");
            out.print(jParkArray);
            
        } finally {            
            out.close();
        }
        
        try {
            dm.close();
        } catch (SQLException ex) {
            Logger.getLogger(ParsingServlet.class.getName()).log(Level.SEVERE, null, ex);
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
