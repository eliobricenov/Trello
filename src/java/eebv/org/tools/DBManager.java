/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.tools;

import eebv.org.models.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Properties;

/**
 *
 * @author JoseUrdaneta
 */
public class DBManager {

    private final Connection con = DataBaseConnection.getInstance().getConnection();
    
    private Properties user_p = new Properties();
    private Properties board_p = new Properties();
    private Properties card_p = new Properties();
    private Properties column_p = new Properties();
    private Properties comment_p = new Properties();
    public DBManager(){
        try {
            this.user_p.load(DBManager.class.getResourceAsStream("/eebv/org/properties/UserQueries.properties"));
            this.card_p.load(DBManager.class.getResourceAsStream("/eebv/org/properties/CardQueries.properties"));
            this.column_p.load(DBManager.class.getResourceAsStream("/eebv/org/properties/ColumnQueries.properties"));
            this.board_p.load(DBManager.class.getResourceAsStream("/eebv/org/properties/BoardQueries.properties"));
            this.comment_p.load(DBManager.class.getResourceAsStream("/eebv/org/properties/CommentQueries.properties"));
        } catch (IOException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean registerCollab(int userId, int boardId, int memberType) {
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try {
            stm = con.prepareStatement(user_p.getProperty("registerCollab"));
            stm.setInt(1, boardId);
            stm.setInt(2, userId);
            stm.setInt(3, memberType);
            rs = stm.executeUpdate();
            if (rs > 0) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }
    
    public boolean deleteCollab(int userId, int boardId) {
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try {
            stm = con.prepareStatement(user_p.getProperty("deleteCollab"));
            stm.setInt(1, boardId);
            stm.setInt(2, userId);
            rs = stm.executeUpdate();
            if (rs > 0) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean updateCollabs(JSONArray collabs, int boardId) {
        boolean flag = false;
        ResultSet rs = null;
        DBManager db = new DBManager();
        List<User> admins = db.getCollabs(boardId);
        try {
            //if there are collabs
            if (collabs.length() > 0) {
                //Check if users in new admin set are already admins
                for (int i = 0; i < collabs.length(); i++) {
                    boolean found = false;
                    if (admins != null) {
                        for (User admin : admins) {
                            if (collabs.getJSONObject(i).getInt("id") == admin.getId()) {
                                found = true;
                            }
                        }
                    }
                    // if not, add him/her
                    if (!found) {
                        if (db.registerCollab(collabs.getJSONObject(i).getInt("id"), boardId, 2)) {
                            flag = true;
                        } else {
                            return false;
                        }
                    }
                }

                //Check if collabs in database are in new collabs set
                //Deleting all old data
                admins = db.getCollabs(boardId);
                for (User admin : admins) {
                    boolean found = false;
                    for (int i = 0; i < collabs.length(); i++) {
                        if (admin.getId() == collabs.getJSONObject(i).getInt("id")) {
                            found = true;
                        }
                    }
                    if (!found) {
                        if (db.deleteCollab(admin.getId(), boardId)) {
                            flag = true;
                        } else {
                            return false;
                        }
                    }
                }
                flag = true;
            } else {
                //if not, it means they were all deleted so we delete them
                // from the DB

                if (admins != null) {
                    if (admins.size() > 0) {
                        for (User a : admins) {
                            flag = db.deleteCollab(a.getId(), boardId);
                        }
                    } else {
                        flag = true;
                    }
                } else {
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public List<User> getUsers() {
        Statement stm = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            stm = con.createStatement();
            rs = stm.executeQuery("SELECT * FROM users");
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
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
            } else {
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return users;
    }

    public List<User> getCollabs(int boardId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            stm = con.prepareStatement(user_p.getProperty("getCollabs"));
            stm.setInt(1, boardId);
            rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("user_id"));
                    u.setName(rs.getString("user_username"));
                    u.setBoardId(rs.getInt("board_id"));
                    u.setCredential(rs.getInt("type_board_user_id"));
                    users.add(u);
                }
            } else {
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            return null;
        }
        return users;
    }

    public User getUser(String username) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        User u = new User();
        try {
            stm = con.prepareStatement(user_p.getProperty("getUser"));
            stm.setString(1, username);
            rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    u.setId(rs.getInt("user_id"));
                    u.setTypeId(rs.getInt("type_id"));
                    u.setName(rs.getString("user_name"));
                    u.setLastName(rs.getString("user_last_name"));
                    u.setUsername(rs.getString("user_username"));
                    u.setEmail(rs.getString("user_email"));
                    u.setPassword(rs.getString("user_password"));
                    u.setTimestamp(rs.getString("user_created_at"));
                }
            } else {
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return u;
    }

    public List<User> getUsers(String token, String param) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            stm = con.prepareStatement("SELECT * FROM users WHERE " + token + " = ?");
            stm.setString(1, param);
            rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
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
            } else {
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public List<Board> getBoards(String token, int param) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Board> boards = new ArrayList<>();
        try {
            stm = con.prepareStatement("SELECT * FROM boards WHERE " + token + " = ?");
            stm.setInt(1, param);
            rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    Board b = new Board();
                    b.setId(Integer.parseInt(rs.getString("board_id")));
                    b.setName(rs.getString("board_name"));
                    b.setColor(rs.getString("board_color"));
                    b.setDescription(rs.getString("board_description"));
                    b.setTimestamp(rs.getString("board_created_at"));
                    b.setUserId(Integer.parseInt(rs.getString("user_id")));
                    boards.add(b);
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return boards;
    }
    
    public List<Board> searchBoards(String param) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Board> boards = new ArrayList<>();
        try {
            stm = con.prepareStatement(board_p.getProperty("searchBoards"));
            stm.setString(1, param + "%");
            rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    Board b = new Board();
                    b.setId(Integer.parseInt(rs.getString("board_id")));
                    b.setName(rs.getString("board_name"));
                    b.setColor(rs.getString("board_color"));
                    b.setDescription(rs.getString("board_description"));
                    b.setTimestamp(rs.getString("board_created_at"));
                    b.setUserId(Integer.parseInt(rs.getString("user_id")));
                    boards.add(b);
                }
            } else {
                boards = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return boards;
    }

    public List<Column> getColumns(int boardId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Column> columns = new ArrayList<>();
        try {
            stm = con.prepareStatement(column_p.getProperty("getColumns"));
            stm.setInt(1, boardId);
            rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    Column c = new Column();
                    c.setBoard_id(rs.getInt("board_id"));
                    c.setUser_id(rs.getInt("user_id"));
                    c.setColumn_id(rs.getInt("column_id"));
                    c.setColumn_name(rs.getString("column_name"));
                    columns.add(c);
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return columns;
    }

    public List<Card> getCards(int columnId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Card> cards = new ArrayList<>();
        try {
            stm = con.prepareStatement(card_p.getProperty("getCards"));
            stm.setInt(1, columnId);
            rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    Card c = new Card();
                    c.setCard_id(rs.getInt("card_id"));
                    c.setColumnId(rs.getInt("column_id"));
                    c.setUserId(rs.getInt("user_id"));
                    c.setCard_name(rs.getString("card_name"));
                    c.setCard_description(rs.getString("card_description"));
                    cards.add(c);
                }
            } else {
               return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cards;
    }

    public Card getCard(int cardId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Card card = new Card();
        try {
            stm = con.prepareStatement(card_p.getProperty("getCard"));
            stm.setInt(1, cardId);
            rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    card.setCard_id(rs.getInt("card_id"));
                    card.setColumnId(rs.getInt("column_id"));
                    card.setUserId(rs.getInt("user_id"));
                    card.setCard_name(rs.getString("card_name"));
                    card.setCard_description(rs.getString("card_description"));
                }
            } else {
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return card;
    }

    public boolean usernameExists(String username) {
        PreparedStatement stm = null;
        boolean flag = true;
        ResultSet rs = null;
        try {
            String q = this.user_p.getProperty("checkUsername");
            stm = con.prepareStatement(q);
            stm.setString(1, username);
            rs = stm.executeQuery();
            if (rs.next()) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            return false;
        }

        return flag;
    }

    public boolean emailExists(String email) {
        PreparedStatement stm = null;
        boolean flag = true;
        ResultSet rs = null;
        try {
            String q = user_p.getProperty("checkEmail");
            stm = con.prepareStatement(q);
            stm.setString(1, email);
            rs = stm.executeQuery();
            if (rs.next()) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            return false;
        }

        return flag;
    }

    public boolean registerUser(String name, String lastName, String username, String password,
            String email) {
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try {
            stm = con.prepareStatement(user_p.getProperty("registerUser"));
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
            if (rs > 0) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean checkPassword(String username, String pass) {
        try {
            PreparedStatement stm = con.prepareStatement(user_p.getProperty("getPassword"));
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            rs.next();
            String hash_pass = rs.getString("user_password");
            return BCrypt.checkpw(pass, hash_pass);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Board getBoard(String token, String value) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Board b = new Board();
        try {
            stm = con.prepareStatement("SELECT * FROM boards WHERE " + token + " = ? LIMIT 1");
            stm.setString(1, value);
            rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    b.setId(rs.getInt("board_id"));
                    b.setName(rs.getString("board_name"));
                    b.setUserId(rs.getInt("user_id"));
                    b.setTimestamp(rs.getString("board_created_at"));
                    b.setColor(rs.getString("board_color"));
                    b.setDescription(rs.getString("board_description"));
                }
            } else {
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    public Column getColumn(String column_id, int value) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Column c = new Column();
        try {
            stm = con.prepareStatement(column_p.getProperty("getColumn"));
            stm.setInt(1, value);
            rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    c.setBoard_id(rs.getInt("board_id"));
                    c.setColumn_id(rs.getInt("column_id"));
                    c.setColumn_name(rs.getString("column_name"));
                }
            } else {
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return c;
    }
    
     public Comment getComment(int commentId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Comment c = new Comment();
        try {
            stm = con.prepareStatement(comment_p.getProperty("getComment"));
            stm.setInt(1, commentId);
            rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    c.setId(rs.getInt("comment_id"));
                    c.setCardId(rs.getInt("card_id"));
                    c.setUserUsername(rs.getString("user_username"));
                    c.setText(rs.getString("comment_text"));
                    c.setUserId(rs.getInt("user_id"));
                    c.setTimestamp(rs.getString("comment_created_at"));
                }
            } else {
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return c;
    }
     
     
     public List<Comment> getComments(int cardId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Comment> comments = new ArrayList<>();
        try {
            stm = con.prepareStatement(comment_p.getProperty("getComments"));
            stm.setInt(1, cardId);
            rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    Comment c = new Comment();
                    c.setId(rs.getInt("comment_id"));
                    c.setCardId(rs.getInt("card_id"));
                    c.setText(rs.getString("comment_text"));
                    c.setUserUsername(rs.getString("user_username"));
                    c.setTimestamp(rs.getString("comment_created_at"));
                    c.setUserId(rs.getInt("user_id"));
                    comments.add(c);
                }
            } else {
                throw new Exception("No results");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return comments;
    }

    public boolean updateBoard(int boardId, String name, String color, String description,
            JSONArray collabs) {
        PreparedStatement stm = null;
        DBManager db = new DBManager();
        int rs;
        boolean flag = false;
        try {
            stm = con.prepareStatement(board_p.getProperty("updateBoard"));
            stm.setString(1, name);
            stm.setString(2, color);
            stm.setString(3, description);
            stm.setInt(4, boardId);
            rs = stm.executeUpdate();
            if (rs > 0) {
                flag = db.updateCollabs(collabs, boardId);
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }
    
    public boolean updateComment(int commentId, String text) {
        PreparedStatement stm = null;
        DBManager db = new DBManager();
        int rs;
        boolean flag = false;
        try {
            stm = con.prepareStatement(comment_p.getProperty("updateComment"));
            stm.setString(1, text);
            stm.setInt(2, commentId);
            rs = stm.executeUpdate();
            if (rs > 0) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }
    
    public boolean deleteBoard(int boardId) {
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try {
            stm = con.prepareStatement(board_p.getProperty("deleteBoard"));
            stm.setInt(1, boardId);
            rs = stm.executeUpdate();
            if (rs > 0) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean deleteColumn(int columnId) {
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try {
            stm = con.prepareStatement(column_p.getProperty("deleteColumn"));
            stm.setInt(1, columnId);
            rs = stm.executeUpdate();
            if (rs > 0) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean updateColumn(int columnId, String newName) {
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try {
            stm = con.prepareStatement(column_p.getProperty("updateColumn"));
            stm.setString(1, newName);
            stm.setInt(2, columnId);
            rs = stm.executeUpdate();
            if (rs > 0) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public int registerCard(String name, int columnId, String description, int userId) {
        PreparedStatement stm = null;
        int rs;
        int result = 0;
        try {
            stm = con.prepareStatement(card_p.getProperty("registerCard"),
                    Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, columnId);
            stm.setInt(2, userId);
            stm.setString(3, name);
            stm.setString(4, description);
            rs = stm.executeUpdate();
            ResultSet keys = stm.getGeneratedKeys();
            if (rs > 0) {
                keys.next();
                result = keys.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public int registerColumn(int boardId, int userId, String name) {
        PreparedStatement stm = null;
        int rs;
        int result = 0;
        try {
            stm = con.prepareStatement(column_p.getProperty("registerColumn"),
                    Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, boardId);
            stm.setInt(2, userId);
            stm.setString(3, name);
            rs = stm.executeUpdate();
            ResultSet keys = stm.getGeneratedKeys();
            if (rs > 0) {
                keys.next();
                result = keys.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public boolean deleteCard(int cardId) {
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try {
            stm = con.prepareStatement(card_p.getProperty("deleteCard"));
            stm.setInt(1, cardId);
            rs = stm.executeUpdate();
            if (rs > 0) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }
    
    public boolean deleteComment(int commentId) {
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try {
            stm = con.prepareStatement(comment_p.getProperty("deleteComment"));
            stm.setInt(1, commentId);
            rs = stm.executeUpdate();
            if (rs > 0) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean updateCard(int cardId, String newName, String newDes) {
        PreparedStatement stm = null;
        int rs;
        boolean flag = false;
        try {
            stm = con.prepareStatement(card_p.getProperty("updateCard"));
            stm.setString(1, newName);
            stm.setString(2, newDes);
            stm.setInt(3, cardId);
            rs = stm.executeUpdate();
            if (rs > 0) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean isBoardMaster(int userId, int boardId) {
        boolean flag = false;
        try {
            PreparedStatement stm = con.prepareStatement(user_p.getProperty("isBoardMaster"));
            stm.setInt(1, userId);
            stm.setInt(2, boardId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int typeId = rs.getInt("type_board_user_id");
                flag = typeId == 1;
            } else {
                flag = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return flag;
    }

    public boolean registerBoard(String name, int userId, String t, String color, String desc,
            JSONArray collabs) {
        PreparedStatement stm = null;
        PreparedStatement stm2 = null;
        int rs;
        boolean flag = false;
        try {
            stm = con.prepareStatement(board_p.getProperty("registerBoard"),
                    Statement.RETURN_GENERATED_KEYS);
            stm2 = con.prepareStatement(user_p.getProperty("registerCollab"));
            stm.setString(1, name);
            stm.setInt(2, userId);
            stm.setString(3, t);
            stm.setString(4, color);
            stm.setString(5, desc);
            rs = stm.executeUpdate();
            ResultSet keys = stm.getGeneratedKeys();
            keys.next();
            int generatedId = keys.getInt(1);
            if (rs > 0) {
                stm2.setInt(1, generatedId);
                stm2.setInt(2, userId);
                stm2.setInt(3, 1);
                rs = stm2.executeUpdate();
                if (rs > 0) {
                    if (collabs.length() > 0) {
                        for (int i = 0; i < collabs.length(); i++) {
                            stm2.setInt(1, generatedId);
                            stm2.setInt(2, collabs.getJSONObject(i).getInt("id"));
                            stm2.setInt(3, 2);
                            rs = stm2.executeUpdate();
                            flag = (rs > 0);
                        }
                    } else {
                        stm2.setInt(1, generatedId);
                        stm2.setInt(2, userId);
                        stm2.setInt(3, 1);
                        rs = stm2.executeUpdate();
                        flag = (rs > 0);
                    }
                } else {
                    flag = false;
                }
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean isColumnOwner(int userId, int columnId) {
        boolean flag = false;
        try {
            PreparedStatement stm = con.prepareStatement(user_p.getProperty("isColumnOwner"));
            stm.setInt(1, columnId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int columnUserId = rs.getInt("user_id");
                flag = columnUserId == userId;
            } else {
                flag = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean isCardOwner(int userId, int cardId) {
        boolean flag = false;
        try {
            PreparedStatement stm = con.prepareStatement(user_p.getProperty("isCardOwner"));
            stm.setInt(1, cardId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int cardUserId = rs.getInt("user_id");
                flag = cardUserId == userId;
            } else {
                flag = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    public boolean isCommentOwner(int userId, int commentId) {
        boolean flag = false;
        try {
            PreparedStatement stm = con.prepareStatement(user_p.getProperty("isCollumnOwner"));
            stm.setInt(1, commentId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int cardUserId = rs.getInt("user_id");
                flag = cardUserId == userId;
            } else {
                flag = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean isCollab(int userId, int boardId) {
        boolean flag = false;
        try {
            PreparedStatement stm = con.prepareStatement(user_p.getProperty("isCollab"));
            stm.setInt(1, userId);
            stm.setInt(2, boardId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int typeId = rs.getInt("type_board_user_id");
                flag = typeId == 2;
            } else {
                flag = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return flag;
    }
    
    public int registerComment(int cardId, int userId, String text, String t) {
        PreparedStatement stm = null;
        int rs;
        int result = 0;
        try {
            stm = con.prepareStatement(comment_p.getProperty("registerComment"),
                    Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, cardId);
            stm.setInt(2, userId);
            stm.setString(3, text);
            stm.setString(4, t);
            rs = stm.executeUpdate();
            ResultSet keys = stm.getGeneratedKeys();
            if (rs > 0) {
                keys.next();
                result = keys.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
}
