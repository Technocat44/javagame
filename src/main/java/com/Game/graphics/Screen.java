package com.Game.graphics;

// displays our pixels, fills them in
public class Screen {

    private int width, height;
    public int[] pixels; //

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        // we have created on integer for each pixel in our game
        pixels = new int[width * height];
    }

}
