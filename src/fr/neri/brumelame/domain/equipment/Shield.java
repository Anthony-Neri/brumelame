package fr.neri.brumelame.domain.equipment;

public class Shield extends DefensiveEquipment {

    public Shield(String name, int bonus) {
        String type = "shield";
        super(name, type, bonus);
    }
}
