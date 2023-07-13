package TileMap;

import Main.Game;
import Main.GamePanel;
import Tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TileMap {
    private int x;
    private int y;

    private int tileSize;
    private int[][] map;
    private int mapWidth;
    private int mapHeight;

    private BufferedImage tileSet;
    private Tile[][] tiles;

    private int minX;
    private int minY;
    private int maxX = 0;
    private int maxY = 0;

    public TileMap(String fileName, int tileSize) {
        this.tileSize = tileSize;

        try {
            // reading map file
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            // getting map dimensions
            mapWidth = Integer.parseInt(br.readLine());
            mapHeight = Integer.parseInt(br.readLine());
            map = new int[mapHeight][mapWidth];

            minX = GamePanel.WIDTH - mapWidth * tileSize;
            minY = GamePanel.HEIGHT - mapHeight * tileSize;

            String delimiters = "\\s+";
            for (int row = 0; row < mapHeight; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delimiters);
                for (int col = 0; col < mapWidth; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTiles(String fileName) {
        try {
            tileSet = ImageIO.read(new File(fileName));
            int numTilesAcross = (tileSet.getWidth() + 1) / (tileSize + 1);
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subimage;

            for (int col = 0; col < numTilesAcross; col++) {
                subimage = tileSet.getSubimage(
                        col * tileSize + col,
                        0,
                        tileSize,
                        tileSize
                );
                tiles[0][col] = new Tile(subimage, false);
                subimage = tileSet.getSubimage(
                        col * tileSize + col,
                        tileSize + 1,
                        tileSize,
                        tileSize
                );
                tiles[1][col] = new Tile(subimage, true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void update() {

    }

    public void draw(Graphics2D g) {
        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                // Current value
                int rc = map[row][col];

                int r = rc / tiles[0].length;
                int c = rc % tiles[0].length;

                g.drawImage(
                        tiles[r][c].getImage(),
                        x + col * tileSize,
                        y + row * tileSize,
                        null
                );

            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColTile(int x) {
        return x / tileSize;
    }

    public int getRowTile(int y) {
        return y / tileSize;
    }

    public int getTile(int row, int col) {
        return map[row][col];
    }

    public int getTileSize() {
        return tileSize;
    }

    public boolean isBlocked(int row, int col) {
        int rc = map[row][col];
        int r = rc / tiles[0].length;
        int c = rc % tiles[0].length;
        return tiles[r][c].isBlocked();
    }

    public void setX(int i) {
        x = i;
        if (x < minX) {
            x = minX;
        }
        if (x > maxX) {
            x = maxX;
        }
    }

    public void setY(int i) {
        y = i;
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }
    }


}
