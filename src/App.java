public class App {
    public static void main(String[] args) {
        Game game = new Game(10);

        for(int i=0;i<10;i++)
        {
            Farmer farmer = new Farmer(game, 3, 3);
            Thread farmerThread = new Thread(farmer);
            farmerThread.start();
        }

        GameVisualiser visualiser = new GameVisualiser(game);
        Thread visualiserThread = new Thread(visualiser);
        visualiserThread.start();
    }
}
