package fr.neri.brumelame.domain.character;

import fr.neri.brumelame.domain.equipment.OffensiveEquipment;

public class Elf extends Hero {

    public Elf () {
    }

    public Elf (String name, int health, int attack){
        super(name, health, attack, null);
    }
    
    public Elf(String name, int health, int attack , OffensiveEquipment equipment) {
        super(name, health, attack, equipment);
    }

}
