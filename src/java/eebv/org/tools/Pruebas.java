/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.tools;

import java.util.List;
import eebv.org.models.*;
import eebv.org.services.CardServices;
import eebv.org.services.CommentServices;
import eebv.org.services.FileServices;
import org.json.JSONArray;

/**
 *
 * @author GGsus
 */
public class Pruebas {

    public static void main(String[] args) {
        DBManager db = new DBManager();
        FileManager fm = new FileManager();
//        List<MyFile> fs = db.getFiles(18);
//        JSONArray files = new JSONArray();
//        for(MyFile f : fs){
//            files.put(FileServices.fileToJSON(f));
//        }
//        System.out.println(files);
        System.out.println(fm.deleteFile(db.getFile(31)));
    }
}
