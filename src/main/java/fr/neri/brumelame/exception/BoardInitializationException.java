package fr.neri.brumelame.exception;

public class BoardInitializationException extends Exception {

    public BoardInitializationException(String message) {
        super("Le plateau n'a pas pu être initialisé. Veuillez vérifier la base de données.\n" + message);
    }
}
