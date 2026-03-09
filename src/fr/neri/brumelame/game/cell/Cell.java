package fr.neri.brumelame.game.cell;

import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.domain.equipment.Equipment;
import fr.neri.brumelame.domain.enemy.Enemy;

public abstract class Cell {

    private int id;
    private int number;
    private int boardId;

    public Cell(int number, int boardId) {
        this.number = number;
        this.boardId = boardId;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "id=" + id +
                ", number=" + number +
                ", boardId=" + boardId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    // Méthodes à surcharger dans les sous-classes
    public Equipment getEquipment() {
        return null;
    }

    public Enemy getEnemy() {
        return null;
    }


    public abstract StringBuilder interact(Hero hero);
}
