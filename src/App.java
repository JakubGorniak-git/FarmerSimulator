import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SettingsManager.initialize();
        Game game = new Game();

        for (int i = 5; i < game.getFieldSize(); i+=100) {
            new Farmer(game, i, i).runInstance();
            new Dog(game, i, i).runInstance();
        }

        new RabbitSpawner(game).runInstance();
        GameVisualiser visualiser = new GameVisualiser(game);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Visualiser");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(visualiser);
            frame.pack();
            frame.setVisible(true);

            Thread visualiserThread = new Thread(visualiser);
            visualiserThread.start();
        });
    }
}
