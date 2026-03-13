package fr.neri.brumelame.game.cell;

import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.domain.equipment.ConsumableEquipment;
import fr.neri.brumelame.domain.equipment.DefensiveEquipment;
import fr.neri.brumelame.domain.equipment.Equipment;
import fr.neri.brumelame.domain.equipment.OffensiveEquipment;
import fr.neri.brumelame.ui.Menu;

public class EquipmentCell extends Cell{
    private Equipment equipment;

    public EquipmentCell(int number, int boardId, Equipment equipment) {
        this.equipment = equipment;
        super(number,boardId);
    }

    @Override
    public Equipment getEquipment() {
        return equipment;
    }

    @Override
    public String toString() {
        return equipment.getName();
    }

    @Override
    public void interact(Hero hero, Menu menu) {

        int choice = menu.askEquipEquipment(this.equipment);
        String action = "Vous vous équipez de ";
        if (choice == 1) {
            if (this.equipment instanceof OffensiveEquipment ) {
                hero.setOffEquip((OffensiveEquipment) this.equipment);
            } else if (this.equipment instanceof DefensiveEquipment ) {
                hero.setDefEquip((DefensiveEquipment) this.equipment);
            } else if (this.equipment instanceof ConsumableEquipment) {

                hero.getHeal(this.equipment.getBonus());
                action = "Vous utilisez ";
            }
            menu.printMessage(action + getEquipment().getName());
        }else {
            menu.printMessage("Vous laissez " + this.equipment.getName() + " à terre.");
        }


    }
}
