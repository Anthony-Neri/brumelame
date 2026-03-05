package fr.neri.brumelame.game;

import fr.neri.brumelame.game.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int id;
    private List<Cell> cells;
    private  int size;
    private String name;

    public Board(String name, int size ) {
        this.cells = new ArrayList<Cell>();
        this.size = size;
        this.name = name;
        initializeCells();
    }
    
    public Board(String name) { // Se cond constructeur avec taille par défaut
        this(name, 64); // taille par défaut
    }

    private void initializeCells() {
//        cells.add(new EmptyCell());
//        cells.add(new EnemyCell());
//        cells.add(new EquipmentCell());
//        cells.add(new Bonus());
    }

    public Cell getCell(int position) {
        return cells.get(position);
    }

    public int getFinalCell() {
        return size;
    }

    public int getSize() {
        return size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}