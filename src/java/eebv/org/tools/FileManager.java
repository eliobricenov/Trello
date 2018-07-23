/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.tools;

import eebv.org.models.MyFile;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;

/**
 *
 * @author Elio Briceño
 */
public class FileManager {
    private Properties file_p = new Properties();
    
    public FileManager(){
        try {
            this.file_p.load(DBManager.class.getResourceAsStream("/eebv/org/properties/FileQueries.properties"));
        } catch (IOException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public MyFile createFile(Part p, int userId, int cardId, String t) {
        InputStream filecontent = null;
        OutputStream os = null;
        DBManager db = new DBManager();
        MyFile f = new MyFile();
        try {
            if (p.getContentType() != null) {
                filecontent = p.getInputStream();
                String fileName = this.getFileName(p);
                String path = this.file_p.getProperty("rootPath") + fileName;
                int file_id = db.registerFile(cardId, userId, fileName, path, t);
                f = db.getFile(file_id);
                f.setUrl(this.file_p.getProperty("rootUrl") + String.valueOf(file_id));
                os = new FileOutputStream(path);
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = filecontent.read(bytes)) != -1) {
                    os.write(bytes, 0, read);
                }
                filecontent.close();
                os.close();
                return f;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
