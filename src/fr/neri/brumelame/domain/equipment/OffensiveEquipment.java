package fr.neri.brumelame.domain.equipment;

import java.util.List;

public abstract class OffensiveEquipment extends Equipment{

    private static final List<String> VALID_TYPES = List.of("ATTACK");

    public OffensiveEquipment(String type, String name, String category, String description, int bonus) {
        super(type, name, category, description, bonus);

    }

    protected List<String> getValidTypes() {
        return VALID_TYPES;
    }

    protected abstract List<String> getValidCategories();
}
