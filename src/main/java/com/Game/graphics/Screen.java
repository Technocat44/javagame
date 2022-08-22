package com.Game.graphics;

// displays our pixels, fills them in
public class Screen {

    private int width, height;
    public int[] pixels; //

    //############DEMO CODE TO SHOWCASE ANIMATIONS######################
    int time = 0;
    int counter = 0;
    // update render for loop, replace x and y with time
    //##################################################################

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        // we have created one integer for each pixel in our game
        pixels = new int[width * height];
    }

    // set each pixel to 0 or black after an image is rendered
    // (clearing the screen)
    public void clear() {
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
    }
    public void render() {
        //############DEMO CODE TO SHOWCASE ANIMATIONS######################
        counter++;
        if (counter % 100 == 0){
            time++;
        }
        //##################################################################
        // iterate over the rows one by one which is why x is inner loop
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                /* since pixels is a one dimensional array
                there are no coordinates. so we create our own basically

                 x +(y * width) gives you the correct pixel.
                But if you think about it, the pixels are numbered from left to right.
                If your width is 100 and your pixel's x is 19, in each row it will
                be the 20th pixel in that row (remember we count from 0). To get to
                 same column in the next row you'll have to count another 100 (the width).

                1st row: x = 19    y=0     19 + (0*100) = 19
                2nd row: x = 19    y=1     19 + (1*100) = 119
                3rd row: x = 19    y=2     19 + (2*100) = 219

                So thinking in terms of rows and columns: (y*width) is an entire row,
                 x is what column you're in.
                If I want the 20th column(x) in the 30th row (y*width),
                 I have to add them together:
                 x + (y*width) or 19 + (29*100)  = 2919
                   20 + (30 * 100) = 500
                   (20, 30)
                */
                // to specifically target one pixel change the x and y to a
                // hardcoded value, i.e. pixels[20 + (40 * width)]
                pixels[x + (y * width)] = 0xff00ff; // pink


                // we need to clear the screen after each image

            }
        }
    }
}
