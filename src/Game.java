import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Game extends JPanel {
    int boardWidth, boardHeight;
    int tileSize = 25;

    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    Tile farmer;
    Random random = new Random(); // Generator losowych liczb

    Game(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.GREEN);

        int[] position = generateRandomPosition();
        farmer = new Tile(position[0], position[1]);

        // Uruchomienie wątku dla farmera
        FarmerThread farmerThread = new FarmerThread();
        new Thread(farmerThread).start();
    }

    private int[] generateRandomPosition() {
        int x = random.nextInt(boardWidth / tileSize);  // Losowanie pozycji X w granicach szerokości planszy
        int y = random.nextInt(boardHeight / tileSize); // Losowanie pozycji Y w granicach wysokości planszy
        return new int[]{x, y}; // Zwrócenie tablicy z dwoma wartościami
    }

    private void moveFarmerRandomly() {
        int direction = random.nextInt(4);

        switch (direction) {
            case 0: // Góra
                if (farmer.y > 0) farmer.y--;
                break;
            case 1: // Dół
                if (farmer.y < boardHeight / tileSize - 1) farmer.y++;
                break;
            case 2: // Lewo
                if (farmer.x > 0) farmer.x--;
                break;
            case 3: // Prawo
                if (farmer.x < boardWidth / tileSize - 1) farmer.x++;
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Wywołuje oryginalną implementację, aby wyczyścić ekran
        g.setColor(Color.BLUE); // Ustawia kolor na niebieski
        g.fillRect(farmer.x * tileSize, farmer.y * tileSize, tileSize, tileSize);
        // Rysuje prostokąt w miejscu, gdzie znajduje się farmer
    }

    // Klasa wątku sterującego ruchem farmera
    private class FarmerThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                moveFarmerRandomly(); // Przesuwa farmera
                SwingUtilities.invokeLater(() -> repaint()); // Aktualizuje widok w wątku Swing
                try {
                    Thread.sleep(500); // Czeka 500 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
