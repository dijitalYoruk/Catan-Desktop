package com.catan.modal;

public class Die {
    int dice1;
    int dice2;
    public Die(){
        dice1 = 0;
        dice2 = 0;
    }

    public void rollDie(){
        double randomDouble = Math.random();
        randomDouble = randomDouble * 5 + 1;
        int randomInt = (int) randomDouble;
        double randomDouble2 = Math.random();
        randomDouble2 = randomDouble2 * 5 + 1;
        int randomInt2 = (int) randomDouble2;
        dice1 = randomInt;
        dice2 = randomInt2;
    }

    public int getDice1(){
        return dice1;
    }
    public int getDice2(){
        return dice2;
    }
    public int getDieSum(){
        return dice1 + dice2;
    }
}
