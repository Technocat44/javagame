package com.Game;
// runnable allows us to put (this) in the thread
// means the thread we create is attached to the game object
public class Game implements Runnable{

    /* public means we can access it anywhere in our project

       static means for this class, if there are multiple instances
       of the game class the width will never change.
       static also enables us to use width in another class

    * */
    public static int width = 300;
    // we want the game window to be in the 16:9 aspect ratio, based on the width
    public static int height = width / 16 * 9;
    // how much our game will be scaled up
    // eventually will multiply the width and height by the scale
    public static int scale = 3;


    // a thread is a process within a process, we make it an object
    private Thread gameThread;

    public synchronized void start() {
        gameThread = new Thread(this, "Display");
        gameThread.start(); // creates a new thread object and starts it
    }

    // java needs a way to stop the thread
    public synchronized void stop() {
        try {
            gameThread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
