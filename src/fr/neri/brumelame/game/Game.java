package fr.neri.brumelame.game;

import fr.neri.brumelame.domain.character.Character;
import fr.neri.brumelame.domain.character.Warrior;
import fr.neri.brumelame.domain.character.Wizard;
import fr.neri.brumelame.game.cell.*;
import fr.neri.brumelame.ui.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Gère le cycle de vie d'une partie.
 * <p>
 * Cette classe orchestre les menus, la création du personnage,
 * les déplacements sur le plateau et la condition de fin de jeu.
 * </p>
 */
public class Game {

    /**
     * Dernière case à atteindre pour terminer la partie.
     */
    private final int FINAL_CELL = 4;

    /**
     * Interface console de dialogue avec le joueur.
     */
    private Menu menu;
    /**
     * Personnage contrôlé par le joueur.
     */
    private Character character;
    /**
     * Dé utilisé pour les déplacements.
     */
    private Dice dice;

    private int playerPosition = 0;

    private List<Cell> board;

    /**
     * Initialise une nouvelle partie avec un menu et un dé standard (1 à 6).
     */
    public Game() {
        this.menu = new Menu();
        this.dice = new Dice(1, 1);
        this.board = new ArrayList<Cell>();
    }

    /**
     * Affiche l'accueil et démarre ou quitte la partie selon le choix utilisateur.
     */
    public void home() {
        int choice = menu.askMainStartChoice();

        if (choice == 1) {
            startGame();
        } else {
            menu.quit();

        }
    }

    /**
     * Lance la création du personnage puis ouvre le menu personnage.
     */
    public void startGame() {
        int choice = menu.askCharacterType();
        String type = (choice == 1) ? "wizard" : "warrior";
        this.character = createCharacter(type, menu.askNameCharacter());

        characterMenu();

    }

    /**
     * Affiche le menu principal du jeu et exécute l'action choisie.
     */
    public void mainMenu() {
        int choice = menu.askMainChoice();
        switch (choice) {
            case 1 -> characterMenu();
            case 2 -> play();
            case 3 -> menu.quit();


        }
    }

    /**
     * Affiche en boucle le menu de gestion du personnage.
     * <p>
     * Permet notamment de consulter/modifier le personnage,
     * revenir au menu principal, jouer, ou quitter.
     * </p>
     */
    public void characterMenu() {

        while (true) {
            int choice = menu.askCharacterMenuChoice();

            switch (choice) {
                case 1 -> menu.printCharacter(character);
                case 2 -> character.setName(menu.askNameCharacter());
                case 3 -> {
                    mainMenu();
                    return;
                }
                case 4 -> {
                    play();
                    return;
                }
                case 5 -> {
                    menu.quit();
                    return;
                }


            }

        }


    }

    /**
     * Crée un personnage selon le type demandé.
     *
     * @param type type de personnage attendu ({@code "wizard"} ou autre)
     * @param name nom du personnage
     * @return une instance de {@link Wizard} si le type vaut {@code "wizard"},
     * sinon une instance de {@link Warrior}
     */
    public Character createCharacter(String type, String name) {
        if (Objects.equals(type, "wizard")) {
            return new Wizard(name);
        } else {
            return new Warrior(name);
        }
    }

    /**
     * Démarre la boucle de jeu jusqu'à atteindre la case finale.
     * <p>
     * À chaque tour, le joueur choisit une action. L'action de déplacement
     * fait avancer le personnage selon le résultat du dé.
     * </p>
     */
    public void play() {
        createBoard();

        while (playerPosition < FINAL_CELL){
            menu.printCell(board.get(playerPosition));

            int choice = menu.askAction(playerPosition);
            switch (choice) {
                case 1 -> moveCharacter();
            }


        };

        menu.end();


    }

    /**
     * Déplace le personnage en ajoutant le résultat d'un lancer de dé
     * à sa position actuelle.
     */
    private void moveCharacter() {
        int diceResult = dice.roll();
        playerPosition += diceResult;


    }

    private void createBoard(){
        board.add(new EmptyCell());
        board.add(new Ennemy());
        board.add(new Weapon());
        board.add(new Bonus());
    }


}
