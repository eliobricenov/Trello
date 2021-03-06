/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eebv.org.models;


/**
 *
 * @author Mariela
 */
public class User {

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the typeId
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    private String username;
    private String password;
    private int id;
    private String email;
    private String name;
    private String lastName;
    private int typeId;
    private String timestamp;
    private int credential;
    private int boardId;
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
        
    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void printInfo(){
        System.out.println("-----------------");
        System.out.println(this.getId());
        System.out.println(this.getName());
        System.out.println(this.getLastName());
        System.out.println(this.getEmail());
        System.out.println(this.getUsername());
        System.out.println(this.getPassword());
    }

    /**
     * @return the credential
     */
    public int getCredential() {
        return credential;
    }

    /**
     * @param credential the credential to set
     */
    public void setCredential(int credential) {
        this.credential = credential;
    }

    /**
     * @return the boardId
     */
    public int getBoardId() {
        return boardId;
    }

    /**
     * @param boardId the boardId to set
     */
    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }
}
