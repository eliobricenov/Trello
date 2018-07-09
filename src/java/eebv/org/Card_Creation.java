/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author JoseUrdaneta
 */
public class Card_Creation extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Card_Creation</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Card_Creation at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DBManager db = new DBManager();
        JSONObject r = new JSONObject();
        JSONObject d = new JSONObject();
        PrintWriter p = response.getWriter();
        User u = (User) request.getSession(false).getAttribute("user");
        System.out.println(u.getId());
        try {
            JSONObject data = new JSONObject(IOUtils.toString(request.getInputStream()));
            System.out.println("TRY");
            int column_id = Integer.parseInt(data.getString("column_id"));
            int id = db.registerCard(data.getString("card_name"), column_id
                        , data.getString("card_description"), (u.getId()));
            System.out.println(id);    
            if(id>0){
                    r.put("status", 200);
                    d.put("card_name", data.getString("card_name"));
                    d.put("column_id", Integer.parseInt(data.getString("column_id")));
                    d.put("card_description", data.getString("card_description"));
                    d.put("card_id", id); 
                    r.put("data", d);
                }else{
                    r.put("status", 404);                }
        } catch (JSONException ex) {
            ex.printStackTrace();
            Logger.getLogger(Board_Creation.class.getName()).log(Level.SEVERE, null, ex);
             try {
                r.put("status", 500);
            } catch (JSONException ex2) {
                Logger.getLogger(GetUserData.class.getName()).log(Level.SEVERE, null, ex);
            };
        }
        p.print(r);
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
