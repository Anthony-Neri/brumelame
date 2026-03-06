package fr.neri.brumelame.dao;

import fr.neri.brumelame.domain.character.HeroClasse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HeroClassesDAO extends DAO<HeroClasse> {

    public HeroClasse findByName(String name){
        String sql = "Select * from heroclasses WHERE name = ?";

        try(PreparedStatement ps = conn.getConnection().prepareStatement(sql)){
            ps.setString(1,name.toUpperCase());

            try (ResultSet rs = ps.executeQuery()){
                if (!rs.next()){
                    return null;
                }
                int attack = rs.getInt("attack");
                int health = rs.getInt("health");

                return  new HeroClasse(name,attack,health);

            }


        }catch (SQLException e){
            return null;
        }
    }
}
