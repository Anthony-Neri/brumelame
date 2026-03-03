package fr.neri.brumelame.game;

import fr.neri.brumelame.game.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Cell> cells;
    private final int FINAL_CELL = 4;

    public Board() {
        this.cells = new ArrayList<Cell>();
        initializeCells();
    }

    private void initializeCells() {
        cells.add(new EmptyCell());
        cells.add(new Ennemy());
        cells.add(new Weapon());
        cells.add(new Bonus());
    }

    public Cell getCell(int position) {
        return cells.get(position);
    }

    public int getFinalCell() {
        return FINAL_CELL;
    }
}