package Main;

import Entites.Player;
import TileMap.TileMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    // Dimension
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;

    // Thread
    private Thread thread;
    private int FPS =30;
    private int targetTime = 1000 / FPS;
    private boolean running;

    // Canvas
    private BufferedImage image;

    // Graphics
    private Graphics2D g;

    // Map
    private TileMap tileMap;

    // Player
    private Player player;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    @Override
    public void run() {
        init();

        // Starting game loop
        long startTime;
        long urdTime;
        long waitTime;

        while (running) {
            startTime = System.nanoTime();
            update();
            render();
            draw();

            urdTime = System.nanoTime() - startTime;
            waitTime = targetTime - urdTime / 1000000;
            if (waitTime < 0) {
                waitTime = 5;
            }

            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Initialization
    private void init() {
        running = true;

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        // Drawing map
        tileMap = new TileMap("Resources/map1.txt", 32);
        tileMap.loadTiles("Resources/tileset_basic.gif");

        // Initialization player
        player = new Player(tileMap);
        player.setX(50);
        player.setY(50);

    }

    private void update() {
        // Updating map
        tileMap.update();

        // Updating player
        player.update();
    }

    private void render() {
        // background screen
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);


        // drawing textures
        tileMap.draw(g);

        // drawing player
        player.draw(g);
    }

    private void draw() {
        Graphics g = getGraphics();
        g.drawImage(image, 0,0,null);
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent key) {

    }

    @Override
    public void keyPressed(KeyEvent key) {
        int code = key.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            player.setLeft(true);
        }
        if (code == KeyEvent.VK_RIGHT) {
            player.setRight(true);
        }
        if (code == KeyEvent.VK_UP) {
            player.setJumping(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
        int code = key.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            player.setLeft(false);
        }
        if (code == KeyEvent.VK_RIGHT) {
            player.setRight(false);
        }
    }
}
