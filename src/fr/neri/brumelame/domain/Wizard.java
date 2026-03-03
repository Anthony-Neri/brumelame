package fr.neri.brumelame.domain;

public class Wizard extends Character{

    public Wizard(String name) {
        int health = 6;
        int attack = 8;
        OffensiveEquipement baton = new Spell("Étincelle", "Électrique", 0);
        String type = "Sorcier";
        super(type, name, health, attack, baton);
    }


}
