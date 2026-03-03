package fr.neri.brumelame.domain.character;

import fr.neri.brumelame.domain.equipment.OffensiveEquipment;
import fr.neri.brumelame.domain.equipment.Weapon;

public class Warrior extends Character{


    public Warrior(String name) {

        int health = 10;
        int attack = 5;
        OffensiveEquipment epee = new Weapon("Epee en bois", "épée", 0);
        String type = "Guerrier";
        super(type,name, health, attack, epee);
    }

}
