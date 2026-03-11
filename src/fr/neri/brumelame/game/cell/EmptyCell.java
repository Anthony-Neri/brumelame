package fr.neri.brumelame.game.cell;

import fr.neri.brumelame.domain.character.Hero;

public class EmptyCell extends Cell {

    public EmptyCell(int number, int boardId) {
        super(number, boardId);
    }

    @Override
    public String toString() {
        return "C'est une case vide";
    }

    @Override
    public StringBuilder interact(Hero hero) {

        StringBuilder message = new StringBuilder();

        if (this.number == 0){
            message.append("Vous êtes sur la case de départ.");
        }else {
            message.append("Vous êtes arrivez sur une case vide, vous vous reposez ! ");
            if (hero.getHealth() < hero.getMaxHealth()) {
                hero.getHeal(1);
                message.append("Et recouvrez un point de vie !");
            }
        }
        return message ;
    }
}
