/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.servlets;

import eebv.org.models.MyFile;
import eebv.org.models.User;
import eebv.org.services.FileServices;
import eebv.org.tools.DBManager;
import eebv.org.tools.FileManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Elio Briceño
 */
@MultipartConfig
public class FileServlet extends HttpServlet {

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
            out.println("<title>Servlet FileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FileServlet at " + request.getContextPath() + "</h1>");
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
        MyFile f = db.getFile(Integer.parseInt(request.getParameter("file_id")));
        File my_file = new File(f.getPath());
        response.setContentType("file");
        response.setHeader("Content-disposition", "attachment; filename=" + f.getName());
        FileOutputStream out = new FileOutputStream(f.getPath());
        FileInputStream in = new FileInputStream(my_file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();

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
        PrintWriter w = response.getWriter();
        JSONObject r = new JSONObject();
        User u = (User) request.getSession(false).getAttribute("user");
        try {
            FileManager fm = new FileManager();
            Part p = request.getPart("file");
            JSONObject data = new JSONObject(request.getParameter("data"));
            String t = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Timestamp(System.currentTimeMillis()));
            MyFile f = fm.createFile(p, u.getId(), data.getInt("card_id"), t);
            List<MyFile> files = db.getFiles(data.getInt("card_id"));
            JSONArray files_j = new JSONArray();
            if (files != null) {
                for (MyFile fs : files) {
                    files_j.put(FileServices.fileToJSON(fs));
                }
                r.put("files", files_j);
            }else{
               r.put("files", new JSONArray());
            }
            r.put("status", 200);
            r.put("data", new JSONObject().put("files", files_j).put("created_file", 
                        FileServices.fileToJSON(f)));
        } catch (JSONException ex) {
            try {
                Logger.getLogger(FileServlet.class.getName()).log(Level.SEVERE, null, ex);
                r.put("status", 500);
            } catch (JSONException ex1) {
                Logger.getLogger(FileServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        w.print(r);
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
        FileManager fm = new FileManager();
        JSONObject r = new JSONObject();
        JSONArray files_j = new JSONArray();
        PrintWriter p = response.getWriter();
        try {
            JSONObject data = new JSONObject(IOUtils.toString(request.getInputStream()));
            if (db.deleteFile(data.getInt("file_id"))) {
                r.put("status", 200);
                List<MyFile> files = db.getFiles(data.getInt("card_id"));
                if(files != null){
                   for(MyFile f : files){
                       files_j.put(FileServices.fileToJSON(f));
                   } 
                   r.put("data", files_j);
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
