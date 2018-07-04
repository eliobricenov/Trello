/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author JoseUrdaneta
 */
public class GetColumnsData extends HttpServlet {

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
            out.println("<title>Servlet GetColumns</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetColumns at " + request.getContextPath() + "</h1>");
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
        int boardId = (request.getParameter("board_id")== null)? 0 : Integer.parseInt(request.getParameter("board_id"));
        if (boardId != 0){
            try {
                System.out.println(boardId);
                List<Column> columns = db.getColumns(boardId);
                for(Column c : columns){
                    JSONObject column_json = new JSONObject();
                    column_json.put("column_id", c.getColumn_id());
                    column_json.put("board_id", c.getBoard_id());
                    column_json.put("column_name", c.getColumn_name());
                    List<Card> cards = db.getCards(c.getColumn_id());
                    JSONArray cards_json = new JSONArray();
                    for(Card card: cards){
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
                Logger.getLogger(GetColumnsData.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    r.put("status", 404);
                } catch (JSONException ex1) {
                    Logger.getLogger(GetColumnsData.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }else{
            try {
                r.put("status", 500);
            } catch (JSONException ex) {
                Logger.getLogger(GetColumnsData.class.getName()).log(Level.SEVERE, null, ex);
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
