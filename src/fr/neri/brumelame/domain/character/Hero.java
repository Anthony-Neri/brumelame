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
    /** Classe du personnage (ex. Guerrier, Magicien). */
    private String heroClass;
    /** Nom du personnage. */
    private String name;
    /** Points de vie du personnage. */
    private int health;
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
     * @param heroClass classe du personnage
     * @param name nom du personnage
     * @param health points de vie
     * @param attack valeur d'attaque
     * @param offEquip équipement offensif initial
     */
    public Hero(String heroClass, String name, int health, int attack, OffensiveEquipment offEquip) {
        this.heroClass = heroClass;
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.offEquip = offEquip;
        this.defEquip = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le nom du personnage.
     *
     * @return nom du personnage
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne les points de vie du personnage.
     *
     * @return points de vie
     */
    public int getHealth() {
        return health;
    }

    /**
     * Retourne la valeur d'attaque du personnage.
     *
     * @return attaque
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Retourne l'équipement offensif du personnage.
     *
     * @return équipement offensif
     */
    public OffensiveEquipment getOffEquip() {
        return offEquip;
    }

    /**
     * Modifie le nom du personnage.
     *
     * @param name nouveau nom
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Modifie la valeur d'attaque du personnage.
     *
     * @param attack nouvelle valeur d'attaque
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(String heroClass) {
        this.heroClass = heroClass;
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

    /**
     * Modifie les points de vie du personnage.
     *
     * @param health nouveaux points de vie
     */
    public void setHealth(int health) {
        this.health = health;
    }

    public void receivedDamage(int damage){
        this.health -= damage;
    }
    /**
     * Retourne une représentation textuelle du personnage.
     *
     * @return chaîne décrivant le personnage
     */
    public String toString(){
        return this.name +
                " | Classe : " + this.heroClass +
                " | Points de vie : " + this.health +
                " | Attaque : " + this.attack +
                " | Équipement offensif : " + this.offEquip +
                " | Équipement défensif : " + this.defEquip;
    }
}
