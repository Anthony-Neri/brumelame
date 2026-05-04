package fr.neri.brumelame.domain.equipment;

import java.util.List;

public  abstract class ConsumableEquipment extends Equipment{
    private static final List<String> VALID_TYPES = List.of("CONSUMABLE");

    public ConsumableEquipment(String type, String name, String category, String description, int bonus) {
        super(type, name, category, description, bonus);


    }
    protected List<String> getValidTypes() {
        return VALID_TYPES;
    }

    protected abstract List<String> getValidCategories();
}
