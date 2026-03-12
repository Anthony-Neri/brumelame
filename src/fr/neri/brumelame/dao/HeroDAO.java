package fr.neri.brumelame.dao;

import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.domain.character.Warrior;
import fr.neri.brumelame.domain.character.Wizard;
import fr.neri.brumelame.domain.equipment.DefensiveEquipment;
import fr.neri.brumelame.domain.equipment.OffensiveEquipment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class HeroDAO extends DAO<Hero> {

    public int create(Hero hero) {
        String sql = """
            INSERT INTO heroes (
                name,
                hero_class_id,
                health,
                attack,
                offensive_equipment_id,
                defensive_equipment_id,
                cell_id
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        int heroClassId = getHeroClassId(hero);
        if (heroClassId == -1) {
            return -1;
        }

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, hero.getName());
            ps.setInt(2, heroClassId);
            ps.setInt(3, hero.getHealth());
            ps.setInt(4, hero.getAttack());

            Integer offEquipId = hero.getOffEquip() != null ? hero.getOffEquip().getId() : null;
            Integer defEquipId = hero.getDefEquip() != null ? hero.getDefEquip().getId() : null;

            if (offEquipId != null) {
                ps.setInt(5, offEquipId);
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            if (defEquipId != null) {
                ps.setInt(6, defEquipId);
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            ps.setInt(7, hero.getCellId());

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

            return -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
            SET name = ?,
                hero_class_id = ?,
                health = ?,
                attack = ?,
                offensive_equipment_id = ?,
                defensive_equipment_id = ?,
                cell_id = ?
            WHERE id = ?
        """;

        int heroClassId = getHeroClassId(hero);
        if (heroClassId == -1) {
            return false;
        }

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setString(1, hero.getName());
            ps.setInt(2, heroClassId);
            ps.setInt(3, hero.getHealth());
            ps.setInt(4, hero.getAttack());

            Integer offEquipId = hero.getOffEquip() != null ? hero.getOffEquip().getId() : null;
            Integer defEquipId = hero.getDefEquip() != null ? hero.getDefEquip().getId() : null;

            if (offEquipId != null) {
                ps.setInt(5, offEquipId);
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            if (defEquipId != null) {
                ps.setInt(6, defEquipId);
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            ps.setInt(7, hero.getCellId());
            ps.setInt(8, hero.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public Hero find(int id) {
        String sql = """
            SELECT h.*, hc.name AS hero_class_name
            FROM heroes h
            JOIN hero_classes hc ON h.hero_class_id = hc.id
            WHERE h.id = ?
        """;

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String heroClassName = rs.getString("hero_class_name");
                String name = rs.getString("name");
                int health = rs.getInt("health");
                int attack = rs.getInt("attack");

                Hero hero;
                switch (heroClassName) {
                    case "WARRIOR" -> hero = new Warrior(name, health, attack);
                    case "WIZARD" -> hero = new Wizard(name, health, attack);
                    default -> {
                        return null;
                    }
                }

                hero.setId(rs.getInt("id"));

                int cellId = rs.getInt("cell_id");
                if (!rs.wasNull()) {
                    hero.setCellId(cellId);
                }

                EquipmentDAO equipmentDAO = new EquipmentDAO();

                int offEquipId = rs.getInt("offensive_equipment_id");
                if (!rs.wasNull()) {
                    hero.setOffEquip((OffensiveEquipment) equipmentDAO.find(offEquipId));
                }

                int defEquipId = rs.getInt("defensive_equipment_id");
                if (!rs.wasNull()) {
                    hero.setDefEquip((DefensiveEquipment) equipmentDAO.find(defEquipId));
                }

                return hero;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    private int getHeroClassId(Hero hero) {
        String heroClassName = hero.getClass().getSimpleName().toUpperCase();
        String sql = "SELECT id FROM hero_classes WHERE name = ?";

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setString(1, heroClassName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            return -1;
        }

        return -1;
    }
}
