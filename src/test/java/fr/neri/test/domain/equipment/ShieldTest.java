package fr.neri.test.domain.equipment;

import fr.neri.brumelame.domain.equipment.Shield;
import fr.neri.brumelame.domain.equipment.Spell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShieldTest {
    @Test
    void creation(){
        Shield shield = new Shield("DEFENSE","Bouclier","WEAPON","Un bouclier",5);
        assertEquals("Bouclier", shield.getName());

    }
    @Test
    void exception(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Shield("ATTACK","Bouclier","WEAPON","Un bouclier",5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Shield("DEFENSE","Bouclier","SPELL","Un bouclier",5);
        });
    }
}
