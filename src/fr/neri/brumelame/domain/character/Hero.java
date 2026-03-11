package fr.neri.brumelame.domain.character;

import fr.neri.brumelame.domain.equipment.DefensiveEquipment;
import fr.neri.brumelame.domain.equipment.OffensiveEquipment;

/**
 * Représente un personnage du jeu.
 * <p>
 * Un personnage possède une classe, un nom, des points de vie,
 * une valeur d'attaque, un équipement offensif et une position sur le plateau.
 * </p>
 */
public abstract class Hero {

    private int id;
    /** Nom du personnage. */
    private String name;
    /** Points de vie du personnage. */
    private int health;

    private int maxHealth;
    /** Valeur d'attaque de base du personnage. */
    private int attack;
    /** Équipement offensif actuellement porté. */
    private OffensiveEquipment offEquip;
    /** Équipement défensif actuellement porté. */
    private DefensiveEquipment defEquip;
    /** ID du plateau. */
    private int boardId;
    /** ID de la cellule. */
    private int cellId;

    /**
     * Construit un personnage avec ses caractéristiques principales.
     *

     * @param name nom du personnage
     * @param health points de vie
     * @param attack valeur d'attaque
     * @param offEquip équipement offensif initial
     */
    public Hero( String name, int health, int attack, OffensiveEquipment offEquip) {

        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attack = attack;
        this.offEquip = offEquip;
        this.defEquip = null;
    }
    public Hero(){}

    // GUETTERS et SETTERS
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public OffensiveEquipment getOffEquip() {
        return offEquip;
    }

    public void setOffEquip(OffensiveEquipment offEquip) {
        this.offEquip = offEquip;
    }

    public DefensiveEquipment getDefEquip() {
        return defEquip;
    }

    public void setDefEquip(DefensiveEquipment defEquip) {
        this.defEquip = defEquip;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    //    Interaction
    
    public int getDamage(){
        return this.attack + (this.offEquip != null ? this.offEquip.getBonus() : 0);
    }
    public void receivedDamage(int damage){
        if (this.defEquip != null){
            damage = Math.max(damage - defEquip.getBonus() , 0);
        }

        this.health -= damage;
    }
    public void getHeal (int heal){
        this.health += heal;
        if (this.health > this.maxHealth) this.health = this.maxHealth;
    }

    /**
     * Retourne une représentation textuelle du personnage.
     *
     * @return chaîne décrivant le personnage
     */
    public String toString(){
        return this.name +
                " | Classe : " + this.getClass().getName() +
                " | Points de vie : " + this.health +
                " | Attaque : " + this.attack +
                " | Équipement offensif : " + this.offEquip +
                " | Équipement défensif : " + this.defEquip;
    }

}
