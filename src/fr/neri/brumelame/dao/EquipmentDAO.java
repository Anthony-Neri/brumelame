package fr.neri.brumelame.dao;

import fr.neri.brumelame.domain.equipment.ConsumableEquipment;
import fr.neri.brumelame.domain.equipment.Equipment;
import fr.neri.brumelame.domain.equipment.OffensiveEquipment;
import fr.neri.brumelame.domain.equipment.DefensiveEquipment;
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
        String sql = "SELECT * FROM equipments WHERE id = ?";

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String type = rs.getString("type");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String description = rs.getString("description");
                int bonus = rs.getInt("bonus");

                Equipment equipment;
                switch(type){
                    case "ATTACK" ->   equipment = new OffensiveEquipment(type, name, category, description, bonus);
                    case "DEFENSE" ->   equipment = new DefensiveEquipment(type, name, category, description, bonus);
                    default -> {
                        return null;
                    }
                }

                equipment.setId(id);

                return equipment;
            }
        } catch (SQLException e) {
            return null;
        }
    }
    public Equipment findByHeroClasseAndNivAndType(String heroClasse, int niv, String type ) {
        String sql = "SELECT * FROM equipments e " +
                "JOIN heroclasses_categories hc on e.category = hc.category " +
                "WHERE niv = ? and hc.heroclasse = ? and type = ? LIMIT 1 ";

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setString(2, heroClasse);
            ps.setInt(1, niv);
            ps.setString(3, type.toUpperCase());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String name = rs.getString("name");
                String category = rs.getString("category");
                String description = rs.getString("description");
                int bonus = rs.getInt("bonus");

                Equipment equipment;
                switch(type){
                    case "ATTACK" ->   equipment = new OffensiveEquipment(type, name, category, description, bonus);
                    case "DEFENSE" ->   equipment = new DefensiveEquipment(type, name, category, description, bonus);
                    case "CONSUMABLE" -> equipment = new ConsumableEquipment(type, name, category, description, bonus);
                    default -> {
                        return null;
                    }
                }

                equipment.setId(rs.getInt("id"));

                return equipment;
            }
        } catch (SQLException e) {
            return null;
        }
    }

}
