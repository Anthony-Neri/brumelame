package fr.neri.brumelame.game.cell;

import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.ui.Menu;

public class EmptyCell extends Cell {

    public EmptyCell(int number, int boardId) {
        super(number, boardId);
    }

    @Override
    public String toString() {
        return "C'est une case vide";
    }

    @Override
    public void interact(Hero hero, Menu menu) {

        String message = "";

        if (this.number == 0){
            message += "Vous êtes sur la case de départ.";
        }else {
            message += "Vous êtes arrivez sur une case vide, vous vous reposez ! ";
            if (hero.getHealth() < hero.getMaxHealth()) {
                hero.getHeal(1);
                message += "Et recouvrez un point de vie !";
            }
        }

        menu.printMessage(message);

    }
}
