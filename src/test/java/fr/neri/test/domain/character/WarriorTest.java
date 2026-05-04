package fr.neri.test.domain.character;

import fr.neri.brumelame.domain.character.Warrior;
import fr.neri.brumelame.domain.equipment.Barrier;
import fr.neri.brumelame.domain.equipment.Shield;
import fr.neri.brumelame.domain.equipment.Spell;
import fr.neri.brumelame.domain.equipment.Weapon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WarriorTest {

    private Weapon weapon = new Weapon("ATTACK", "Epee", "WEAPON", "Une Epee", 5);
    private Spell spell = new Spell("ATTACK", "Sort", "SPELL", "Un sort", 5);
    private Barrier barrier = new Barrier("DEFENSE", "Bouclier", "SPELL", "Un bouclier", 5);
    private Shield shield = new Shield("DEFENSE", "Bouclier", "WEAPON", "Un bouclier", 5);

    @Test
    void creation() {
        Warrior warrior = new Warrior("Valdemar"
                , 10
                , 10,
                weapon);
        assertEquals("Valdemar", warrior.getName());
    }
    @Test
    void creationWithEquipDef() {
        Warrior warrior = new Warrior("Valdemar"
                , 10
                , 10,
               weapon,
                shield);
        assertEquals("Valdemar", warrior.getName());
    }
    @Test
    void exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            Warrior warrior = new Warrior("Valdemar"
                    , 10
                    , 10,
                    spell);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Warrior warrior = new Warrior("Valdemar"
                    , 10
                    , 10,
                    weapon,
                    barrier
                    );
        });
    }
    @Test
    void EquipEquipment(){
        Warrior warrior = new Warrior("Valdemar"
                , 10
                , 10,
                weapon);


        warrior.equipOffEquip( weapon);
        assertEquals(weapon,warrior.getOffEquip());

        warrior.equipDefEquip(shield);

        assertEquals(shield, warrior.getDefEquip());



    }

    @Test
    void exceptionEquipEquipment() {
        Warrior warrior = new Warrior("Valdemar"
                , 10
                , 10,
                weapon);


        assertThrows(IllegalArgumentException.class, () -> {
            warrior.equipOffEquip(spell);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            warrior.equipDefEquip(barrier);
        });
    }

    @Test
    void damage(){
        Warrior warrior = new Warrior("Valdemar"
                , 10
                , 10,
                weapon);


        assertEquals(15, warrior.getDamage());
        assertEquals(warrior.getAttack()+warrior.getOffEquip().getBonus(),warrior.getDamage());
    }

    @Test
    void defendAndReceivedDamage(){
        Warrior warrior = new Warrior("Valdemar"
                , 10
                , 10,
                weapon);

        assertEquals(0,warrior.getDefense());
        warrior.equipDefEquip(shield);

        assertEquals(5,warrior.getDefense());

        warrior.receivedDamage(10);
        assertEquals(5,warrior.getHealth());


    }

    @Test
    void heal(){
        Warrior warrior = new Warrior("Valdemar"
                , 10
                , 10,
                weapon);

        warrior.getHeal(5);
        assertEquals(10, warrior.getHealth());

        warrior.receivedDamage(3);
        warrior.getHeal(5);
        assertEquals(10, warrior.getHealth());

        warrior.receivedDamage(7);
        warrior.getHeal(5);
        assertEquals(8, warrior.getHealth());

    }
}
