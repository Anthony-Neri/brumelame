package fr.neri.brumelame.domain.equipment;

public class Potion extends DefensiveEquipment {

    public Potion(String name, int bonus) {
        String type = "potion";
        super(name, type, bonus);
    }
}
