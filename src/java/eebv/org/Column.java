/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org;

import java.util.List;

/**
 *
 * @author JoseUrdaneta
 */
public class Column {
    private int column_id;
    private int board_id;
    private String column_name;
    private List<Card> column_cards;
    /**
     * @return the column_id
     */
    public int getColumn_id() {
        return column_id;
    }

    /**
     * @param column_id the column_id to set
     */
    public void setColumn_id(int column_id) {
        this.column_id = column_id;
    }

    /**
     * @return the board_id
     */
    public int getBoard_id() {
        return board_id;
    }

    /**
     * @param board_id the board_id to set
     */
    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    /**
     * @return the column_name
     */
    public String getColumn_name() {
        return column_name;
    }

    /**
     * @param column_name the column_name to set
     */
    public void setColumn_name(String column_name) {
         this.column_name = column_name;
    }

    /**
     * @return the column_cards
     */
    public List<Card> getColumn_cards() {
        return column_cards;
    }

    /**
     * @param column_cards the column_cards to set
     */
    public void setColumn_cards(List<Card> column_cards) {
        this.column_cards = column_cards;
    }
}
