package fr.neri.brumelame.dao;

import fr.neri.brumelame.domain.equipment.Equipment;
import fr.neri.brumelame.game.cell.Cell;
import fr.neri.brumelame.game.cell.EmptyCell;
import fr.neri.brumelame.game.cell.EnemyCell;
import fr.neri.brumelame.game.cell.EquipmentCell;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class CellDAO extends DAO<Cell> {

    public int create(Cell cell) {
        String sql = """
            INSERT INTO cells (cell_number, board_id, equipment_id, enemy_id)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, cell.getNumber());
            ps.setInt(2, cell.getBoardId());

            Integer equipmentId = cell.getEquipment() != null ? cell.getEquipment().getId() : null;
            Integer enemyId = cell.getEnemy() != null ? cell.getEnemy().getId() : null;

            if (equipmentId != null) {
                ps.setInt(3, equipmentId);
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            if (enemyId != null) {
                ps.setInt(4, enemyId);
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

            return -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    public boolean delete(Cell cell) {
        String sql = "DELETE FROM cells WHERE id = ?";

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, cell.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(Cell cell) {
        String sql = """
            UPDATE cells
            SET cell_number = ?, board_id = ?, equipment_id = ?, enemy_id = ?
            WHERE id = ?
        """;

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, cell.getNumber());
            ps.setInt(2, cell.getBoardId());

            Integer equipmentId = cell.getEquipment() != null ? cell.getEquipment().getId() : null;
            Integer enemyId = cell.getEnemy() != null ? cell.getEnemy().getId() : null;

            if (equipmentId != null) {
                ps.setInt(3, equipmentId);
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            if (enemyId != null) {
                ps.setInt(4, enemyId);
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setInt(5, cell.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public Cell find(int id) {
        String sql = "SELECT * FROM cells WHERE id = ?";

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                int cellId = rs.getInt("id");
                int number = rs.getInt("cell_number");
                int boardId = rs.getInt("board_id");

                int equipmentId = rs.getInt("equipment_id");
                boolean equipmentIsNull = rs.wasNull();

                int enemyId = rs.getInt("enemy_id");
                boolean enemyIsNull = rs.wasNull();

                Cell cell;

                if (!equipmentIsNull) {
                    EquipmentDAO equipmentDAO = new EquipmentDAO();
                    Equipment equipment = equipmentDAO.find(equipmentId);
                    cell = new EquipmentCell(number, boardId, equipment);
                } else if (!enemyIsNull) {
                    EnemyDAO enemyDAO = new EnemyDAO();
                    cell = new EnemyCell(number, boardId, enemyDAO.find(enemyId));
                } else {
                    cell = new EmptyCell(number, boardId);
                }

                cell.setId(cellId);
                return cell;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}