package fr.neri.brumelame.app;

import fr.neri.brumelame.dao.EquipmentDAO;
import fr.neri.brumelame.dao.HeroClassesDAO;
import fr.neri.brumelame.dao.HeroDAO;

import fr.neri.brumelame.game.Board;
import fr.neri.brumelame.game.Dice;
import fr.neri.brumelame.game.Game;
import fr.neri.brumelame.ui.Menu;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(
                new Menu(),
                new Dice(1, 6),
                new Board(),
                new EquipmentDAO(),
                new HeroClassesDAO(),
                new HeroDAO()
        );
        game.home();
    }


}
