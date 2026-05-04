package fr.neri.test.domain.equipment;

import fr.neri.brumelame.domain.equipment.Spell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpellTest {

    @Test
    void creation(){
        Spell spell = new Spell("ATTACK","Sort","SPELL","Un sort",5);
        assertEquals("Sort", spell.getName());

    }
    @Test
    void exception(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Spell("DEFENSE","Sort","SPELL","Un sort",5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Spell("ATTACK","Sort","WEAPON","Un sort",5);
        });
    }
}
