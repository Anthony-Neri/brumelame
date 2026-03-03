package fr.neri.brumelame.domain.equipment;

public abstract class DefensiveEquipment {
    private String type;
    private String name;
    private int bonus;

    public DefensiveEquipment(String name, String type, int defense) {
        this.name = name;
        this.type = type;
        this.bonus = defense;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getBonus() {
        return bonus;
    }

    
    public String toString() {
        return this.name
                + " | type : " + this.type
                + " | défense " + this.bonus;
    }
}
