import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameVisualiser extends JPanel implements Runnable {
    private final Game game;
    private final int tileSize = 40;
    private final Map<String, BufferedImage> textures;

    public GameVisualiser(Game game) {
        this.game = game;
        this.textures = new HashMap<>();
        setPreferredSize(new Dimension(game.getFieldSize() * tileSize, game.getFieldSize() * tileSize));
        setBackground(Color.WHITE);
        loadTextures();
    }

    private void loadTextures() {
        String[] textureNames = { "farmer", "rabbit", "dog", "carrot", "growing_carrot", "dirt", "grass"};
        for (String name : textureNames) {
            try {
                BufferedImage image = ImageIO.read(new File("src/textures/" + name + ".png"));
                textures.put(name, scaleImage(image, tileSize, tileSize));
            } catch (IOException e) {
                System.err.println("Cannot load texture: " + name);
            }
        }
    }

    private BufferedImage scaleImage(BufferedImage img, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(img, 0, 0, width, height, null);
        g2d.dispose();
        return scaledImage;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
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
        Graphics2D g2d = (Graphics2D) g;
        int size = game.getFieldSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Tile tile = game.getTile(i, j);
                drawTile(g2d, tile, i, j);
            }
        }
    }

    private void drawTile(Graphics2D g, Tile tile, int row, int col) {
        int x = col * tileSize;
        int y = row * tileSize;

        BufferedImage backgroundTexture;
        if (tile.getIsFertile()) {
            backgroundTexture = textures.get("grass");
        } else {
            backgroundTexture = textures.get("dirt");
        }

        if (backgroundTexture != null) {
            g.drawImage(backgroundTexture, x, y, null);
        }

        if (tile.getHasCarrot() && textures.get("carrot") != null) {
            g.drawImage(textures.get("carrot"), x, y, null);
        } else if (tile.getIsCarrotGrowing() && textures.get("growing_carrot") != null) {
            g.drawImage(textures.get("growing_carrot"), x, y, null);
        }

        Entity.EntityType entity = tile.getCurrentEntityType();
        BufferedImage entityTexture = null;

        if (entity == Entity.EntityType.FARMER) {
            entityTexture = textures.get("farmer");
        } else if (entity == Entity.EntityType.RABBIT) {
            entityTexture = textures.get("rabbit");
        } else if (entity == Entity.EntityType.DOG) {
            entityTexture = textures.get("dog");
        }

        if (entityTexture != null) {
            g.drawImage(entityTexture, x, y, null);
        }

        g.setColor(new Color(0, 0, 0, 50));
        g.drawRect(x, y, tileSize, tileSize);
    }
}
