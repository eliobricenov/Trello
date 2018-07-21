/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.services;

import eebv.org.models.*;
import eebv.org.tools.DBManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author GGsus
 */
public class CardServices {

    public static JSONObject cardToJSON(Card c) {
        JSONObject result = new JSONObject();
        try { 
            result.put("card_id", c.getCard_id());
            result.put("card_name", c.getCard_name());
            result.put("card_description", c.getCard_description());
            result.put("user_id", c.getUserId());
            result.put("column_id", c.getColumnId());
            DBManager db = new DBManager();
            List<Comment> comments = db.getComments(c.getCard_id());
            JSONArray comments_j = new JSONArray();
            if (comments != null) {
                for (Comment cm : comments) {
                    JSONObject cmj = new JSONObject();
                    cmj.put("comment_id", cm.getId());
                    cmj.put("card_id", cm.getCardId());
                    cmj.put("comment_text", cm.getText());
                    cmj.put("comment_created_at", cm.getTimestamp());
                    cmj.put("user_id", cm.getUserId());
                    cmj.put("user_username", cm.getUserUsername());
                    comments_j.put(cmj);
                }
                result.put("comments", comments_j);
            }else{
               result.put("comments", new JSONArray());
            }
            
        } catch (JSONException ex) {
            Logger.getLogger(CardServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
