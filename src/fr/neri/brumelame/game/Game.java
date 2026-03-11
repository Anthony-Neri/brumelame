package fr.neri.brumelame.game;

import fr.neri.brumelame.dao.EquipmentDAO;
import fr.neri.brumelame.dao.HeroClassesDAO;
import fr.neri.brumelame.dao.HeroDAO;
import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.domain.character.HeroClasse;
import fr.neri.brumelame.domain.character.Warrior;
import fr.neri.brumelame.domain.character.Wizard;
import fr.neri.brumelame.domain.enemy.Enemy;
import fr.neri.brumelame.domain.equipment.OffensiveEquipment;
import fr.neri.brumelame.game.cell.Cell;
import fr.neri.brumelame.game.cell.EnemyCell;
import fr.neri.brumelame.ui.Menu;

import java.util.Locale;
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
     * Interface console de dialogue avec le joueur.
     */
    private Menu menu;
    /**
     * Personnage contrôlé par le joueur.
     */
    private Hero hero;
    /**
     * Dé utilisé pour les déplacements.
     */
    private Dice dice;

    private int playerPosition = 0;

    private Board board;

    private EquipmentDAO equipmentDAO;
    private HeroClassesDAO heroClassesDAO;
    private HeroDAO heroDAO;

    /**
     * Initialise une nouvelle partie avec un menu et un dé standard (1 à 6).
     */
    public Game() {
        this.menu = new Menu();
        this.dice = new Dice(1, 6);
        this.board = new Board();
        this.equipmentDAO = new EquipmentDAO();
        this.heroClassesDAO = new HeroClassesDAO();
        this.heroDAO = new HeroDAO();

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

        board.initialize();


        String choice = menu.askCharacterType();
        this.hero = createHero(choice, menu.askNameCharacter());

        try {
            board.initializeCells(hero.getClass().getName());
        } catch (Exception $e) {}

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
                case 1 -> {
                    play();
                    return;
                }
                case 2 -> menu.printCharacter(hero);
                case 3 -> hero.setName(menu.askNameCharacter());
                case 4 -> {
                    mainMenu();
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
     * @param type type de personnage attendu ({@code "Wizard"} ou autre = le nom de la Classe)
     * @param name nom du personnage
     * @return une instance de {@link Wizard} si le type vaut {@code "Wizard"},
     * sinon une instance de {@link Warrior}
     */
    public Hero createHero(String type, String name) {
        HeroClasse heroClasse = null;
        OffensiveEquipment equip = null;
        Hero hero = null;

        try {
            equip = (OffensiveEquipment) equipmentDAO.findByHeroClasseAndNivAndType(type.toUpperCase(),0, "ATTACK");
        } catch (Exception $e) {}

        try {
            heroClasse = heroClassesDAO.findByName(type);
        } catch (Exception $e) {
            heroClasse = new HeroClasse("", 10, 1);
        }

        try {
            Class c = Class.forName("fr.neri.brumelame.domain.character." + type);
            hero = (Hero) c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
     
        hero.setName(name);
        hero.setHealth(heroClasse.getHealth());
        hero.setAttack(heroClasse.getAttack());
        hero.setBoardId(board.getId());
        hero.setCellId(board.getCell(0).getId());
        try {
            hero.setId(heroDAO.create(hero));
        } catch (Exception $e) {}

        return hero;
    }

    /**
     * Démarre la boucle de jeu jusqu'à atteindre la case finale.
     * <p>
     * À chaque tour, le joueur choisit une action. L'action de déplacement
     * fait avancer le personnage selon le résultat du dé.
     * </p>
     */
    public void play() {

        while (!gameOver() && !finishGame()) {


            int choice = menu.askAction(playerPosition, this.hero);
            switch (choice) {
                case 1 -> moveCharacter();
                case 2 -> menu.printCharacter(hero);
            }

        }


        if (gameOver()) {
            menu.defeat();
            return;
        }
        if (finishGame()) menu.end();

    }

    /**
     * Déplace le personnage en ajoutant le résultat d'un lancer de dé
     * à sa position actuelle.
     */
    private void moveCharacter() {
        int diceResult = dice.roll();
        playerPosition += diceResult;

        if (playerPosition > board.getNumberFinalCell()) playerPosition = board.getNumberFinalCell();

        menu.printMoveHero(diceResult, playerPosition);

        hero.setCellId(board.getCell(playerPosition).getId());

        this.interactCellHero(playerPosition);

    }

    private void interactCellHero(int playerPosition) {

        menu.printInteractCell(this.board.getCell(playerPosition).interact(this.hero));

    }

    private boolean gameOver() {
        return hero.getHealth() <= 0;
    }

    private boolean finishGame() {
        return playerPosition == board.getNumberFinalCell();
    }


}
