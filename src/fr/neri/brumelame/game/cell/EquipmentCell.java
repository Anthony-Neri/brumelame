package fr.neri.brumelame.game.cell;

import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.domain.equipment.Equipment;
import fr.neri.brumelame.domain.equipment.OffensiveEquipment;
import fr.neri.brumelame.ui.Menu;

public class EquipmentCell extends Cell{
    private Equipment equipement;

    public EquipmentCell(int number, int boardId, Equipment equipement) {
        this.equipement = equipement;
        super(number,boardId);
    }

    @Override
    public Equipment getEquipment() {
        return equipement;
    }

    @Override
    public String toString() {
        return "C'est une arme";
    }

    @Override
    public StringBuilder interact(Hero hero) {
        Menu menu = new Menu();
        int choice = menu.askEquipEquipement(this.equipement);
        if (choice ==1) hero.setOffEquip((OffensiveEquipment) equipement);

        return new StringBuilder("Vous vous équipez de " + getEquipment().getName());
    }
}
