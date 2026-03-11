package fr.neri.brumelame.game;

import fr.neri.brumelame.dao.BoardDAO;
import fr.neri.brumelame.dao.CellDAO;
import fr.neri.brumelame.dao.EnemyDAO;
import fr.neri.brumelame.dao.EquipmentDAO;

import fr.neri.brumelame.game.cell.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private static final int BOARD_SIZE = 64;

    private int id;
    private List<Cell> cells;
    private String name;
    private EnemyDAO enemyDAO;
    private CellDAO cellDAO;
    private EquipmentDAO equipmentDAO;
    private BoardDAO boardDAO;

    // Positions :
    List<Integer> DRAGON_CELLS = new ArrayList<>(Arrays.asList(45, 52, 56, 62,63,11));
    List<Integer> WIZARD_CELLS = new ArrayList<>(Arrays.asList(10, 20, 25, 32, 35, 36, 37, 40, 44, 47,57,58,60,8));
    List<Integer> GOBELIN_CELLS = new ArrayList<>(Arrays.asList(3, 6, 9, 12, 15, 18, 21, 24, 27, 30,49));

    List<Integer> OFF_1_CELLS = new ArrayList<>(Arrays.asList(2,  38, 1, 4, 23));
    List<Integer> OFF_2_CELLS = new ArrayList<>(Arrays.asList(19, 26, 42, 53, 48));
    List<Integer> DEF_1_CELLS = new ArrayList<>(Arrays.asList(5, 22, 17,61));
    List<Integer> CON_1_CELLS = new ArrayList<>(Arrays.asList(7, 13, 31, 33, 39, 43));
    List<Integer> CON_2_CELLS = new ArrayList<>(Arrays.asList(28, 41));


    public Board(String name) {
        this.cells = new ArrayList<Cell>(64);
        this.name = name;
        this.enemyDAO = new EnemyDAO();
        this.cellDAO = new CellDAO();
        this.equipmentDAO = new EquipmentDAO();
        this.boardDAO = new BoardDAO();
    }

    public Board() { // Constructeur avec date et heure du jour par défaut
        this(LocalDate.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy")));
    }

    public void initialize() {

        try {
            int id = boardDAO.create(this);
            if (id > 0) this.setId(id);
        } catch (Exception $e) {}

        for (int i = 0; i < BOARD_SIZE; i++) {
            EmptyCell emptyCell = new EmptyCell(i, this.id);
            try {
                emptyCell.setId(cellDAO.create(emptyCell));
            } catch (Exception $e) {}
            cells.add(emptyCell);
        }

    }

    public void initializeCells(String heroClasse) {

        for (int numberCell : DRAGON_CELLS) {
            addEnemyCell(3, numberCell);
        }
        for (int numberCell : WIZARD_CELLS) {
            addEnemyCell(1, numberCell);
        }
        for (int numberCell : GOBELIN_CELLS) {
            addEnemyCell(1, numberCell);
        }

        for (int numberCell : OFF_1_CELLS) {
            addEquipmentCell(1, heroClasse, "ATTACK", numberCell);

        }
        for (int numberCell : OFF_2_CELLS) {
            addEquipmentCell(2, heroClasse, "ATTACK", numberCell);

        }
        for (int numberCell : DEF_1_CELLS) {
            addEquipmentCell(1, heroClasse, "DEFENSE", numberCell);

        }

        for (int numberCell : CON_1_CELLS) {
            addEquipmentCell(1, heroClasse, "CONSUMABLE", numberCell);

        }
        for (int numberCell : CON_2_CELLS) {
            addEquipmentCell(1, heroClasse, "CONSUMABLE", numberCell);

        }


    }

    private void addEnemyCell(int idEnemy, int position) {

        EnemyCell cell = new EnemyCell(position, this.id, enemyDAO.find(idEnemy));
        cell.setId(cellDAO.create(cell));
        cells.set(position, cell);
    }

    private void addEquipmentCell(int niv, String heroClasse, String type, int position) {
        EquipmentCell cell = new EquipmentCell(position, this.id,
                equipmentDAO.findByHeroClasseAndNivAndType(heroClasse, niv, type));
        cell.setId(cellDAO.create(cell));
        cells.set(position, cell);
    }

    public Cell getCell(int position) {
        return cells.get(position);
    }

    public int getNumberFinalCell() {
        return cells.size() - 1;
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