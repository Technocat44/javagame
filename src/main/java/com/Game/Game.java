package com.Game;

import javax.swing.*;
import java.awt.*;

// runnable allows us to put (this) in the thread
// means the thread we create is attached to the game object
public class Game extends Canvas implements Runnable{

    /* public means we can access it anywhere in our project

       static means for this class, if there are multiple instances
       of the game class the width will never change.
       static also enables us to use width in another class

    For a game, we are rendering an image to a screen, so we want
    the game to keep updating the image. So we create a game loop.

    We also need a window to display the game. We make the Game class
    extend Canvas. Canvas represents a black rectangle on the screen
    which can be drawn on.

    Create a new variable named frame of type JFrame, which will act
    as the window.

    */
    public static final long serialVersionUID = 1L; // java convention
    public static int width = 300;
    // we want the game window to be in the 16:9 aspect ratio, based on the width
    public static int height = width / 16 * 9;
    // how much our game will be scaled up
    // eventually will multiply the width and height by the scale
    public static int scale = 3;


    // a thread is a process within a process, we make it an object
    private Thread gameThread;
    private final JFrame frame; // JFrame is a window
    private boolean running = false;

    // constructor will only be run once when object is created
    public Game() {
        Dimension size = new Dimension(width*scale, height*scale);
        setPreferredSize(size); // method from canvas/component

        frame = new JFrame();
    }
    public synchronized void start() {
        running = true;
        gameThread = new Thread(this, "Display");
        gameThread.start(); // creates a new thread object and starts it
    }

    // java needs a way to stop the thread
    public synchronized void stop() {
        running = false;
        try {
            gameThread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* when we start this app it will start the run method,
    we need to create a while loop to continue rendering the game

    When we start the gameThread we set running to true,
    When we stop the gameThread we set running to false.
    */
    @Override
    public void run() {
        while(running){
            System.out.println("Running...");
        }
    }

    // static means it has no relation with the rest of the class
    public static void main(String[] args) {
        Game game = new Game();
        game.frame.setResizable(false); // prevents window from being resized
        game.frame.setTitle("MyGame");
        game.frame.add(game); // fills the window with something, since our class extends canvas it is a component
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // if you hit the close button, it will terminate the app
        game.frame.setLocationRelativeTo(null); // centers window in the screen
        game.frame.setVisible(true); // shows the frame/window

        game.start();
    }
}

