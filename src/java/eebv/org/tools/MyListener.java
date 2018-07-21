/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.tools;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author GGsus
 */

@WebListener
public class MyListener implements ServletContextListener{
    
    public void contextDestroyed(ServletContextEvent e, ServletRequest req, ServletResponse res){
        try {
            HttpServletResponse myRes = (HttpServletResponse) res;
            HttpServletRequest myReq = (HttpServletRequest) req;
            HttpSession mySession = myReq.getSession();
            mySession.invalidate();
            myRes.sendRedirect(myReq.getContextPath()+"/views/");
        } catch (IOException ex) {
            Logger.getLogger(MyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
