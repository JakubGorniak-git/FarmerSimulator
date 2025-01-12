import java.util.Random;

public class Farmer extends Entity{
    private Tile currentTile;
    private Game game;
    private int posX;
    private int posY;
    
    public int getX(){
        return this.posX;
    }

    public int getY(){
        return this.posY;
    }

    public Farmer(Game g, int sx, int sy){
        this.game = g;
        this.posX = sx;
        this.posY = sy;
        this.type = Entity.EntityType.FARMER;
    }

    private int[][] randomDirections() {
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
    
    public void run(){
        this.currentTile = game.getTile(this.getX(), this.getY());
        this.currentTile.enterTile(this.type);
        while(true)
        {
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            // System.err.println("im here");
            if(currentTile.getHasCarrot()==false && currentTile.getIsCarrotGrowing()==false){
                // System.out.println("planting a carrot");
                currentTile.plantCarrot();
            }
            else{
                int [][] directions = this.randomDirections();
                for(int i=0;i<4;i++)
                {
                    int x = currentTile.getX() + directions[i][0];
                    int y = currentTile.getY() + directions[i][1];

                    if(game.isValidPosition(x, y)){
                        Tile newTile = game.getTile(x, y);
                        if(newTile.enterTile(this.type)==true){
                            this.posX = x;
                            this.posY = y;
                            this.currentTile.leaveTile();
                            this.currentTile = newTile;
                            break;
                        }
                    }
                }
            }
        }
        
    }
}