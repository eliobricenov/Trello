/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.tools;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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

/**
 *
 * @author JoseUrdaneta
 */
@WebFilter(urlPatterns = {"/views/board.html", "/views/columns.html"})
public class MyFilter implements Filter {
    
    public MyFilter() {
    }    
    
        @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
     PrintWriter out = res.getWriter();
        HttpServletRequest myReq = (HttpServletRequest) req;
        HttpServletResponse myRes = (HttpServletResponse) res;
        HttpSession mySession = myReq.getSession();// don't create if it doesn't exist

        if (mySession.isNew()) {
            //myjson.put("success", false).put("url", "index.html").put("message", "You are not allowed to get here");
            mySession.invalidate();
            myRes.sendRedirect(myReq.getContextPath()+"/views/");
        } else {
            //We can not clear the browser cache, but we can make that our pages will not be cached
            myRes.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
            myRes.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
            myRes.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
            myRes.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility                
            //System.out.println("ESTAS LOGGEADO, PUEDES ENTRAR...");
        }
        chain.doFilter(req, res);
        //out.print(myjson.toString());
    }

    @Override
    public void destroy() {
    }

}
