/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.services;

import eebv.org.models.Comment;
import org.json.JSONObject;

/**
 *
 * @author GGsus
 */
public class CommentServices {
    
    public static JSONObject commentToJSON(Comment c){
        try{
            JSONObject r = new JSONObject();
            r.put("comment_id", c.getId());
            r.put("comment_text", c.getText());
            r.put("user_id", c.getUserId());
            r.put("card_id", c.getCardId());
            r.put("user_username", c.getUserUsername());
            r.put("comment_created_at", c.getTimestamp());
            return r;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
