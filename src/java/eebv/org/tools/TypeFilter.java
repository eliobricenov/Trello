/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.tools;

import eebv.org.models.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Elio Briceño
 */
@WebFilter(servletNames = {"ColumnServlet", "CardServlet", "CommentsServlet"})
public class TypeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            PrintWriter out = res.getWriter();
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;
            HttpSession session = request.getSession(false);// don't create if it doesn't exist
            User u = (User) session.getAttribute("user");
            DBManager db = new DBManager();
            String requestURI = request.getRequestURI();
            JSONObject r = new JSONObject();
            PrintWriter p = response.getWriter();
            if (request.getMethod().equalsIgnoreCase("GET")) {
                chain.doFilter(req, res);
            } else {
                int board_id = Integer.parseInt(request.getParameter("board_id"));
                if (db.isBoardMaster(u.getId(), board_id)) {
                    chain.doFilter(req, res);
                } else if (db.isCollab(u.getId(), board_id)) {
                    if (!request.getMethod().equalsIgnoreCase("POST")) {
                        switch (requestURI) {
                            //Case user tries to modify a column
                            case "/Trello/ColumnServlet": {
                                int column_id = Integer.parseInt(request.getParameter("column_id"));
                                if (db.isColumnOwner(u.getId(), column_id)) {
                                    chain.doFilter(req, res);
                                } else {
                                    r.put("status", 403);
                                    p.print(r);
                                }
                            }
                            break;

                            //Case user tries to modify a card
                            case "/Trello/CardServlet": {
                                int card_id = Integer.parseInt(request.getParameter("card_id"));
                                if (db.isCardOwner(u.getId(), card_id)) {
                                    chain.doFilter(req, res);
                                } else {
                                    r.put("status", 403);
                                    p.print(r);
                                }
                            }
                            break;
                            
                            //Case user tries to modify a comment
                            case "/Trello/CommentsServlet": {
                                int comment_id = Integer.parseInt(request.getParameter("comment_id"));
                                System.out.println(comment_id);
                                System.out.println(u.getId());
                                if (db.isCommentOwner(u.getId(), comment_id)) {
                                    chain.doFilter(req, res);
                                } else {
                                    r.put("status", 403);
                                    p.print(r);
                                }
                            }
                            break;
                        }
                    } else {
                        chain.doFilter(req, res);
                    }
                } else {
                    r.put("status", 403);
                    p.print(r);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TypeFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy() {
    }

}
