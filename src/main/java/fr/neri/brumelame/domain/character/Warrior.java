package fr.neri.brumelame.domain.character;

import fr.neri.brumelame.domain.equipment.DefensiveEquipment;
import fr.neri.brumelame.domain.equipment.OffensiveEquipment;

import java.util.List;

public class Warrior extends Hero {

    private static final List<String> VALID_CLASSES_OFF_EQUIP = List.of("WEAPON");
    private static final List<String> VALID_CLASSES_DEF_EQUIP = List.of("SHIELD");


    public Warrior(String name, int health, int attack , OffensiveEquipment equipment) {

        super(name, health, attack, equipment);
    }
    public Warrior(String name, int health, int attack , OffensiveEquipment equipment, DefensiveEquipment defensiveEquipment) {

        super(name, health, attack, equipment, defensiveEquipment);
    }
    public Warrior(){}

    protected List<String> getValidClassOffEquip() {
        return VALID_CLASSES_OFF_EQUIP;
    }

    protected List<String> getValidClassDefEquip() {
        return VALID_CLASSES_DEF_EQUIP;
    }


}