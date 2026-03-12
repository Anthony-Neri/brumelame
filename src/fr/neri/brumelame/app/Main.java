package fr.neri.brumelame.app;

import fr.neri.brumelame.dao.BoardDAO;
import fr.neri.brumelame.dao.CellDAO;
import fr.neri.brumelame.dao.EnemyDAO;
import fr.neri.brumelame.dao.EquipmentDAO;
import fr.neri.brumelame.dao.HeroClassesDAO;
import fr.neri.brumelame.dao.HeroDAO;
import fr.neri.brumelame.exception.BoardInitializationException;
import fr.neri.brumelame.exception.HeroInitializationException;
import fr.neri.brumelame.game.Board;
import fr.neri.brumelame.game.Dice;
import fr.neri.brumelame.game.Game;
import fr.neri.brumelame.service.HeroCreationService;
import fr.neri.brumelame.ui.Menu;

public class Main {

    public static void main(String[] args) {
        try {
            EquipmentDAO equipmentDAO = new EquipmentDAO();

            HeroCreationService heroCreationService = new HeroCreationService(
                    equipmentDAO,
                    new HeroClassesDAO(),
                    new HeroDAO()
            );

            Board board = new Board(
                    new EnemyDAO(),
                    new CellDAO(),
                    equipmentDAO,
                    new BoardDAO()
            );

            Game game = new Game(
                    new Menu(),
                    new Dice(1, 6),
                    board,
                    heroCreationService
            );

            game.home();
        } catch (BoardInitializationException | HeroInitializationException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
