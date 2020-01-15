/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Produs;

/**
 *
 * @author student
 */
public class ProdusDao {
    private Connection con;
    public ProdusDao(Connection con){
        this.con = con;
    }
    public void adaugaProdus(Produs p) throws SQLException{
        String sql = "INSERT INTO produse VALUES(NULL,?,?)";
        try(PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1,p.getNume());
            stmt.setDouble(2,p.getPret());
            stmt.executeUpdate();
        }
    }
    public List<Produs> getAllProducts() throws SQLException{
        String sql = "SELECT * FROM produse";
        List<Produs> produse;
        try(PreparedStatement stmt = con.prepareStatement(sql)){
            produse = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                double pret = rs.getDouble("pret");
                produse.add(new Produs(id,nume,pret));
            }
            
        }
        return produse;
    }
    public void stergeProdus(int id) throws SQLException{
        String sql = "DELETE FROM produse WHERE id=?";
        try(PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setInt(1,id);
            stmt.executeUpdate();
        }
    }
    
}
