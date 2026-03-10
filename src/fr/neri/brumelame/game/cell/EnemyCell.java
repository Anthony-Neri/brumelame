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
        StringBuilder fight = new StringBuilder();
        int round = 1;

        fight.append("\n");
        fight.append("╔══════════════════════════════════════╗\n");
        fight.append("║            DÉBUT DU COMBAT           ║\n");
        fight.append("╠══════════════════════════════════════╣\n");
        fight.append(String.format("║ Héro   : %-27s ║%n", hero.getName()));
        fight.append(String.format("║ Ennemi : %-27s ║%n", enemy.getName()));
        fight.append("╚══════════════════════════════════════╝\n\n");

        while (enemy.getHealth() > 0 && hero.getHealth() > 0) {
            fight.append("──────────── Tour ").append(round).append(" ────────────\n");

            int heroDamage = hero.getDamage();
            enemy.receivedDamage(heroDamage);
            fight.append("⚔ ")
                    .append(hero.getName())
                    .append(" inflige ")
                    .append(heroDamage)
                    .append(" dégâts à ")
                    .append(enemy.getName())
                    .append(".\n");

            fight.append("   ")
                    .append(enemy.getName())
                    .append(" : ")
                    .append(Math.max(enemy.getHealth(), 0))
                    .append(" PV restants\n");

            if (enemy.getHealth() > 0) {
                int enemyDamage = enemy.getAttack();
                int defenseBonus = hero.getDefEquip() != null ? hero.getDefEquip().getBonus() : 0;
                int finalDamage = Math.max(enemyDamage - defenseBonus, 0); // permet de ne pas avoir de dégâts négatifs.

                hero.receivedDamage(enemyDamage);

                fight.append("🩸 ")
                        .append(enemy.getName())
                        .append(" inflige ")
                        .append(finalDamage)
                        .append(" dégâts à ")
                        .append(hero.getName())
                        .append(".\n");

                fight.append("   ")
                        .append(hero.getName())
                        .append(" : ")
                        .append(hero.getHealth())
                        .append(" PV restants\n");
            }

            fight.append("\n");
            round++;
        }

        fight.append("══════════════════════════════════════\n");
        if (hero.getHealth() <= 0) {
            fight.append(hero.getName()).append(" a été vaincu.\n");
        } else {
            fight.append(hero.getName()).append(" a vaincu ")
                    .append(enemy.getName()).append(".\n");
        }
        fight.append("══════════════════════════════════════\n");

        return fight;
    }

}
