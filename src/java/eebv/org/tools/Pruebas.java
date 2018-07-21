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
import org.json.JSONArray;
/**
 *
 * @author GGsus
 */
public class Pruebas {
    public static void main(String[] args){
        DBManager db = new DBManager();
        Card c = db.getCard(10);
        System.out.println(CardServices.cardToJSON(c));
    }
    
}
