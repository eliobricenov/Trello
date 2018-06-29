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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author Mariela
 */
public class Login extends HttpServlet {

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
        JSONObject r = new JSONObject();
        PrintWriter p = response.getWriter();
        try {
            JSONObject data = new JSONObject(IOUtils.toString(request.getInputStream()));
            String email = data.getString("email");
            String pass = data.getString("password");
            DBManager db = new DBManager();
            User u = db.getUser(email);
            HttpSession ses = request.getSession();
            if(db.userExists(email)){
               if(db.checkPassword(email, pass)){
                   r.put("status", 200);
                   ses.setAttribute("user", u);
               }else{
                   r.put("status", 403);
               }
            }else{
                 r.put("status", 409);
                 ses.invalidate();
            }
        } catch (Exception ex) {
            try {
                r.put("status", 500);
                Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex1) {
                
                Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace();
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
