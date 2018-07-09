/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
 * @author Elio
 */
public class Board_Creation extends HttpServlet {

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
            out.println("<title>Servlet Board</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Board at " + request.getContextPath() + "</h1>");
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
        Timestamp t = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = sdf.format(t);
        
        try {
            JSONObject data = new JSONObject(IOUtils.toString(request.getInputStream()));
            if (request.getSession(false).getAttribute("user") != null) {
                User u = (User) request.getSession().getAttribute("user");
                if(db.registerBoard(data.getString("board_name"), (u.getId()), 
                        time, data.getString("board_color"), data.getString("board_description"))){
                    r.put("status", 200);
                    Board b = db.getBoard("board_created_at", time);
                    d.put("board_id", b.getId());
                    d.put("board_name", b.getName());
                    d.put("user_id", b.getUserId());
                    d.put("board_created_at", b.getTimestamp());
                    d.put("board_color", b.getColor());
                    d.put("board_description", b.getDescription());
                    r.put("data", d);
                }else{
                    r.put("status", 404);
                    r.put("query", db.registerBoardString(data.getString("name"), (u.getId())));
                }
            }else{
                r.put("status", 500);
            }
        } catch (JSONException ex) {
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
