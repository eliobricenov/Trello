/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.services;

import eebv.org.tools.DBManager;
import eebv.org.models.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

/**
 *
 * @author Elio Briceño
 */
public class BoardServices {

    public static JSONObject boardToJSON(Board b) {
        DBManager db = new DBManager();
        JSONObject j = new JSONObject();
        try {
            j.put("board_id", b.getId());
            j.put("board_name", b.getName());
            j.put("board_color", b.getColor());
            j.put("board_description", b.getDescription());
            j.put("user_id", b.getUserId());
            j.put("board_created_at", b.getTimestamp());
            List<User> collab = db.getCollabs(b.getId());
            if (collab != null) {
                JSONArray collabs_inner = new JSONArray();
                for (User c : collab) {
                    JSONObject c_json = new JSONObject();
                    c_json.put("user_id", c.getId());
                    c_json.put("board_id", b.getId());
                    c_json.put("user_username", c.getName());
                    c_json.put("type_board_user_id", c.getCredential());
                    collabs_inner.put(c_json);
                }
                j.put("board_collaborators", collabs_inner);
            } else {
                j.put("board_collaborators", new JSONArray());
            }
        } catch (JSONException ex) {
            Logger.getLogger(BoardServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return j;
    }
    
    
}

