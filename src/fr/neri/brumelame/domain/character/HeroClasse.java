package fr.neri.brumelame.domain.character;

public class HeroClasse {
    private String name;
    private int attack;
    private int health;

    public HeroClasse(String name, int attack , int health ) {
        this.health = health;
        this.attack = attack;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
