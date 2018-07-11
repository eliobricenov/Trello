/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.tools;

import eebv.org.models.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

/**
 *
 * @author JoseUrdaneta
 */
public class JSONParser {

    public JSONArray boardsToJSON(List<Board> boards) {
        JSONArray boards_j = new JSONArray();
        
        try {
            for (Board b : boards) {
                JSONObject json = new JSONObject();
//                if (Integer.valueOf(b.getId()) != null) {
//                    json.put("board_id", b.getId());
//                }
//                if (b.getName() != null){
//                    json.put("board_name", b.getName());
//                }
//                if (Integer.valueOf(b.getUserId()) != null){
//                    json.put("board_user_id", b.getUserId());
//                }
//                
//                if(b.getDescription() != null){
//                    json.put("board_description", b.getDescription());
//                }
//                if(b.)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return boards_j;
    }
}
