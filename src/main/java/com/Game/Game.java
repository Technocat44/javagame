package com.Game;

import com.Game.graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


// runnable allows us to put (this) in the thread
// means the thread we create is attached to the game object
public class Game extends Canvas implements Runnable {

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

    // a buffered Image object is an image with an accessible buffer of image data
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // once we have the image we can write to it
    // we want to retrieve a writable raster to change each pixel

    /* How this works is we contact the image, get the array of pixels that make
    up the image, get the dataBuffer that handles the raster

    We are converting the image object into an array of integers, which will
    single which pixel gets which color, and we can manipulate the pixels then
    however we want
     */
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    private Screen screen; //  we create a screen object


    // constructor will only be run once when object is created
    public Game() {
        Dimension size = new Dimension(width*scale, height*scale);
        setPreferredSize(size); // method from canvas/component
        screen = new Screen(width, height);
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

    People with faster computers will run the game faster
    People with slower computers will run the game slower
    WE need to normalize this so all game no matter the computer
    run at the same FPS.

    the run method is called when the game gets created in the constructor
    since Game implements Runnable, after Game is created, run is automatically called.
    */
    @Override
    public void run() {
        while(running){
            update(); // update will run 60 times per second, handles logic
            render(); // unlimited or as fast as we can, display image
        }
    }

    public void update() {

    }

    /* buffering
    a temporary storage place.

    When a game renders, it has to go pixel by pixel and update the
    color to the pixel, so instead of doing that live, we do it by
    buffering. We create the entire frame in a buffer and once it completes
    then we display it.

    We need a physical place in memory to store the frame as its buffering

    */
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3); // 3 is the golden number. starts 3 buffers each filling at different intervals
        }
        // need to make the buffer object visible
        if (bs != null){
            Graphics g = bs.getDrawGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.dispose(); // releases all systems resources, at end of frame need to remove the graphics we aren't using a
            bs.show(); // makes next buffer visible
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

    /*
    We need a window to display our game.
    For a game, we are rendering an image to a screen, so we want
    the game to keep updating the image. So we create a game loop.

    We also need a window to display the game. We make the Game class
    extend Canvas. Canvas represents a black rectangle on the screen
    which can be drawn on.

    Create a new variable named frame of type JFrame, which will act
    as the window.

    Then we create a render() and update() function to loop over while the
    game is running.

    When a game renders, it has to go pixel by pixel and update the
    color to the pixel, so instead of doing that live, we do it by
    buffering. We create the entire frame in a buffer and once it completes
    then we display it.

    We need a physical place in memory to store the frame as its buffering


    How do we manage to get images on the screen?
    We need to render a color to every single pixel on the screen.

    A RASTER is a data structure that represents a grid or array of pixels.
    Raster images are compiled using pixels, or tiny dots, containing unique color and tonal information that
    come together to create the image.

    Since raster images are pixel based, they are resolution dependent.
     The number of pixels that make up an image as well as how many of
     those pixels are displayed per inch, both determine the quality of
     an image. As you may have guessed, the more pixels in the image and
      the higher the resolution is, the higher quality the image will be.

    For example, if we scale a raster image to enlarge it, without changing
    resolution, it will lose quality and look blurry or pixilated. This
    is because we are stretching the pixels over a larger area, thus
    making them look less sharp. This is a common problem but can
    be remedied by using raster image editing programs such as Photoshop
    to change resolution and properly scale images.

    We use the bufferedImage class to handle this:

    The java.awt.Image class is the superclass that represents graphical
    images as rectangular arrays of pixels. The java.awt.image.BufferedImage class,
    which extends the Image class to allow the application to operate directly with
    image data (for example, retrieving or setting up the pixel color).
     Applications can directly construct instances of this class.

    The BufferedImage class is a cornerstone of the Java 2D immediate-mode
    imaging API. It manages the image in memory and provides methods for
    storing, interpreting, and obtaining pixel data. Since BufferedImage is
     a subclass of Image it can be rendered by the Graphics and Graphics2D
     methods that accept an Image parameter.

    A BufferedImage is essentially an Image with an accessible data buffer.
    It is therefore more efficient to work directly with BufferedImage.
    A BufferedImage has a ColorModel and a Raster of image data.
    The ColorModel provides a color interpretation of the image's pixel data.

    We create an array to store all of the pixels from the BufferedImage.


    Now we have the pixels from the Image, lets create a new Class called
    Screen.


     */
}

