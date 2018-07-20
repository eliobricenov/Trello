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
public class ColumnServices {

    public static JSONObject columndToJSON(Column c) {
        DBManager db = new DBManager();
        JSONObject column_json = new JSONObject();
        try {
            column_json.put("column_id", c.getColumn_id());
            column_json.put("user_id", c.getUser_id());
            column_json.put("board_id", c.getBoard_id());
            column_json.put("column_name", c.getColumn_name());
            List<Card> cards = db.getCards(c.getColumn_id());
            JSONArray cards_json = new JSONArray();
            if (cards != null) {
                for (Card card : cards) {
                    JSONObject card_json = new JSONObject();
                    card_json.put("card_id", card.getCard_id());
                    card_json.put("user_id", card.getUserId());
                    card_json.put("column_id", card.getColumnId());
                    card_json.put("card_name", card.getCard_name());
                    card_json.put("card_description", card.getCard_description());
                    cards_json.put(card_json);
                };
                column_json.put("cards", cards_json);
            } else {
                column_json.put("card", new JSONArray());
            }
        } catch (Exception e) {
            Logger.getLogger(ColumnServices.class.getName()).log(Level.SEVERE, null, e);
        }
        return column_json;
    }
}
