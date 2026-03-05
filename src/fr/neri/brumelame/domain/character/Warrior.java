package fr.neri.brumelame.domain.character;

import fr.neri.brumelame.domain.equipment.OffensiveEquipment;
import fr.neri.brumelame.domain.equipment.Weapon;

public class Warrior extends Hero {


    public Warrior(String name,int  health, int attack ) {

        OffensiveEquipment epee = new Weapon("Epee en bois", "épée", 0);
        String type = "WARRIOR";
        super(type,name, health, attack, epee);
    }

}
