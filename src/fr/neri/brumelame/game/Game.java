package fr.neri.brumelame.game;

import fr.neri.brumelame.dao.EquipmentDAO;
import fr.neri.brumelame.dao.HeroClassesDAO;
import fr.neri.brumelame.dao.HeroDAO;
import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.domain.character.HeroClasse;
import fr.neri.brumelame.domain.character.Warrior;
import fr.neri.brumelame.domain.character.Wizard;

import fr.neri.brumelame.domain.equipment.OffensiveEquipment;


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

    private final Menu menu;
    private final Dice dice;
    private final Board board;
    private final EquipmentDAO equipmentDAO;
    private final HeroClassesDAO heroClassesDAO;
    private final HeroDAO heroDAO;


    private int playerPosition = 0;
    private Hero hero;

    public Game(
            Menu menu,
            Dice dice,
            Board board,
            EquipmentDAO equipmentDAO,
            HeroClassesDAO heroClassesDAO,
            HeroDAO heroDAO
    ) {
        this.menu = Objects.requireNonNull(menu);
        this.dice = Objects.requireNonNull(dice);
        this.board = Objects.requireNonNull(board);
        this.equipmentDAO = Objects.requireNonNull(equipmentDAO);
        this.heroClassesDAO = Objects.requireNonNull(heroClassesDAO);
        this.heroDAO = Objects.requireNonNull(heroDAO);
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


            board.initializeCells(hero.getClass().getSimpleName());

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
     * @param type type de personnage attendu ({@code "wizard"} ou autre)
     * @param name nom du personnage
     * @return une instance de {@link Wizard} si le type vaut {@code "wizard"},
     * sinon une instance de {@link Warrior}
     */
    public Hero createHero(String type, String name) {
        String normalizedType = type == null ? "" : type.trim().toLowerCase();

        HeroClasse heroClasse;

        try {
            heroClasse = heroClassesDAO.findByName(normalizedType);
        } catch (Exception e) {
            throw new IllegalStateException("Classe de héros introuvable : " + normalizedType, e);
        }

        OffensiveEquipment equip = null;
        try {
            equip = (OffensiveEquipment) equipmentDAO.findByHeroClassAndLevelAndType(
                    normalizedType, 0, "ATTACK"
            );
        } catch (Exception e) {
            // équipement optionnel
        }

        Hero hero;
        try {
            String className = normalizedType.substring(0, 1).toUpperCase()
                    + normalizedType.substring(1);

            Class<?> clazz = Class.forName("fr.neri.brumelame.domain.character." + className);

            if (!Hero.class.isAssignableFrom(clazz)) { // Vérifie que la classe étend bien Hero
                throw new IllegalStateException(className + " n'est pas un Hero");
            }

            hero = (Hero) clazz.getDeclaredConstructor().newInstance(); // Crée une instance de la classe correspondante
        } catch (Exception e) {
            throw new IllegalArgumentException("Type de héros invalide : " + type, e);
        }

        hero.setName(name);
        hero.setHealth(heroClasse.getHealth());
        hero.setMaxHealth(heroClasse.getHealth());
        hero.setAttack(heroClasse.getAttack());
        if (equip != null) {
            hero.setOffEquip(equip);
        }


        hero.setCellId(board.getCell(0).getId());

        try {
            hero.setId(heroDAO.create(hero));
        } catch (Exception e) {
            throw new IllegalStateException("Impossible de sauvegarder le héros", e);
        }

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
