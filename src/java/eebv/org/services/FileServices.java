/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.services;

import org.json.JSONObject;
import eebv.org.models.MyFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
/**
 *
 * @author Elio Briceño
 */
public class FileServices {
    
    public static JSONObject fileToJSON(MyFile f){
        JSONObject r = new JSONObject();
        
        try {
            r.put("file_id", f.getId());
            r.put("file_name", f.getName());
            r.put("user_id", f.getUserId());
            r.put("card_id", f.getCardId());
            r.put("user_username", f.getUser_Username());
            r.put("created_at", f.getTimestamp());
            r.put("file_url", f.getUrl());
        } catch (JSONException ex) {
            Logger.getLogger(FileServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  r;
    }
}
