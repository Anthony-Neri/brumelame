package fr.neri.brumelame.domain.equipment;

import java.util.List;

public class Barrier extends DefensiveEquipment{
    private static final List<String> VALID_CATEGORIES = List.of("SPELL");

    public Barrier(String type, String name, String category, String description, int bonus) {
        super(type, name, category, description, bonus);
    }

    protected List<String> getValidCategories(){
        return VALID_CATEGORIES;
    }

}
