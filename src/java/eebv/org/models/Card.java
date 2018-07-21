/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.models;

import eebv.org.*;
import org.json.JSONArray;

/**
 *
 * @author JoseUrdaneta
 */
public class Card {
    private int card_id;
    private int columnId;
    private int userId;
    private String card_name;
    private String card_description;
    private JSONArray comments;

    /**
     * @return the card_id
     */
    public int getCard_id() {
        return card_id;
    }

    /**
     * @param card_id the card_id to set
     */
    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    /**
     * @return the columnId
     */
    public int getColumnId() {
        return columnId;
    }

    /**
     * @param columnId the columnId to set
     */
    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the card_name
     */
    public String getCard_name() {
        return card_name;
    }

    /**
     * @param card_name the card_name to set
     */
    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    /**
     * @return the card_description
     */
    public String getCard_description() {
        return card_description;
    }

    /**
     * @param card_description the card_description to set
     */
    public void setCard_description(String card_description) {
        this.card_description = card_description;
    }

    /**
     * @return the comments
     */
    public JSONArray getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(JSONArray comments) {
        this.comments = comments;
    }
}
