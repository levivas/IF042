//package com.mymoney.entity;
//
//
//import org.springframework.mock.staticmock.MockStaticEntityMethods;
//
//
//import javax.persistence.*;
//
//import java.io.Serializable;
//import java.sql.Time;
//import java.sql.Timestamp;
//
///**
//* Created by Kukusya on 20.04.2015.
//*/
//@Entity
//@Table(name="users")
//@NamedQuery(name=User.findAll, query="SELECT u FROM User u")
//public class User implements Serializable {
//    private static final long serialversionUID = 1L;
//    private int userId;
//    private String login;
//    private String firstname;
//    private String surname;
//    private String password;
//
//    private Timestamp dateReg;
//
//    public User() {
//    }
//
//    public User(int userId) {
//        this.userId = userId;
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_id")
//    public int getUserId() {
//        return this.userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//
//    @Column(name = "login")
//    public String getLogin() {
//        return this.login;
//    }
//
//    public void setLogin(String login) {
//        this.login = login;
//    }
//
//
//    @Column(name="firstname")
//    public String getFirstname(){
//        return this.firstname;
//    }
//
//    public void setFirstname(String firstname){
//        this.firstname = firstname;
//    }
//
//    @Column(name = "surname")
//    public String getSurname(){
//        return this.surname;
//    }
//
//    public void setSurname(String surname){
//         this.surname = surname;
//    }
//
//
//    @Column(name="password")
//    public String getPassword(){
//        return this.password;
//    }
//
//    public void setPassword(String password){
//        this.password = password;
//    }
//
//    @Column(name="date_reg")
//    public Timestamp getDateReg(){
//        return this.dateReg;
//    }
//
//    public void setDateReg(Timestamp dateReg){
//        this.dateReg = dateReg;
//    }
//
//}
//
