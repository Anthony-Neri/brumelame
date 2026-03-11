package fr.neri.brumelame.dao;

import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.domain.character.Warrior;
import fr.neri.brumelame.domain.character.Wizard;
import fr.neri.brumelame.domain.equipment.DefensiveEquipment;
import fr.neri.brumelame.domain.equipment.OffensiveEquipment;
import fr.neri.brumelame.game.Board;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class HeroDAO extends DAO<Hero> {

    public int create(Hero hero) {
        String sql = """

            INSERT INTO heroes (name, type, health, attack, id_off_equip, id_def_equip, id_cell, id_board)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, hero.getName());
            ps.setString(2, hero.getClass().getSimpleName().toUpperCase());
            ps.setInt(3, hero.getHealth());
            ps.setInt(4, hero.getAttack());

            Integer offEquipId = (hero.getOffEquip() != null) ? hero.getOffEquip().getId() : null;
            Integer defEquipId = (hero.getDefEquip() != null) ? hero.getDefEquip().getId() : null;

            if (offEquipId != null) ps.setInt(5, offEquipId);
            else ps.setNull(5, Types.INTEGER); // permet de gérer les valeurs nulles pour un entier optionnel.

            if (defEquipId != null) ps.setInt(6, defEquipId);
            else ps.setNull(6, Types.INTEGER);

            // Adapter selon ton modèle (optionnel aussi si besoin)
            ps.setInt(7, hero.getCellId());
            ps.setInt(8, hero.getBoardId());

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {

                        return rs.getInt(1);
                    }
                }
            }
            return -1;
        } catch (SQLException e) {
            System.out.print(e);
            return -1;
        }
    }

    public boolean delete(Hero hero) {
        String sql = "DELETE FROM heroes WHERE id = ?";
        
        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, hero.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(Hero hero) {
        String sql = """
            UPDATE heroes 
            SET name = ?, type = ?, health = ?, attack = ?, 
                id_off_equip = ?, id_def_equip = ?, id_cell = ?, id_board = ?
            WHERE id = ?
        """;
        
        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setString(1, hero.getName());
            ps.setString(2, hero.getClass().getName());
            ps.setInt(3, hero.getHealth());
            ps.setInt(4, hero.getAttack());

            Integer offEquipId = (hero.getOffEquip() != null) ? hero.getOffEquip().getId() : null;
            Integer defEquipId = (hero.getDefEquip() != null) ? hero.getDefEquip().getId() : null;

            if (offEquipId != null) ps.setInt(5, offEquipId);
            else ps.setNull(5, Types.INTEGER);

            if (defEquipId != null) ps.setInt(6, defEquipId);
            else ps.setNull(6, Types.INTEGER);

            ps.setInt(7, hero.getCellId());
            ps.setInt(8, hero.getBoardId());
            ps.setInt(9, hero.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public Hero find(int id) {
        String sql = "SELECT * FROM heroes WHERE id = ?";

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String type = rs.getString("type").toUpperCase();
                String name = rs.getString("name");
                int health = rs.getInt("health");
                int attack = rs.getInt("attack");

                Hero hero;
                switch (type) {
                    case "WARRIOR" -> hero = new Warrior(name, health, attack);
                    case "WIZARD" -> hero = new Wizard(name, health, attack);
                    default -> {
                        return null;
                    }
                }
                
                hero.setId(id);
                hero.setCellId(rs.getInt("id_cell"));
                hero.setBoardId(rs.getInt("id_board"));

                // Charger les équipements
                int offEquipId = rs.getInt("id_off_equip");
                if (!rs.wasNull()) {
                    EquipmentDAO equipmentDAO = new EquipmentDAO();
                    hero.setOffEquip((OffensiveEquipment) equipmentDAO.find(offEquipId));
                }

                int defEquipId = rs.getInt("id_def_equip");
                if (!rs.wasNull()) {
                    EquipmentDAO equipmentDAO = new EquipmentDAO();
                    hero.setDefEquip((DefensiveEquipment)  equipmentDAO.find(defEquipId));
                }
                
                return hero;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
