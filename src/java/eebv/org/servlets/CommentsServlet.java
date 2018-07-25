/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.servlets;

import eebv.org.models.Board;
import eebv.org.models.Comment;
import eebv.org.models.User;
import eebv.org.services.BoardServices;
import eebv.org.services.CommentServices;
import eebv.org.tools.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
 * @author GGsus
 */
public class CommentsServlet extends HttpServlet {

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
            out.println("<title>Servlet CommentsServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CommentsServlet at " + request.getContextPath() + "</h1>");
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
        try {
            DBManager db = new DBManager();
            JSONObject r = new JSONObject();
            PrintWriter p = response.getWriter();
            String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Timestamp(System.currentTimeMillis()));
            User u = (User) request.getSession(false).getAttribute("user");
            JSONObject data = new JSONObject(IOUtils.toString(request.getInputStream()));
            JSONArray comments_j = new JSONArray();
            int res = db.registerComment(data.getInt("card_id"), u.getId(),
                    data.getString("comment_text"), time);
            if (res > 0) {
                r.put("status", 200);
                List<Comment> comments = db.getComments(data.getInt("card_id"));
                if(comments != null){
                   for(Comment c : comments){
                       comments_j.put(CommentServices.commentToJSON(c));
                   } 
                }
                r.put("data", new JSONObject().put("comments", comments_j).put("created_comment", 
                        CommentServices.commentToJSON(db.getComment(res))));
            } else {
                r.put("status", 404);
            }
            p.print(r);
        } catch (JSONException ex) {
            Logger.getLogger(BoardServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DBManager db = new DBManager();
        JSONObject r = new JSONObject();
        JSONArray comments_j = new JSONArray();
        PrintWriter p = response.getWriter();
        try {
            JSONObject data = new JSONObject(IOUtils.toString(request.getInputStream()));
            if (db.deleteComment((data.getInt("comment_id")))) {
                r.put("status", 200);
                List<Comment> comments = db.getComments(data.getInt("card_id"));
                if(comments != null){
                   for(Comment c : comments){
                       comments_j.put(CommentServices.commentToJSON(c));
                   }
                   r.put("data", comments_j);
                }else{
                    r.put("data", new JSONArray());
                }
                
            } else {
                r.put("status", 404);
            }
        } catch (JSONException ex) {
            Logger.getLogger(BoardServlet.class.getName()).log(Level.SEVERE, null, ex);
            try {
                r.put("status", 500);
            } catch (JSONException ex2) {
                Logger.getLogger(BoardServlet.class.getName()).log(Level.SEVERE, null, ex);
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
