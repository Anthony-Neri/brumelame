package fr.neri.brumelame.dao;

import fr.neri.brumelame.domain.equipment.*;
import fr.neri.brumelame.db.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EquipmentDAO extends DAO<Equipment> {

    public EquipmentDAO(DatabaseConnection conn) {
        super(conn);
    }

    public EquipmentDAO() {
        super();
    }

    public Equipment find(int id) {
        String sql = """
            SELECT e.id, e.name, e.equipment_type, ec.code AS category, e.description, e.bonus
            FROM equipments e
            JOIN equipment_categories ec ON e.equipment_category_id = ec.id
            WHERE e.id = ?
        """;

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                return mapEquipment(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL lors de la recherche de l'équipement id=" + id, e);
        }
    }

    public Equipment findByHeroClassAndLevelAndType(String heroClass, int level, String type) {
        String sql = """
            SELECT e.id, e.name, e.equipment_type, ec.code AS category, e.description, e.bonus
            FROM equipments e
            JOIN equipment_categories ec ON e.equipment_category_id = ec.id
            JOIN hero_class_categories hcc ON hcc.equipment_category_id = ec.id
            JOIN hero_classes hc ON hc.id = hcc.hero_class_id
            WHERE e.level = ?
              AND hc.name = ?
              AND e.equipment_type = ?
            LIMIT 1
        """;

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, level);
            ps.setString(2, heroClass.toUpperCase());
            ps.setString(3, type.toUpperCase());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                return mapEquipment(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                "Erreur SQL lors de la recherche d'un équipement pour heroClass=" + heroClass
                    + ", level=" + level
                    + ", type=" + type,
                e
            );
        }
    }

    private Equipment mapEquipment(ResultSet rs) throws SQLException {
        String type = rs.getString("equipment_type");
        String name = rs.getString("name");
        String category = rs.getString("category");
        String description = rs.getString("description");
        int bonus = rs.getInt("bonus");

        Equipment equipment = null;
        switch (type) {
            case "ATTACK" -> {
                switch (category){
                    case "WEAPON" -> equipment = new Weapon(type, name, category, description, bonus);
                    case "SPELL" -> equipment = new Spell(type, name, category, description, bonus);
                }
            }
            case "DEFENSE" ->{
                switch (category){
                    case "WEAPON" -> equipment = new Shield(type, name, category, description, bonus);
                    case "SPELL" -> equipment = new Barrier(type, name, category, description, bonus);
                }
            }
            case "CONSUMABLE" -> {
                switch (category){
                    case "POTION" -> equipment = new Potion(type, name, category, description, bonus);
                }
            }
            default -> {
                return null;
            }
        }

        equipment.setId(rs.getInt("id"));
        return equipment;
    }
}
