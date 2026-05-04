package fr.neri.test.domain.equipment;

import fr.neri.brumelame.domain.equipment.Barrier;
import fr.neri.brumelame.domain.equipment.Shield;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BarrierTest {
    @Test
    void creation(){
        Barrier barrier = new Barrier("DEFENSE","Bouclier","SPELL","Un bouclier",5);
        assertEquals("Bouclier", barrier.getName());

    }
    @Test
    void exception(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Barrier("ATTACK","Bouclier","SPELL","Un bouclier",5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Barrier("DEFENSE","Bouclier","WEAPON","Un bouclier",5);
        });
    }
}
