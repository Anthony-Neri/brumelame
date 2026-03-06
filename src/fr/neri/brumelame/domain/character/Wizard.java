package fr.neri.brumelame.domain.character;

import fr.neri.brumelame.domain.equipment.OffensiveEquipment;


public class Wizard extends Hero {

    public Wizard (String name, int health, int attack){

        String type = "wizard";
        super(type, name, health, attack, null);
    }
    public Wizard(String name, int health, int attack , OffensiveEquipment equipment) {

        String type = "wizard";
        super(type, name, health, attack, equipment);
    }


}
