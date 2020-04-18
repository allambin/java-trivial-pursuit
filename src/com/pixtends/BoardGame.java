package com.pixtends;

import java.util.Random;

public class BoardGame {

    private void generateBoard() {

    }

    public int throwDie() {
        Random r = new Random();
        return r.ints(1, 6).findFirst().getAsInt();
    }
}
