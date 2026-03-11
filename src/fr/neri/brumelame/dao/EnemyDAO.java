package fr.neri.brumelame.dao;



import fr.neri.brumelame.domain.enemy.Enemy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnemyDAO extends DAO<Enemy> {

    public boolean create(Enemy enemy) {
        String sql = "INSERT INTO enemies (name, health, attack) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setString(1, enemy.getName());
            ps.setInt(2, enemy.getHealth());
            ps.setInt(3, enemy.getAttack());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(Enemy enemy) {
        String sql = "DELETE FROM enemies WHERE id = ?";
        
        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, enemy.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(Enemy enemy) {
        String sql = "UPDATE enemies SET name = ?, health = ?, attack = ? WHERE id = ?";
        
        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setString(1, enemy.getName());
            ps.setInt(2, enemy.getHealth());
            ps.setInt(3, enemy.getAttack());
            ps.setInt(4, enemy.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public Enemy find(int id) {
        String sql = "SELECT * FROM enemies WHERE id = ?";

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                int enemyId = rs.getInt("id");
                String name = rs.getString("name");
                int health = rs.getInt("health");
                int attack = rs.getInt("attack");

                Enemy enemy = new Enemy(name, health, attack);
                enemy.setId(enemyId);

                return enemy;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}