package Main;

import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        JFrame window = new JFrame("Kirby Platform");
        window.setIconImage(Toolkit.getDefaultToolkit().getImage("Resources/logo/platform_logo.jpg"));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new GamePanel());
        window.pack();
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
