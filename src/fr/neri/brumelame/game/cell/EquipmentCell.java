package fr.neri.brumelame.game.cell;

import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.domain.equipment.Equipment;

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
        return null;
    }
}
