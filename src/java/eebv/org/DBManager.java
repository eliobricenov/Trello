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
                    u.setId(rs.getString("user_id"));
                    u.setTypeId(rs.getString("type_id"));
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
                    u.setId(rs.getString("user_id"));
                    u.setTypeId(rs.getString("type_id"));
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
                    u.setId(rs.getString("user_id"));
                    u.setTypeId(rs.getString("type_id"));
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
}