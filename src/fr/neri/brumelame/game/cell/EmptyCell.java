package fr.neri.brumelame.game.cell;

public class EmptyCell extends Cell {

    public EmptyCell(int number, int boardId) {
        super(number, boardId);
    }

    @Override
    public String toString() {
        return "C'est une case vide";
    }
}
