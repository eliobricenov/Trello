/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.servlets;

import eebv.org.models.User;
import eebv.org.tools.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.*;

/**
 *
 * @author JoseUrdaneta
 */
public class ChipsServlet extends HttpServlet {

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
            out.println("<title>Servlet ChipsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChipsServlet at " + request.getContextPath() + "</h1>");
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
        DBManager db = new DBManager();
        JSONObject r = new JSONObject();
        JSONArray tagData = new JSONArray();
        JSONObject autocompleteData = new JSONObject();
        PrintWriter p = response.getWriter();
        List<User> users = db.getUsers();
        try {
            for (User u : users) {
                JSONObject obj = new JSONObject();
                obj.put("username", u.getUsername());
                obj.put("id", u.getId());
                autocompleteData.put(u.getUsername(), JSONObject.NULL);
                tagData.put(obj);
            }
            r.put("tagData", tagData);
            r.put("autocompleteData", autocompleteData);
            r.put("status", 200);
        } catch (JSONException ex) {
            try {
                r.put("status", 500);
            } catch (JSONException ex1) {
                Logger.getLogger(ChipsServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ChipsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        p.print(r);
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
