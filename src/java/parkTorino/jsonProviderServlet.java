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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import json.JSONObject;

/**
 *
 * @author Giulio
 */
@WebServlet(name = "jsonProviderServlet", urlPatterns = {"/jsonProviderServlet"})
public class jsonProviderServlet extends HttpServlet {

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
            DataManager dm=new DataManager();
            
            jsonParks = dm.getJson();
            
        } catch (SQLException ex) {
            Logger.getLogger(jsonProviderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        //out.print(jsonParks);
        out.print(jsonParks.toString(1));
        
        /*
        out.print("\n\nTEST\n\n");
        String test="[{\"total\":147,\"id\":1,\"pranzo\":0,\"free\":132,\"tendence\":1,\"name\":\"ARBARELLO\",\"lng\":7.6748,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.07243},{\"total\":434,\"id\":2,\"pranzo\":0,\"free\":227,\"tendence\":1,\"name\":\"BODONI\",\"lng\":7.68357,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.06355},{\"total\":858,\"id\":3,\"pranzo\":0,\"free\":476,\"tendence\":1,\"name\":\"BOLZANO\",\"lng\":7.66716,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.07248},{\"total\":229,\"id\":4,\"pranzo\":0,\"free\":162,\"tendence\":1,\"name\":\"D'AZEGLIO\nGALILEI\",\"lng\":7.67754,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.04289},{\"total\":110,\"id\":5,\"pranzo\":0,\"free\":83,\"tendence\":1,\"name\":\"EMANUELE\nFILIBERTO\",\"lng\":7.68031,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.07666},{\"total\":3389,\"id\":7,\"pranzo\":0,\"free\":3357,\"tendence\":1,\"name\":\"LINGOTTO\",\"lng\":7.66268,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.03162},{\"total\":259,\"id\":8,\"pranzo\":0,\"free\":109,\"tendence\":1,\"name\":\"MADAMA CRISTINA\",\"lng\":7.68342,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.05859},{\"total\":375,\"id\":9,\"pranzo\":0,\"free\":321,\"tendence\":1,\"name\":\"NIZZA\",\"lng\":7.6689,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.04085},{\"total\":485,\"id\":10,\"pranzo\":0,\"free\":409,\"tendence\":1,\"name\":\"PALAGIUSTIZIA\",\"lng\":7.66109,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.06935},{\"total\":753,\"id\":11,\"pranzo\":0,\"free\":196,\"tendence\":1,\"name\":\"PORTA PALAZZO\",\"lng\":7.68647,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.07646},{\"total\":696,\"id\":23,\"pranzo\":0,\"free\":678,\"tendence\":1,\"name\":\"CAIO MARIO\",\"lng\":7.64029,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.02863},{\"total\":137,\"id\":13,\"pranzo\":0,\"free\":127,\"tendence\":1,\"name\":\"RE UMBERTO\",\"lng\":7.67583,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.067},{\"total\":800,\"id\":14,\"pranzo\":0,\"free\":614,\"tendence\":1,\"name\":\"ROMA S.CARLO\nCASTELLO\",\"lng\":7.68037,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.06399},{\"total\":312,\"id\":15,\"pranzo\":0,\"free\":299,\"tendence\":1,\"name\":\"VENTIMIGLIA\",\"lng\":7.67297,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.03365},{\"total\":365,\"id\":16,\"pranzo\":0,\"free\":140,\"tendence\":1,\"name\":\"FONTANESI\",\"lng\":7.70501,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.07035},{\"total\":97,\"id\":17,\"pranzo\":0,\"free\":82,\"tendence\":1,\"name\":\"RACCONIGI\",\"lng\":7.64669,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.06983},{\"total\":200,\"id\":19,\"pranzo\":0,\"free\":108,\"tendence\":1,\"name\":\"STATI UNITI\",\"lng\":7.667,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.06406},{\"total\":470,\"id\":25,\"pranzo\":0,\"free\":477,\"tendence\":-1,\"name\":\"MOLINETTE\",\"lng\":7.67589,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.03858},{\"total\":277,\"id\":27,\"pranzo\":0,\"free\":59,\"tendence\":1,\"name\":\"SOFIA\",\"lng\":7.71692,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.09557},{\"total\":610,\"id\":28,\"pranzo\":0,\"free\":550,\"tendence\":1,\"name\":\"STURA\",\"lng\":7.71156,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.12058},{\"total\":478,\"id\":30,\"pranzo\":0,\"free\":279,\"tendence\":1,\"name\":\"VITTORIO PARK\",\"lng\":7.69543,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.06478},{\"total\":500,\"id\":18,\"pranzo\":0,\"free\":363,\"tendence\":1,\"name\":\"VALDO FUSI\",\"lng\":7.68761,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.06504},{\"total\":164,\"id\":32,\"pranzo\":0,\"free\":126,\"tendence\":1,\"name\":\"CTO\",\"lng\":7.67349,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.0336},{\"total\":390,\"id\":12,\"pranzo\":0,\"free\":279,\"tendence\":1,\"name\":\"SANTO STEFANO\",\"lng\":7.68372,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.07384},{\"total\":305,\"id\":46,\"pranzo\":0,\"free\":293,\"tendence\":1,\"name\":\"BACIGALUPO\",\"lng\":7.67249,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.0366},{\"total\":449,\"id\":48,\"pranzo\":0,\"free\":-1,\"tendence\":0,\"name\":\"VENCHI UNICA\",\"lng\":7.61576,\"sera\":0,\"mattina\":0,\"pome\":0,\"lat\":45.0741}]";
        
        */
        
        out.flush();
        
        /*
        try {
            // TODO output your page here. You may use following sample code. 
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet jsonProviderServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet jsonProviderServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
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
}
