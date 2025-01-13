import java.util.Random;

public class Entity implements Runnable{
    protected int posX;
    protected int posY;
    protected Entity.EntityType type;
    protected Tile currentTile;
    protected Game game;
    protected boolean isAlive = true;
    protected Thread thisThread;

    protected int[][] randomDirections() {
        int[][] directions = {
            {0, 1},  // Up
            {0, -1}, // Down
            {-1, 0}, // Left
            {1, 0}   // Right
        };
    
        Random random = new Random();
    
        // Shuffle the array
        for (int i = directions.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Swap elements at indices i and index
            int[] temp = directions[i];
            directions[i] = directions[index];
            directions[index] = temp;
        }
    
        return directions;
    }

    protected void runInstance(){
        thisThread = new Thread(this);
        thisThread.start();
    }

    public void destroy(){
        this.isAlive = false;
        this.currentTile.leaveTile();
        thisThread.interrupt();
    }

    public enum EntityType {
        NONE,
        FARMER,
        RABBIT,
        DOG
    }

    public int getX(){
        return this.posX;
    }

    public int getY(){
        return this.posY;
    }

    public Entity.EntityType getType(){
        return this.type;
    }

    public void run(){
        return;
    }

}
