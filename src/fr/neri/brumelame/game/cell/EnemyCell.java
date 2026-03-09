package fr.neri.brumelame.game.cell;

import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.domain.enemy.Enemy;

public class EnemyCell extends Cell {
    private Enemy enemy;

    @Override
    public String toString() {
        return super.toString() + "EnemyCell{" +
                "enemy=" + enemy +
                '}';
    }

    public EnemyCell(int number, int boardId, Enemy enemy) {
        this.enemy = enemy;
        super(number, boardId);
    }

    @Override
    public Enemy getEnemy() {
        return enemy;
    }


    public StringBuilder interact(Hero hero) {
        StringBuilder fight = new StringBuilder("Vous êtes attaquer par : " + enemy.getName()+ " ! \n");

        while (this.enemy.getHealth() > 0 && hero.getHealth() > 0) {
            fight.append("Vous infligez ").append(hero.getDamage()).append(" au ").append(enemy.getName()+ " ! \n");
            enemy.receivedDamage(hero.getDamage());

            if (enemy.getHealth() > 0) {
                fight.append("Le " + enemy.getName() + " vous inflige " + enemy.getAttack() + " ! \n");
                hero.receivedDamage(enemy.getAttack());
            }

        }
        if (hero.getHealth() <= 0) {
            fight.append("Vous êtes mort ! \n");
        }else {
            fight.append("Vous avez vaincu le ").append(enemy.getName()).append(" ! \n");
        }

        return fight;
    }

}
