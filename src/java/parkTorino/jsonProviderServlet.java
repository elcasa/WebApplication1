/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkTorino;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import json.JSONObject;

/**
 * Servlet che fornisce il Json contenente tutti idati del DB
 * @author Giulio
 */
@WebServlet(name = "jsonProviderServlet", urlPatterns = {"/jsonProviderServlet"})
public class jsonProviderServlet extends HttpServlet {
   
   DataManager dm;
   
   public void init(ServletConfig config) throws ServletException 
   { 
      super.init(config);  // call the init method of base class

      try {
            dm=new DataManager();
                                    
      } catch (SQLException ex) {
            Logger.getLogger(jsonProviderServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

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
        
        JSONObject jsonParks=null;
        
        try {
            
            jsonParks = dm.getJson();
            
        } catch (SQLException ex) {
            Logger.getLogger(jsonProviderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
                
        out.print(jsonParks.toString(1));
                        
        out.flush();   
        
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
    
    
    @Override
    public void destroy() {  
       
       try {
            dm.close();
        } catch (SQLException ex) {
            Logger.getLogger(jsonProviderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
       super.destroy();
   } 
}
