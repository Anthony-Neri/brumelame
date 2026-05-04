package fr.neri.brumelame.exception;

public class HeroInitializationException extends Exception{

    public HeroInitializationException(String message) {
        super(message);
    }

    public HeroInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
