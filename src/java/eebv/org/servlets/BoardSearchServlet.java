/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.servlets;

import eebv.org.models.Board;
import eebv.org.models.User;
import eebv.org.tools.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author JoseUrdaneta
 */
public class BoardSearchServlet extends HttpServlet {

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
            out.println("<title>Servlet BoardSearchServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BoardSearchServlet at " + request.getContextPath() + "</h1>");
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
        JSONArray jarray = new JSONArray();
        PrintWriter p = response.getWriter();
        if (request.getSession(false) != null) {
            System.out.println(request.getParameter("token"));
            try {
                List<Board> boards = db.searchBoards(request.getParameter("token"));
                if (boards != null) {
                    for (Board b : boards) {
                        JSONObject j = new JSONObject();
                        j.put("board_id", b.getId());
                        j.put("board_name", b.getName());
                        j.put("board_color", b.getColor());
                        j.put("board_description", b.getDescription());
                        j.put("user_id", b.getUserId());
                        j.put("board_created_at", b.getTimestamp());
                        List<User> collab = db.getCollabs(b.getId());
                        if (collab != null) {
                            JSONArray collabs_inner = new JSONArray();
                            for (User c : collab) {
                                JSONObject c_json = new JSONObject();
                                c_json.put("user_id", c.getId());
                                c_json.put("board_id", b.getId());
                                c_json.put("user_username", c.getName());
                                c_json.put("type_board_user_id", c.getCredential());
                                collabs_inner.put(c_json);
                            }
                            j.put("board_collaborators", collabs_inner);
                        }else{
                            j.put("board_collaborators", new JSONArray());
                        }
                        jarray.put(j);
                    }
                    r.put("boards", jarray);
                    r.put("status", 200);
                } else {
                    r.put("status", 404);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                try {
                    r.put("status", 500);
                } catch (JSONException ex1) {
                    Logger.getLogger(BoardServlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(BoardServlet.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        } else {
            System.out.println("NO SESSION");
            try {
                r.put("status", 501);
            } catch (JSONException ex) {
                Logger.getLogger(BoardServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
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
