package fr.neri.brumelame.ui;

import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.game.cell.Cell;

import java.util.Scanner;

public class Menu {

    private Scanner input;

    public Menu() {
        input = new Scanner(System.in);
    }

    public int askMainStartChoice(){
        System.out.println();
        System.out.println("Brumelame");
        System.out.println("1. Nouveau Personnage");

        System.out.println("2. Quitter");

        return askInt("Choix :", 1, 2);



    }
    public int askMainChoice(){
        System.out.println();
        System.out.println("Brumelame");
        System.out.println("1. Menu Personnage");
        System.out.println("2. Commencer le jeu");
        System.out.println("3. Quitter");
        return askInt("Choix :", 1, 3);
    }

    public int askCharacterType(){
        System.out.println();
        System.out.println("Choisis ta classe :");
        System.out.println("1. Sorcier");
        System.out.println("2. Guerrier");
        return askInt("Choix" ,1 ,2 );
    }

    public int askCharacterMenuChoice() {
        System.out.println();
        System.out.println("Menu Personnage :");
        System.out.println("1. Voir les caractéristiques");
        System.out.println("2. Éditer le nom du personnage");
        System.out.println("3. Retourner au menu");
        System.out.println("4. Commencer le jeu");
        System.out.println("5. Quitter");

        return askInt("Choix" ,1 ,5 );
    }
    public String askNameCharacter(){
        System.out.println();
        return askString("Nom de votre personnage : ");
    }
    public int askAction(int characterPosition){
        System.out.println();
        System.out.println("Vous êtes à la position " +characterPosition );
        System.out.println("1. Avancer");
        return askInt("Choix",1,1);
    }

    public void printCharacter(Hero hero){
        System.out.println(hero);
    }

    public void printCell(Cell cell){
        System.out.println();
        System.out.println(cell.toString());

    }

    public void end(){
        System.out.println();
        System.out.println("Vous êtes arrivé à la fin, Bravo !.");
    }
    public void quit(){
        System.out.println();
        System.out.println("A bientôt sur Brumelame.");
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
}
