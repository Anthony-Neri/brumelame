package fr.neri.brumelame.dao;

import fr.neri.brumelame.game.Board;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardDAO extends DAO<Board> {


    public int create(Board board) {
        String sql = "INSERT INTO boards (name) VALUES (?)";

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, board.getName());

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


    public boolean delete(Board obj) {
        return false;
    }



    public Board find(int id) {
        String sql = "Select * from `boards` WHERE id = ?";

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String name = rs.getString("name");

                Board board = new Board(name);
                board.setId(id);


                return board;
            }


        } catch (SQLException e) {
            return null;
        }

    }


    public boolean update(Board board) {
        if (board == null) return false;
        if (board.getId() < 1) return false;

        String sql = "UPDATE boards SET name = ? WHERE id = ?";

        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {


            ps.setString(1, board.getName());
            ps.setInt(2, board.getId());

            int edited = ps.executeUpdate();
            return edited == 1;

        } catch (SQLException e) {
            return false;
        }
    }


}