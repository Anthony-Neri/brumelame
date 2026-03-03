package fr.neri.brumelame.domain;

public class Potion extends DefensiveEquipement{

    public Potion(String name, int bonus) {
        String type = "potion";
        super(name, type, bonus);
    }
}
