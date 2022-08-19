package com.Game.java;

public class Game {

    /* public means we can access it anywhere in our project

       static means for this class, if there are multiple instances
       of the game class the width will never change.
       static also enables us to use width in another class

    * */
    public static int width = 300;
    public static int height = width / 16 * 9;
}
