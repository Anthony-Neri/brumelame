package fr.neri.brumelame.game;

import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.exception.BoardInitializationException;
import fr.neri.brumelame.exception.HeroInitializationException;
import fr.neri.brumelame.service.HeroCreationService;
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

    private final Menu menu;
    private final Dice dice;
    private final Board board;
    private final HeroCreationService heroCreationService;

    private int playerPosition = 0;
    private Hero hero;

    public Game(
            Menu menu,
            Dice dice,
            Board board,
            HeroCreationService heroCreationService
    ) {
        this.menu = Objects.requireNonNull(menu);
        this.dice = Objects.requireNonNull(dice);
        this.board = Objects.requireNonNull(board);
        this.heroCreationService = Objects.requireNonNull(heroCreationService);
    }

    public void home() throws BoardInitializationException, HeroInitializationException {
        int choice = menu.askMainStartChoice();

        if (choice == 1) {
            startGame();
        } else {
            menu.quit();
        }
    }

    public void startGame() throws BoardInitializationException, HeroInitializationException {
        board.initialize();

        String choice = menu.askCharacterType();
        String name = menu.askNameCharacter();

        this.hero = heroCreationService.createHero(choice, name, board.getCell(0).getId());

        board.initializeCells(hero.getClass().getSimpleName());

        characterMenu();
    }

    public void mainMenu() {
        int choice = menu.askMainChoice();
        switch (choice) {
            case 1 -> characterMenu();
            case 2 -> play();
            case 3 -> menu.quit();
        }
    }

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

        if (finishGame()) {
            menu.end();
        }
    }

    /**
     * Déplace le personnage en ajoutant le résultat d'un lancer de dé
     * à sa position actuelle.
     */
    private void moveCharacter() {
        int diceResult = dice.roll();
        playerPosition += diceResult;

        if (playerPosition > board.getNumberFinalCell()) {
            playerPosition = board.getNumberFinalCell();
        }

        menu.printMoveHero(diceResult, playerPosition);

        hero.setCellId(board.getCell(playerPosition).getId());

        this.interactCellHero(playerPosition);
    }

    private void interactCellHero(int playerPosition) {
        this.board.getCell(playerPosition).interact(this.hero,this.menu);
    }

    private boolean gameOver() {
        return hero.getHealth() <= 0;
    }

    private boolean finishGame() {
        return playerPosition == board.getNumberFinalCell();
    }
}
