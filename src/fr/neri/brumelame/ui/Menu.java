package fr.neri.brumelame.ui;

import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.domain.equipment.ConsumableEquipment;
import fr.neri.brumelame.domain.equipment.DefensiveEquipment;
import fr.neri.brumelame.domain.equipment.Equipment;
import fr.neri.brumelame.domain.equipment.OffensiveEquipment;
import fr.neri.brumelame.game.cell.Cell;

import java.util.Scanner;

public class Menu {

    private Scanner input;

    public Menu() {
        input = new Scanner(System.in);
    }

    public int askMainStartChoice() {
        System.out.println();
        System.out.println("Brumelame");
        System.out.println("1. Nouveau Personnage");

        System.out.println("2. Quitter");

        return askInt("Choix :", 1, 2);


    }

    public int askMainChoice() {
        System.out.println();
        System.out.println("Brumelame");
        System.out.println("1. Menu Personnage");
        System.out.println("2. Commencer le jeu");
        System.out.println("3. Quitter");
        return askInt("Choix :", 1, 3);
    }

    public int askCharacterType() {
        System.out.println();
        System.out.println("Choisis ta classe :");
        System.out.println("1. Sorcier");
        System.out.println("2. Guerrier");
        return askInt("Choix", 1, 2);
    }

    public int askCharacterMenuChoice() {
        System.out.println();
        System.out.println("Menu Personnage :");
        System.out.println("1. Commencer le jeu");
        System.out.println("2. Voir les caractéristiques");
        System.out.println("3. Éditer le nom du personnage");
        System.out.println("4. Retourner au menu");
        System.out.println("5. Quitter");

        return askInt("Choix", 1, 5);
    }

    public String askNameCharacter() {
        System.out.println();
        return askString("Nom de votre personnage : ");
    }

    public int askAction(int characterPosition, Hero hero) {
        System.out.println();
        System.out.println("Vous êtes à la position " + characterPosition);
        System.out.println("Vous avez " + hero.getHealth() + " points de vie.");
        System.out.println("1. Avancer");
        System.out.println("2. Status");
        return askInt("Choix", 1, 2);
    }

    public void printCharacter(Hero hero) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║               FICHE DU HÉROS                 ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf("║ %-14s %-28s ║%n", "Nom           :", hero.getName());
        System.out.printf("║ %-14s %-28s ║%n", "Classe        :", hero.getHeroClass());
        System.out.printf("║ %-14s %-28d ║%n", "Vie           :", hero.getHealth());
        System.out.printf("║ %-14s %-28d ║%n", "Attaque       :", hero.getAttack());
        System.out.printf("║ %-14s %-28d ║%n", "Dégâts totaux :", hero.getDamage());
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf("║ %-44s ║%n", formatEquipment("Arme", hero.getOffEquip()));
        System.out.printf("║ %-44s ║%n", formatEquipment("Défense", hero.getDefEquip()));
        System.out.println("╚══════════════════════════════════════════════╝");
    }
    public void printMoveHero(int diceResult, int playerPosition){
        System.out.println();
        System.out.println("Résultat du dé : " + diceResult );
        System.out.println("Vous êtes à la case " + playerPosition );
    }
    private String formatEquipment(String label, Equipment equipment) {
        if (equipment == null) {
            return label + " : Aucun";
        }

        return label + " : " + equipment.getName() + " (+" + equipment.getBonus() + ")";
    }

    public void printCell(Cell cell) {
        System.out.println();
        System.out.println(cell.toString());

    }
    public int askEquipEquipement(Equipment equipment){
        String objetType ="";
        String bonus = "";
        String action = " Récupérer ? ";
        if (equipment instanceof OffensiveEquipment){
            objetType = " une arme";
            bonus = "dommages";
        }else if (equipment instanceof DefensiveEquipment){
            objetType = "un bouclier";
            bonus = "de défense";
        }else if (equipment instanceof ConsumableEquipment){
            objetType = " un consommable";
            bonus = " de vie";
            action = "Utiliser ? ";
        }

        System.out.println();
        System.out.println("Vous trouver " +  objetType + " :");
        System.out.println(equipment.getName() + " Bonus : + " + equipment.getBonus() + " " + bonus);
        System.out.println(action);
        System.out.println(" 1 Oui");
        System.out.println(" 2 Non");
        return askInt("Choix", 1, 2);

    }

    public void end() {
        System.out.println();
        System.out.println("Vous êtes arrivé à la fin, Bravo !.");
    }

    public void quit() {
        System.out.println();
        System.out.println("A bientôt sur Brumelame.");
    }
    public void defeat(){
        System.out.println();
        System.out.println("Vous avez perdu et c'était pas compliqué.");
        this.quit();

    }

    public int askInt(String message, int minValue, int maxValue) {

        while (true) {
            IO.println(message);

            if (input.hasNextInt()) {
                int value = input.nextInt();
                input.nextLine();

                if (value >= minValue && value <= maxValue) {
                    return value;
                }
            } else {
                input.next(); // supprime l'entrée invalide
            }

            System.out.println("Veuillez choisir un nombre entre " + minValue + " et " + maxValue);
        }
    }

    public String askString(String message) {

        IO.println(message);

        return input.nextLine().trim();


    }

    public void printInteractCell(StringBuilder string) {
        System.out.print(string);
    }
}
