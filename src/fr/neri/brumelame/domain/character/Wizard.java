package fr.neri.brumelame.domain.character;

import fr.neri.brumelame.domain.equipment.OffensiveEquipment;
import fr.neri.brumelame.domain.equipment.Spell;

public class Wizard extends Character{

    public Wizard(String name) {
        int health = 6;
        int attack = 8;
        OffensiveEquipment baton = new Spell("Étincelle", "Électrique", 0);
        String type = "Sorcier";
        super(type, name, health, attack, baton);
    }


}
