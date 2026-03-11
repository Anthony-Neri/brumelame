package fr.neri.brumelame.app;

import fr.neri.brumelame.dao.HeroDAO;
import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.game.Game;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.home();
    }
}