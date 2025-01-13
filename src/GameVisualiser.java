import javax.swing.*;
import java.awt.*;

public class GameVisualiser extends JPanel implements Runnable {
    private final Game game;
    private final int tileSize = 40;
    public GameVisualiser(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(game.getFieldSize() * tileSize, game.getFieldSize() * tileSize));
        setBackground(Color.WHITE);
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGameGrid(g);
    }

    private void drawGameGrid(Graphics g) {
        int size = game.getFieldSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Tile tile = game.getTile(i, j);
                drawTile(g, tile, i, j);
            }
        }
    }

    private void drawTile(Graphics g, Tile tile, int row, int col) {
        int x = col * tileSize;
        int y = row * tileSize;
    
        // Determine the color based on the tile's properties
        Entity.EntityType entity = tile.getCurrentEntityType();
        if (entity == Entity.EntityType.FARMER) {
            g.setColor(new Color(159, 89, 39)); // Farmer
        } else if (entity == Entity.EntityType.RABBIT) {
            g.setColor(Color.WHITE); // Rabbit
        } else if (entity == Entity.EntityType.DOG) {
            g.setColor(Color.BLACK); // Dog
        } else if (tile.getHasCarrot()) {
            g.setColor(Color.ORANGE); // Has Carrot
        } else if (tile.getIsCarrotGrowing()) {
            g.setColor(new Color(120, 50, 50)); // Is Carrot Growing
        } else if (tile.getIsFertile()) {
            g.setColor(new Color(119, 69, 19)); // Fertile land
        } else {
            g.setColor(Color.GREEN); // Non-fertile land
        }
    
        // Draw the tile rectangle
        g.fillRect(x, y, tileSize, tileSize);
    
        // Draw grid lines
        g.setColor(Color.BLACK);
        g.drawRect(x, y, tileSize, tileSize);
    }
    
}