package fr.neri.brumelame.domain.equipment;

import java.util.List;

public class Shield extends DefensiveEquipment{

    private static final List<String> VALID_CATEGORIES = List.of("CONSUMABLE");

    public Shield(String type, String name, String category, String description, int bonus) {
        super(type, name, category, description, bonus);
    }

    protected List<String> getValidCategories(){
        return VALID_CATEGORIES;
    }
}
