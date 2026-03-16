package fr.neri.brumelame.domain.equipment;

import java.util.List;

public abstract class Equipment {
    private int id;
    private String type;
    private String name;
    private String category;
    private String description;
    private int bonus;

    public Equipment(String type, String name, String category, String description, int bonus) {
        this.type = type;
        this.name = name;
        this.category = category;
        this.description = description;
        this.bonus = bonus;

        if (type == null || !getValidTypes().contains(type.toUpperCase())) {
            throw new IllegalArgumentException(
                    "Invalid type: " + type + ". Must be one of: " + getValidTypes()
            );
        }

        if (category == null || !getValidCategories().contains(category.toUpperCase())) {
            throw new IllegalArgumentException(
                    "Invalid category: " + category + ". Must be one of: " + getValidCategories()
            );
        }
    }
    protected abstract List<String> getValidTypes();
    protected abstract List<String> getValidCategories();

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", bonus=" + bonus +
                '}';
    }
}
