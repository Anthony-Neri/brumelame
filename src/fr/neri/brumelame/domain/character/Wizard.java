package fr.neri.brumelame.domain.character;

import fr.neri.brumelame.domain.equipment.OffensiveEquipment;
import fr.neri.brumelame.domain.equipment.Spell;

public class Wizard extends Hero {

    public Wizard(String name, int health, int attack) {
        OffensiveEquipment baton = new Spell("Étincelle", "Électrique", 0);
        String type = "Sorcier";
        super(type, name, health, attack, baton);
    }


}
