package fr.neri.brumelame.dao;

import fr.neri.brumelame.domain.character.HeroClasse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HeroClassesDAO extends DAO<HeroClasse> {

    public HeroClasse findByName(String name) {
        String sql = "SELECT * FROM hero_classes WHERE name = ?";

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                int baseAttack = rs.getInt("base_attack");
                int baseHealth = rs.getInt("base_health");

                return new HeroClasse(name, baseAttack, baseHealth);
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
