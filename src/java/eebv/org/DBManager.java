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
            stm = con.prepareStatement("SELECT * FROM users WHERE ? = ?");
            stm.setString(1, param);
            stm.setString(2, token);
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
    
    private int getRowCount(ResultSet rs){
        int size = 0;
        try {
            while(rs.next()){
                size++;
            }
        } catch (SQLException ex) {
            return 0;
        }
        return size;
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
}