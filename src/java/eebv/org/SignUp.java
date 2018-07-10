/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Mariela
 */
public class SignUp extends HttpServlet {

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
        processRequest(request, response);
        JSONObject r = new JSONObject();
       PrintWriter w = response.getWriter();
        try {
            JSONObject data = new JSONObject(IOUtils.toString(request.getInputStream()));
            //se crea conexion a bbdd
            DBManager db = new DBManager();
            //se extraen datos del json
            String name = data.getString("name");
            String lastName = data.getString("last_name");
            String email = data.getString("email");
            String username = data.getString("username");
            String password = data.getString("password");
            if(db.userExists(username)){
               r.put("status", 409);
                //System.out.println(db.registerUser(name, lastName, username, password, email));
           }else{
               if(db.registerUser(name, lastName, username, password, email)){
                r.put("status", 200);
                }else{
                r.put("status", 500);
                r.put("query", db.registerUserString(name, lastName, username, password, email));
                }
           }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                r.put("status", 501);
            } catch (JSONException ex1) {
                Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex1);
            }
              ex.printStackTrace();
        }
        w.print(r);
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
