package fr.neri.brumelame.domain.equipment;

import java.util.List;

public abstract class DefensiveEquipment extends Equipment{

    private static final List<String> VALID_TYPES = List.of("DEFENSE");

    public DefensiveEquipment(String type, String name, String category, String description, int bonus) {
        super(type, name, category, description, bonus);


    }
    protected List<String> getValidTypes() {
        return VALID_TYPES;
    }

    protected abstract List<String> getValidCategories();
}
