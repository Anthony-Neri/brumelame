package fr.neri.brumelame.domain.character;

import fr.neri.brumelame.domain.equipment.OffensiveEquipment;

public class Warrior extends Hero {


    public Warrior (String name, int health, int attack){

        super("WARRIOR", name, health, attack, null);
    }
    public Warrior(String name, int health, int attack , OffensiveEquipment equipment) {

        super("WARRIOR", name, health, attack, equipment);
    }

}