import java.util.Random;

public class RabbitSpawner implements Runnable{
    private Game game;
    private Thread thisThread;

    RabbitSpawner(Game g){
        this.game = g;
    }

    public void runInstance(){
        thisThread = new Thread(this);
        thisThread.start();
    }

    public void run(){
        while(true)
        {
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            Random random = new Random();
            int i = random.nextInt(game.getFieldSize());
            int j = random.nextInt(game.getFieldSize());
            new Rabbit(game, i, j).runInstance();
        }
    }
}
