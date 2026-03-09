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
        this.dice = new Dice(1, 1);
        this.board = new Board("test", 4);
        equipmentDAO = new EquipmentDAO();
        heroClassesDAO = new HeroClassesDAO();
        heroDAO = new HeroDAO();

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
        System.out.println(board);

        int choice = menu.askCharacterType();
        String type = (choice == 1) ? "wizard" : "warrior";
        this.hero = createHero(type, menu.askNameCharacter());

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
                case 1 -> menu.printCharacter(hero);
                case 2 -> hero.setName(menu.askNameCharacter());
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
    public Hero createHero(String type, String name) {

        OffensiveEquipment epee = (OffensiveEquipment) equipmentDAO.find(2);

        HeroClasse heroClasse;

        if (Objects.equals(type, "wizard")) {
            heroClasse = heroClassesDAO.findByName("wizard");


            Wizard wizard = new Wizard(name, heroClasse.getHealth(), heroClasse.getAttack(), epee);
            wizard.setBoardId(board.getId());
            wizard.setCellId(board.getCell(0).getId());
            wizard.setId(heroDAO.create(wizard));
            return wizard;

        } else {

            heroClasse = heroClassesDAO.findByName("warrior");

            Warrior warrior = new Warrior(name, heroClasse.getHealth(), heroClasse.getAttack(), epee);
            warrior.setBoardId(board.getId());
            warrior.setCellId(board.getCell(0).getId());
            warrior.setId(heroDAO.create(warrior));
            return warrior;
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

        while (!gameOver() && !finishGame()) {

            int choice = menu.askAction(playerPosition, this.hero);
            switch (choice) {
                case 1 -> moveCharacter();
            }

        }
        ;

        if (gameOver()) menu.defeat();
        if (finishGame()) menu.end();

    }

    /**
     * Déplace le personnage en ajoutant le résultat d'un lancer de dé
     * à sa position actuelle.
     */
    private void moveCharacter() {
        int diceResult = dice.roll();
        playerPosition += diceResult;

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
        return playerPosition >= board.getFinalCell();
    }


}
