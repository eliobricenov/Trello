/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.servlets;

import eebv.org.models.*;
import eebv.org.services.BoardServices;
import eebv.org.tools.*;
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
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author JoseUrdaneta
 */
public class BoardServlet extends HttpServlet {

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
            out.println("<title>Servlet BoardServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BoardServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>PUT</code> method.
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
        JSONArray collabs = new JSONArray();
        PrintWriter p = response.getWriter();
        User u = (User) request.getSession(false).getAttribute("user");
        List<Board> boards = db.getBoards("user_id", (u.getId()));
        try {
            if (boards != null) {
                for (Board b : boards) {
                    jarray.put(BoardServices.boardToJSON(b));
                }
                r.put("boards", jarray);
                r.put("status", 200);
            } else {
                r.put("status", 404);
            }
        } catch (Exception ex) {
            try {
                r.put("status", 500);
            } catch (JSONException ex1) {
                Logger.getLogger(BoardServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(BoardServlet.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
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
        try {
            DBManager db = new DBManager();
            JSONObject r = new JSONObject();
            PrintWriter p = response.getWriter();
            String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Timestamp(System.currentTimeMillis()));
            User u = (User) request.getSession(false).getAttribute("user");
            JSONObject data = new JSONObject(IOUtils.toString(request.getInputStream()));
            
            if (db.registerBoard(data.getString("board_name"), (u.getId()), time, data.getString("board_color"),
                    data.getString("board_description"),data.getJSONArray("board_collaborators"))) {
                r.put("status", 200);
                Board b = db.getBoard("board_created_at", time);
                r.put("data", BoardServices.boardToJSON(b));
            } else {
                r.put("status", 404);
            }
            p.print(r);
        } catch (JSONException ex) {
            Logger.getLogger(BoardServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DBManager db = new DBManager();
        JSONObject r = new JSONObject();
        JSONObject d = new JSONObject();
        PrintWriter p = response.getWriter();
        User u = (User) request.getSession(false).getAttribute("user");
        try {
            JSONObject data = new JSONObject(IOUtils.toString(request.getInputStream()));
            if (db.updateBoard((data.getInt("board_id")),
                    data.getString("board_name"), data.getString("board_color"),
                    data.getString("board_description"), data.getJSONArray("board_collaborators"))) {
                List<User> collab = db.getCollabs((data.getInt("board_id")));
                if (collab != null) {
                    JSONArray collabs_inner = new JSONArray();
                    for (User c : collab) {
                        JSONObject c_json = new JSONObject();
                        c_json.put("user_id", c.getId());
                        c_json.put("board_id", (data.getInt("board_id")));
                        c_json.put("user_username", c.getName());
                        c_json.put("type_board_user_id", c.getCredential());
                        collabs_inner.put(c_json);
                    }
                    r.put("board_collaborators", collabs_inner);
                } else {
                    r.put("board_collaborators", new JSONArray());
                }
                r.put("status", 200);
                r.put("data", d);
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
     * Handles the HTTP <code>POST</code> method.
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
        PrintWriter p = response.getWriter();
        try {
            JSONObject data = new JSONObject(IOUtils.toString(request.getInputStream()));
            if (db.deleteBoard((data.getInt("board_id")))) {
                r.put("status", 200);
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
