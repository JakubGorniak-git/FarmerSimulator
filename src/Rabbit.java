public class Rabbit extends Entity{

    public Rabbit(Game g, int sx, int sy){
        this.isAlive=true;
        this.game = g;
        this.posX = sx;
        this.posY = sy;
        this.type = Entity.EntityType.RABBIT;
    }

    
    public void eatCarrot(){
        try{
            Thread.sleep(SettingsManager.getInt("rabbit.eatingTime"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run(){
        this.currentTile = game.getTile(this.getX(), this.getY());
        this.currentTile.enterTile(this);
        while(isAlive)
        {
            try{
                Thread.sleep(SettingsManager.getInt("rabbit.runTime"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            if(currentTile.getHasCarrot()==true){
                // System.out.println("planting a carrot");
                this.eatCarrot();
                if(isAlive)
                {
                    currentTile.removeCarrot();
                }
            }
            else{
                int [][] directions = this.randomDirections();
                for(int i=0;i<4;i++)
                {
                    int x = currentTile.getX() + directions[i][0];
                    int y = currentTile.getY() + directions[i][1];

                    if(game.isValidPosition(x, y)){
                        Tile newTile = game.getTile(x, y);
                        if(newTile.enterTile(this)==true){
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
        destroy();
        
    }
}
