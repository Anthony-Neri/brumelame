package fr.neri.brumelame.domain;

public class Warrior extends Character{


    public Warrior(String name) {

        int health = 10;
        int attack = 5;
        OffensiveEquipement epee = new Weapon("Epee en bois", "épée", 0);
        String type = "Guerrier";
        super(type,name, health, attack, epee);
    }

}
