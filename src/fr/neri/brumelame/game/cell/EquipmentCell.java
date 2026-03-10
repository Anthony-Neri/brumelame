package fr.neri.brumelame.game.cell;

import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.domain.equipment.ConsumableEquipment;
import fr.neri.brumelame.domain.equipment.DefensiveEquipment;
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
        String action = "Vous vous équipez de ";
        if (choice == 1) {
            if (this.equipement instanceof OffensiveEquipment equipement) {
                hero.setOffEquip(equipement);
            } else if (this.equipement instanceof DefensiveEquipment equipement) {
                hero.setDefEquip(equipement);
            } else if (this.equipement instanceof ConsumableEquipment equipement) {

                hero.getHeal(equipement.getBonus());
                action = "Vous utilisez ";
            }
            return new StringBuilder(action + getEquipment().getName());
        }

        return new StringBuilder("Vous laissez " + this.equipement.getName() + " à terre.");
    }
}
