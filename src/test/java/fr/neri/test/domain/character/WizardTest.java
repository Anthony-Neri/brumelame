package fr.neri.test.domain.character;

import fr.neri.brumelame.domain.character.Wizard;
import fr.neri.brumelame.domain.equipment.*;
import fr.neri.brumelame.domain.equipment.Spell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WizardTest {

    private Weapon weapon = new Weapon("ATTACK", "Epee", "WEAPON", "Une Epee", 5);
    private Spell spell = new Spell("ATTACK", "Sort", "SPELL", "Un sort", 5);
    private Barrier barrier = new Barrier("DEFENSE", "Bouclier", "SPELL", "Un bouclier", 5);
    private Shield shield = new Shield("DEFENSE", "Bouclier", "WEAPON", "Un bouclier", 5);

    @Test
    void creation() {
        Wizard wizard = new Wizard("Merlin"
                , 10
                , 10,
                spell);
        assertEquals("Merlin", wizard.getName());
    }
    @Test
    void creationWithEquipDef() {
        Wizard wizard = new Wizard("Merlin"
                , 10
                , 10,
                spell,
                barrier);
        assertEquals("Merlin", wizard.getName());
    }

    @Test
    void exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            Wizard wizard = new Wizard("Merlin"
                    , 10
                    , 10,
                    weapon);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Wizard wizard = new Wizard("Merlin"
                    , 10
                    , 10,
                    spell,
                    shield
            );
        });
    }

    @Test
    void EquipEquipment() {
        Wizard wizard = new Wizard("Merlin"
                , 10
                , 10,
                spell);


        wizard.equipOffEquip(spell);
        assertEquals(spell, wizard.getOffEquip());

        wizard.equipDefEquip(barrier);

        assertEquals(barrier, wizard.getDefEquip());


    }

    @Test
    void exceptionEquipEquipment() {
        Wizard wizard = new Wizard("Merlin"
                , 10
                , 10,
                spell);


        assertThrows(IllegalArgumentException.class, () -> {
            wizard.equipOffEquip(weapon);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            wizard.equipDefEquip(shield);
        });
    }

}
