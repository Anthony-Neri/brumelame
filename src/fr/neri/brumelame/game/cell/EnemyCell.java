package fr.neri.brumelame.game.cell;

import fr.neri.brumelame.domain.enemy.Enemy;

public class EnemyCell extends Cell {
    private Enemy enemy;

    public EnemyCell(int number, int boardId, Enemy enemy) {
        this.enemy = enemy;
        super(number,boardId);
    }

    @Override
    public Enemy getEnemy() {
        return enemy;
    }

    @Override
    public String toString() {
        return "C'est un ennemi";
    }
}
