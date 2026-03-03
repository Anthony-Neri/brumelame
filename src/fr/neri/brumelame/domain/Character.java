package fr.neri.brumelame.domain;

/**
 * Représente un personnage du jeu.
 * <p>
 * Un personnage possède un type (classe), un nom, des points de vie,
 * une valeur d'attaque, un équipement offensif et une position sur le plateau.
 * </p>
 */
public abstract class Character {
    /** Type du personnage (ex. Guerrier, Magicien). */
    private String type;
    /** Nom du personnage. */
    private String name;
    /** Points de vie du personnage. */
    private int health;
    /** Valeur d'attaque de base du personnage. */
    private int attack;
    /** Équipement offensif actuellement porté. */
    private OffensiveEquipement equipement;

    private DefensiveEquipement defEquipement;
    /** Position actuelle sur le plateau (case de départ : 1). */
    private int cell = 1;



    /**
     * Construit un personnage avec ses caractéristiques principales.
     *
     * @param type type/classe du personnage
     * @param name nom du personnage
     * @param health points de vie
     * @param attack valeur d'attaque
     * @param equipement équipement offensif initial
     */
    public Character(String type, String name, int health, int attack, OffensiveEquipement equipement) {
        this.type = type;
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.equipement = equipement;
        this.defEquipement = null;
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
    public OffensiveEquipement getEquipement() {
        return equipement;
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

    /**
     * Modifie les points de vie du personnage.
     *
     * @param health nouveaux points de vie
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Modifie l'équipement offensif du personnage.
     *
     * @param equipement nouvel équipement offensif
     */
    public void setEquipement(OffensiveEquipement equipement) {
        this.equipement = equipement;
    }

    /**
     * Retourne une représentation textuelle du personnage.
     *
     * @return chaîne décrivant le personnage
     */
    public String toString(){
        return this.name +
                " | Classe : " + this.type +
                " | Points de vie : " + this.health +
                " | Attaque : " + this.attack +
                " | Équipement : " + this.equipement;
    }

    /**
     * Retourne la case actuelle du personnage.
     *
     * @return numéro de case
     */
    public int getCell() {
        return cell;
    }

    /**
     * Définit la case actuelle du personnage.
     *
     * @param cell nouvelle case
     */
    public void setCell(int cell) {
        this.cell = cell;
    }

}
