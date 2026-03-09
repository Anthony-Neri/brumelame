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
        return null;
    }
}
