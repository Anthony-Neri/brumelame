package fr.neri.brumelame.domain.character;

import fr.neri.brumelame.domain.equipment.OffensiveEquipment;

import java.util.List;


public class Wizard extends Hero {

    private static final List<String> VALID_CLASSES_OFF_EQUIP = List.of("SPELL");
    private static final List<String> VALID_CLASSES_DEF_EQUIP = List.of("BARRIER");


    public Wizard (String name, int health, int attack){
        super(name, health, attack, null);
    }
    public Wizard(String name, int health, int attack , OffensiveEquipment equipment) {

        super(name, health, attack, equipment);
    }
    public Wizard(){}

    protected List<String> getValidClassOffEquip() {
        return VALID_CLASSES_OFF_EQUIP;
    }
    protected List<String> getValidClassDefEquip() {
        return VALID_CLASSES_DEF_EQUIP;
    }

}