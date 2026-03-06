package fr.neri.brumelame.game;

import fr.neri.brumelame.dao.BoardDAO;
import fr.neri.brumelame.dao.CellDAO;
import fr.neri.brumelame.dao.EnemyDAO;
import fr.neri.brumelame.domain.enemy.Enemy;
import fr.neri.brumelame.game.cell.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private int id;
    private List<Cell> cells;
    private  int size;
    private String name;
    private EnemyDAO enemyDAO;
    private CellDAO cellDAO;

    public Board(String name, int size ) {
        this.cells = new ArrayList<Cell>();
        this.size = size;
        this.name = name;
        this.enemyDAO = new EnemyDAO();
        this.cellDAO = new CellDAO();
    }

    public Board(String name) { // Second constructeur avec taille par défaut
        this(name, 64); // taille par défaut
    }

    public Board() { // Constructeur avec date et heure du jour par défaut
        this(LocalDate.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy")), 64);
    }

    public void initialize(){
        BoardDAO boardDAO = new BoardDAO();
        int id = boardDAO.create(this);
        if (id >0) this.setId(id);

        EmptyCell depart = new EmptyCell(0, this.id);
        depart.setId(cellDAO.create(depart));
        cells.add(depart);
        for (int i = 1; i <= size; i++) {

            Enemy gobelin = enemyDAO.find(2);
            EnemyCell cell = new EnemyCell(i, this.id , gobelin);
            cell.setId(cellDAO.create(cell));
            cells.add(cell);

        }




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

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", cells=" + cells +
                ", size=" + size +
                ", name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }
}