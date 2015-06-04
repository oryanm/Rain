package net.oryanmat.rain;


import com.badlogic.gdx.graphics.Color;

public class Block {
    boolean empty = true;
    Color color = Color.CLEAR;

    public static Block getEmpty() {
        return new Block();
    }
}
