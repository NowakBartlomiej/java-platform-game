package Animations;

import java.awt.image.BufferedImage;

public class Animation {
    private BufferedImage[] frames;
    private int currentFrame;

    private long startTime;
    private long delay;

    public Animation() {

    }

    public void setFrames(BufferedImage[] images) {
        frames = images;
        if (currentFrame >= frames.length) {
            currentFrame = 0;
        }
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void update() {
        if (delay == -1) return;

        long elapsed =(System.nanoTime() - startTime) / 1000000;
        if (elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if (currentFrame == frames.length) {
            currentFrame = 0;
        }
    }
    public BufferedImage getImage() {
        return frames[currentFrame];
    }
}
