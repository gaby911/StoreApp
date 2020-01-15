/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ProdusDao;
import dao.UserDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import model.Produs;
import model.User;

/**
 *
 * @author student
 */
public class MainService {
    private String url = "jdbc:mysql://localhost/examenpractic";
    private String user = "root";
    private String pass = "";
    private Connection con;
    private MainService(){
        try{con = DriverManager.getConnection(url,user,pass);}
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private static final class SingletonHolder{
        private static final MainService INSTANCE = new MainService();
    }
    public static MainService getInstance(){
        return SingletonHolder.INSTANCE;
    }
    public boolean inregistrare(User user){
        boolean rez = false;
        UserDao userDao = new UserDao(con);
        try{
            Optional<User> optionalUser = userDao.findUser(user.getUsername());
            if(!optionalUser.isPresent()){
                userDao.adaugaUser(user);
                rez = true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rez;
    }
    public Optional<User> login(String username,String parola){
        UserDao userDao = new UserDao(con);
        try{
            Optional<User> optionalUser = userDao.findUser(username);
            if(optionalUser.isPresent()){
                if(optionalUser.get().getParola().equals(parola)){
                    return optionalUser;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public void adaugaProdus(Produs p){
        ProdusDao produsDao = new ProdusDao(con);
        //am nevoie de o instanta de produsDao ca sa am acces la operatii cu baza de date
        try{
            produsDao.adaugaProdus(p);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public List<Produs> getAllProducts(){
        ProdusDao produsDao = new ProdusDao(con); 
        try{
            return produsDao.getAllProducts();
        }catch(Exception e){
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public void stergeProdus(int id){
        ProdusDao produsDao = new ProdusDao(con);
        try{
            produsDao.stergeProdus(id);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    
}
