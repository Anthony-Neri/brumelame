package fr.neri.test.domain.equipment;

import fr.neri.brumelame.domain.equipment.Potion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PotionTest {

    @Test
    void creation(){
        Potion potion = new Potion("CONSUMABLE","Potion","POTION","Une potion",5);
        assertEquals("Potion", potion.getName());

    }
    @Test
    void exception(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Potion("DEFENSE","Potion","POTION","Une potion",5);

        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Potion("CONSUMABLE","Potion","SPELL","Une potion",5);

        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Potion("CONSUMABLE","Potion",null,"Une potion",5);

        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Potion(null,"Potion","SPELL","Une potion",5);

        });
    }
}
