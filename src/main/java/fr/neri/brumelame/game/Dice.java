package fr.neri.brumelame.game;

public class Dice {

    private int minValue;
    private int maxValue;

    public Dice(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int roll(){
        int range = (maxValue - minValue) + 1;
        return (int) ((range * Math.random()) + minValue);
    }
}
