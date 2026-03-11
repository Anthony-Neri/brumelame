package fr.neri.brumelame.domain.character;

import fr.neri.brumelame.domain.equipment.OffensiveEquipment;

public class Wizard extends Hero {

    public Wizard () {
    }

    public Wizard (String name, int health, int attack){
        super(name, health, attack, null);
    }

    public Wizard(String name, int health, int attack , OffensiveEquipment equipment) {
        super(name, health, attack, equipment);
    }


}
