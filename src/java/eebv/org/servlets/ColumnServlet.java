/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.servlets;

import eebv.org.models.*;
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
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author JoseUrdaneta
 */
public class ColumnServlet extends HttpServlet {

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
            out.println("<title>Servlet ColumnServelt</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ColumnServelt at " + request.getContextPath() + "</h1>");
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
        JSONArray columns_json = new JSONArray();
        PrintWriter p = response.getWriter();
        int boardId = (request.getParameter("board_id") == null) ? 0 : Integer.parseInt(request.getParameter("board_id"));
        if (boardId != 0) {
            try {
                System.out.println(boardId);
                List<Column> columns = db.getColumns(boardId);
                for (Column c : columns) {
                    JSONObject column_json = new JSONObject();
                    column_json.put("column_id", c.getColumn_id());
                    column_json.put("board_id", c.getBoard_id());
                    column_json.put("column_name", c.getColumn_name());
                    List<Card> cards = db.getCards(c.getColumn_id());
                    JSONArray cards_json = new JSONArray();
                    for (Card card : cards) {
                        JSONObject card_json = new JSONObject();
                        card_json.put("card_id", card.getCard_id());
                        card_json.put("column_id", card.getColumnId());
                        card_json.put("card_name", card.getCard_name());
                        card_json.put("card_description", card.getCard_description());
                        cards_json.put(card_json);
                    };
                    column_json.put("cards", cards_json);
                    columns_json.put(column_json);
                }
                r.put("status", 200);
                r.put("columns", columns_json);
            } catch (JSONException ex) {
                Logger.getLogger(ColumnServlet.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    r.put("status", 404);
                } catch (JSONException ex1) {
                    Logger.getLogger(ColumnServlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else {
            try {
                r.put("status", 500);
            } catch (JSONException ex) {
                Logger.getLogger(ColumnServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        DBManager db = new DBManager();
        JSONObject r = new JSONObject();
        JSONObject d = new JSONObject();
        PrintWriter p = response.getWriter();
        User u = (User) request.getSession(false).getAttribute("user");
        try {
            JSONObject data = new JSONObject(IOUtils.toString(request.getInputStream()));
            if (db.userOwns(u.getId(), data.getInt("board_id"))) {
                int result = db.registerColumn(data.getInt("board_id"),
                        data.getString("column_name"));
                if (result > 0) {
                    r.put("status", 200);
                    d.put("column_id", result);
                    d.put("board_id", data.getInt("board_id"));
                    d.put("column_name", data.getString("column_name"));
                    r.put("data", d);
                } else {
                    r.put("status", 404);
                }
            } else {
                r.put("status", 403);
            }
        } catch (JSONException ex) {
            Logger.getLogger(ColumnServlet.class.getName()).log(Level.SEVERE, null, ex);
            try {
                r.put("status", 500);
            } catch (JSONException ex2) {
                Logger.getLogger(ColumnServlet.class.getName()).log(Level.SEVERE, null, ex);
            };
        }
        p.print(r);
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
        User u = (User) request.getSession(false).getAttribute("user");
        PrintWriter p = response.getWriter();
        try {
            JSONObject data = new JSONObject(IOUtils.toString(request.getInputStream()));
            if (db.userOwns(u.getId(), data.getInt("board_id"))) {
                if (db.updateColumn(data.getInt("column_id"), data.getString("column_name"))) {
                    r.put("status", 200);
                    r.put("data", d);
                } else {
                    r.put("status", 404);
                }
            } else {
                r.put("status", 403);
            }
        } catch (JSONException ex) {
            Logger.getLogger(ColumnServlet.class.getName()).log(Level.SEVERE, null, ex);
            try {
                r.put("status", 500);
            } catch (JSONException ex2) {
                Logger.getLogger(ColumnServlet.class.getName()).log(Level.SEVERE, null, ex);
            };
        }
        p.print(r);
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
        PrintWriter p = response.getWriter();
        User u = (User) request.getSession(false).getAttribute("user");
        try {
            JSONObject data = new JSONObject(IOUtils.toString(request.getInputStream()));
            if (db.userOwns(u.getId(), data.getInt("board_id"))) {
                if (db.deleteColumn(data.getInt("column_id"))) {
                    r.put("status", 200);
                } else {
                    r.put("status", 404);
                }
            } else {
                r.put("status", 403);
            }
        } catch (JSONException ex) {
            Logger.getLogger(ColumnServlet.class.getName()).log(Level.SEVERE, null, ex);
            try {
                r.put("status", 500);
            } catch (JSONException ex2) {
                Logger.getLogger(ColumnServlet.class.getName()).log(Level.SEVERE, null, ex);
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
