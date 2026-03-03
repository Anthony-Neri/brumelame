package fr.neri.brumelame.domain;

public abstract class OffensiveEquipement {
    private String type;
    private String name;
    private int bonusAttack;

    public OffensiveEquipement(String name, String type, int attack) {
        this.name = name;
        this.type = type;
        this.bonusAttack = attack;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getBonusAttack() {
        return bonusAttack;
    }


    public String toString() {
        return this.name
                + " | type : " + this.type
                + " | Bonus d'attaque : " + this.bonusAttack;
    }
}
