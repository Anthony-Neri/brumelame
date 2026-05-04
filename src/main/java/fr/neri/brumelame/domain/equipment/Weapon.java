package fr.neri.brumelame.domain.equipment;

import java.util.List;

public class Weapon extends OffensiveEquipment{

    private static final List<String> VALID_CATEGORIES = List.of("WEAPON");

    public Weapon(String type, String name, String category, String description, int bonus) {
        super(type, name, category, description, bonus);

    }
    protected List<String> getValidCategories(){
        return VALID_CATEGORIES;
    }
}
