/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org;
import java.sql.*;
import java.util.*;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author Mariela
 */
public class DBManager {
    private final Connection con = DataBaseConnection.getInstance().getConnection();
     public List<User> getUsers(){
        Statement stm = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try{
            stm = con.createStatement();
            rs = stm.executeQuery("SELECT * FROM users");
            if(rs.next()){
                rs.beforeFirst();
                while(rs.next()){
                    User u = new User();
                    u.setId(rs.getInt("user_id"));
                    u.setTypeId(rs.getInt("type_id"));
                    u.setName(rs.getString("user_name"));
                    u.setLastName(rs.getString("user_last_name"));
                    u.setUsername(rs.getString("user_username"));
                    u.setEmail(rs.getString("user_email"));
                    u.setPassword(rs.getString("user_password"));
                    u.setTimestamp(rs.getString("user_created_at"));
                    users.add(u);
                }
            }else{
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return users;
    }
    
    public User getUser(String email){
        PreparedStatement stm = null;
        ResultSet rs = null;
        User u = new User();
        try{
            stm = con.prepareStatement("SELECT * FROM users WHERE user_email = ? LIMIT 1");
            stm.setString(1, email);
            rs = stm.executeQuery();
            if(rs.next()){
                rs.beforeFirst();
                while(rs.next()){
                    u.setId(rs.getInt("user_id"));
                    u.setTypeId(rs.getInt("type_id"));
                    u.setName(rs.getString("user_name"));
                    u.setLastName(rs.getString("user_last_name"));
                    u.setUsername(rs.getString("user_username"));
                    u.setEmail(rs.getString("user_email"));
                    u.setPassword(rs.getString("user_password"));
                    u.setTimestamp(rs.getString("user_created_at"));
                }
            }else{
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return u;
    }
    public List<User> getUsers(String token, String param){
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try{
            stm = con.prepareStatement("SELECT * FROM users WHERE " + token + " = ?");
            stm.setString(1, param);
            rs = stm.executeQuery();
            if(rs.next()){
                rs.beforeFirst();
                while(rs.next()){
                    User u = new User();
                    u.setId(rs.getInt("user_id"));
                    u.setTypeId(rs.getInt("type_id"));
                    u.setName(rs.getString("user_name"));
                    u.setLastName(rs.getString("user_last_name"));
                    u.setUsername(rs.getString("user_username"));
                    u.setEmail(rs.getString("user_email"));
                    u.setPassword(rs.getString("user_password"));
                    u.setTimestamp(rs.getString("user_created_at"));
                    users.add(u);
                }
            }else{
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }
 
     public List<Board> getBoards(String token, int param){
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Board> boards = new ArrayList<>();
        try{
            stm = con.prepareStatement("SELECT * FROM boards WHERE " + token + " = ?");
            stm.setInt(1, param);
            rs = stm.executeQuery();
            if(rs.next()){
                rs.beforeFirst();
                while(rs.next()){
                    Board b = new Board();
                    b.setId(Integer.parseInt(rs.getString("board_id")));
                    b.setName(rs.getString("board_name"));
                    b.setColor(rs.getString("board_color"));
                    b.setDescription(rs.getString("board_description"));
                    b.setTimestamp(rs.getString("board_created_at"));
                    b.setUserId(Integer.parseInt(rs.getString("user_id")));
                    boards.add(b);
                }
            }else{
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return boards;
    }
    
     public List<Column> getColumns(int boardId){
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Column> columns = new ArrayList<>();
        try{
            stm = con.prepareStatement("SELECT * FROM columns WHERE board_id = ?");
            stm.setInt(1, boardId);
            rs = stm.executeQuery();
            if(rs.next()){
                rs.beforeFirst();
                while(rs.next()){
                    Column c = new Column();
                    c.setBoard_id(rs.getInt("board_id"));
                    c.setColumn_id(rs.getInt("column_id"));
                    c.setColumn_name(rs.getString("column_name"));
                    columns.add(c);
                }
            }else{
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return columns;
    }
    public List<Card> getCards(int columnId){
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Card> cards = new ArrayList<>();
        try{
            stm = con.prepareStatement("SELECT * FROM cards WHERE column_id = ?");
            stm.setInt(1, columnId);
            rs = stm.executeQuery();
            if(rs.next()){
                rs.beforeFirst();
                while(rs.next()){
                    Card c = new Card();
                    c.setCard_id(rs.getInt("card_id"));
                    c.setColumnId(rs.getInt("column_id"));
                    c.setUserId(rs.getInt("user_id"));
                    c.setCard_name(rs.getString("card_name"));
                    c.setCard_description(rs.getString("card_description"));
                    cards.add(c);
                }
            }else{
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cards;
    }
    
    public Card getCard(int cardId){
        PreparedStatement stm = null;
        ResultSet rs = null;
        Card card = new Card();
        try{
            stm = con.prepareStatement("SELECT * FROM cards WHERE card_id = ? LIMIT 1");
            stm.setInt(1, cardId);
            rs = stm.executeQuery();
            if(rs.next()){
                rs.beforeFirst();
                while(rs.next()){
                    card.setCard_id(rs.getInt("card_id"));
                    card.setColumnId(rs.getInt("column_id"));
                    card.setUserId(rs.getInt("user_id"));
                    card.setCard_name(rs.getString("card_name"));
                    card.setCard_description(rs.getString("card_description"));
                }
            }else{
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return card;
    }
    
    public boolean userExists(String email){
        PreparedStatement stm = null;
        ResultSet rs = null;
        try{
            String q = "SELECT * FROM users WHERE user_email = ? LIMIT 1";
            stm = con.prepareStatement(q);
            stm.setString(1, email);
            rs = stm.executeQuery();
            if(rs.next()){
                return true;
            }else{
                throw new Exception("No existe el usuario");
            }
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean registerUser(String name, String lastName, String username, String password,
            String email){
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try{
            stm = con.prepareStatement("INSERT INTO users VALUES(null, ?, ?, ?, ?, ?, ?, ?);");
            String hash_p = BCrypt.hashpw(password, BCrypt.gensalt());
            Timestamp t = new Timestamp(System.currentTimeMillis());
            stm.setInt(1, 1);
            stm.setString(2, name);
            stm.setString(3, username);
            stm.setString(4, lastName);
            stm.setString(5, email);
            stm.setString(6, hash_p);
            stm.setString(7, t.toString());
            rs = stm.executeUpdate();
            if(rs > 0){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
       return flag;
    }
    
    public boolean checkPassword(String email, String pass){
        try {
            PreparedStatement stm = con.prepareStatement("SELECT user_password FROM users WHERE user_email = ?");
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            rs.next();
            String hash_pass = rs.getString("user_password");
            return BCrypt.checkpw(pass, hash_pass);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean registerBoard(String name, int userId, String t, String color, String desc){
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try{
            stm = con.prepareStatement("INSERT INTO boards VALUES(null, ?, ?, ?, ?, ?);");
            stm.setString(1, name);
            stm.setInt(2, userId);
            stm.setString(3, t);
            stm.setString(4, color);
            stm.setString(5, desc);
            rs = stm.executeUpdate();
            if(rs > 0){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
       return flag;
    }
    
    public String registerBoardString(String name, int userId){
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try{
            stm = con.prepareStatement("INSERT INTO boards VALUES(null, ?, ?, ?);");
            Timestamp t = new Timestamp(System.currentTimeMillis());
            stm.setString(1, name);
            stm.setInt(2, userId);
            stm.setString(3, t.toString());
            rs = stm.executeUpdate();
            if(rs > 0){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
       return stm.toString();
    }
    
    public String registerUserString(String name, String lastName, String username, String password,
            String email){
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try{
            stm = con.prepareStatement("INSERT INTO users VALUES(null, ?, ?, ?, ?, ?, ?, ?);");
            String hash_p = BCrypt.hashpw(password, BCrypt.gensalt());
            Timestamp t = new Timestamp(System.currentTimeMillis());
            stm.setInt(1, 1);
            stm.setString(2, name);
            stm.setString(3, username);
            stm.setString(4, lastName);
            stm.setString(5, email);
            stm.setString(6, hash_p);
            stm.setString(7, t.toString());
            rs = stm.executeUpdate();
            if(rs > 0){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
       return stm.toString();
    }
    
    public Board getBoard(String token, String value){
        PreparedStatement stm = null;
        ResultSet rs = null;
        Board b = new Board();
        try{
            stm = con.prepareStatement("SELECT * FROM boards WHERE "+ token +" = ? LIMIT 1");
            stm.setString(1, value);
            rs = stm.executeQuery();
            if(rs.next()){
                rs.beforeFirst();
                while(rs.next()){
                    b.setId(rs.getInt("board_id"));
                    b.setName(rs.getString("board_name"));
                    b.setUserId(rs.getInt("user_id"));
                    b.setTimestamp(rs.getString("board_created_at"));
                    b.setColor(rs.getString("board_color"));
                    b.setDescription(rs.getString("board_description"));
                }
            }else{
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
    public Column getColumn(String column_id, int value){
        PreparedStatement stm = null;
        ResultSet rs = null;
        Column c = new Column();
        try{
            stm = con.prepareStatement("SELECT * FROM columns WHERE column_id = ? LIMIT 1");
            stm.setInt(1, value);
            rs = stm.executeQuery();
            if(rs.next()){
                rs.beforeFirst();
                while(rs.next()){
                   c.setBoard_id(rs.getInt("board_id"));
                   c.setColumn_id(rs.getInt("column_id"));
                   c.setColumn_name(rs.getString("column_name"));
                }
            }else{
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return c;
    }
    
    public boolean updateBoard(int boardId, String name, String color, String description){
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try{
            stm = con.prepareStatement("UPDATE boards SET board_name = ?, board_color = ?, board_description = ? WHERE boards.board_id = ?");
            stm.setString(1, name);
            stm.setString(2, color);
            stm.setString(3, description);
            stm.setInt(4, boardId);
            rs = stm.executeUpdate();
            if(rs > 0){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
       return flag;
    }
    public boolean deleteBoard(int boardId){
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try{
            stm = con.prepareStatement("DELETE FROM boards WHERE boards.board_id = ?");
            stm.setInt(1,boardId);
            rs = stm.executeUpdate();
            if(rs > 0){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
       return flag;
    }
    
    public boolean registerColumn(String name, int boardId){
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try{
            stm = con.prepareStatement("INSERT INTO columns (column_id, board_id, column_name) VALUES (NULL, ?,  ? );");
            stm.setInt(1, boardId);
            stm.setString(2, name);
            rs = stm.executeUpdate();
            if(rs > 0){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
       return flag;
    }
    
     public boolean deleteColumn(int columnId){
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try{
            stm = con.prepareStatement("DELETE FROM columns WHERE columns.column_id = ?");
            stm.setInt(1, columnId);
            rs = stm.executeUpdate();
            if(rs > 0){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
       return flag;
    }
    
     public boolean updateColumn(int columnId, String newName){
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try{
            stm = con.prepareStatement("UPDATE columns SET column_name = ? WHERE columns.column_id = ?");
            stm.setString(1, newName);
            stm.setInt(2, columnId);
            rs = stm.executeUpdate();
            if(rs > 0){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
       return flag;
    }
     public int registerCard(String name, int columnId, String description, int userId){
        PreparedStatement stm = null;
        int rs;
        int result = 0;
        try{
            stm = con.prepareStatement("INSERT INTO cards (card_id, column_id, user_id, card_name, card_description) VALUES (NULL, ? , ? ,  ? , ?);", 
                    Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, columnId);
            stm.setInt(2, userId);
            stm.setString(3, name);
            stm.setString(4, description);
            rs = stm.executeUpdate();
            ResultSet keys = stm.getGeneratedKeys();
            if(rs > 0){
                keys.next();
                result = keys.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
       return result;
    }
    public int registerColumn(int boardId, String name){
        PreparedStatement stm = null;
        int rs;
        int result = 0;
        try{
            stm = con.prepareStatement("INSERT INTO columns (column_id, board_id, column_name) VALUES (NULL, ?,  ? );", 
                    Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, boardId);
            stm.setString(2, name);
            rs = stm.executeUpdate();
            ResultSet keys = stm.getGeneratedKeys();
            if(rs > 0){
                keys.next();
                result = keys.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
       return result;
    }
    
     public boolean deleteCard(int cardId){
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try{
            stm = con.prepareStatement("DELETE FROM cards WHERE cards.card_id = ?");
            stm.setInt(1, cardId);
            rs = stm.executeUpdate();
            if(rs > 0){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
       return flag;
    }
    
     public boolean updateCard(int cardId, String newName, String newDes){
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try{
            stm = con.prepareStatement("UPDATE cards SET card_name = ?, card_description = ? WHERE cards.card_id = ?");
            stm.setString(1, newName);
            stm.setString(2, newDes);
            stm.setInt(3, cardId);
            rs = stm.executeUpdate();
            if(rs > 0){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
       return flag;
    }
}