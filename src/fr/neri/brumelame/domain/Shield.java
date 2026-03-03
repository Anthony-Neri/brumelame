package fr.neri.brumelame.domain;

public class Shield extends DefensiveEquipement{

    public Shield(String name, int bonus) {
        String type = "shield";
        super(name, type, bonus);
    }
}
