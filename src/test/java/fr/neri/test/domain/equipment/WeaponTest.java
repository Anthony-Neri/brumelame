package fr.neri.test.domain.equipment;

import fr.neri.brumelame.domain.equipment.Weapon;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class WeaponTest {


    @Test
    void creation(){
        Weapon weapon = new Weapon("ATTACK","Epee","WEAPON","Une épée",5);
        assertEquals("Epee", weapon.getName());

    }
    @Test
    void exception(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("DEFENSE","Epee","WEAPON","Une épée",5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("ATTACK","Epee","SPELL","Une épée",5);
        });
    }
}

