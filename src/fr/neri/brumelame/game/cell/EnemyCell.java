package fr.neri.brumelame.game.cell;

import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.domain.enemy.Enemy;
import fr.neri.brumelame.ui.Menu;

public class EnemyCell extends Cell {
    private Enemy enemy;

    @Override
    public String toString() {
        return super.toString() + "EnemyCell{" +
                "enemy=" + enemy +
                '}';
    }

    public EnemyCell(int number, int boardId, Enemy enemy) {
        super(number, boardId);
        this.enemy = enemy;
    }

    @Override
    public Enemy getEnemy() {
        return enemy;
    }


    public void interact(Hero hero, Menu menu) {

        int round = 1;
        menu.printStartFight(getEnemy().getName(), hero.getName());




        while (enemy.getHealth() > 0 && hero.getHealth() > 0) {

            int heroDamage = hero.getDamage();
            enemy.receivedDamage(heroDamage);

           menu.printTurnFight(heroDamage, enemy.getHealth(),round, hero.getName(), getEnemy().getName());

            if (enemy.getHealth() > 0) {
                int enemyDamage = enemy.getAttack();
                int defenseBonus = hero.getDefEquip() != null ? hero.getDefEquip().getBonus() : 0;
                int finalDamage = Math.max(enemyDamage - defenseBonus, 0); // permet de ne pas avoir de dégâts négatifs.

                hero.receivedDamage(enemyDamage);

                menu.printTurnFight(finalDamage,hero.getHealth(),round, getEnemy().getName(), hero.getName());

            }

            round++;
        }

        String winnerName = "";
        String looserName = "";

        if (hero.getHealth() <= 0) {
            winnerName = getEnemy().getName();
            looserName = hero.getName();

        } else {
            winnerName = hero.getName();
            looserName = getEnemy().getName();
        }

        menu.printEndFight(winnerName,looserName);


    }

}
