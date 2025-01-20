public class Farmer extends Entity{

    public Farmer(Game g, int sx, int sy){
        this.isAlive=true;
        this.game = g;
        this.posX = sx;
        this.posY = sy;
        this.type = Entity.EntityType.FARMER;
    }
    
    @Override
    public void run(){
        this.currentTile = game.getTile(this.getX(), this.getY());
        this.currentTile.enterTile(this);
        while(isAlive)
        {
            try{
                Thread.sleep(SettingsManager.getInt("farmer.sleepTime"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            if(currentTile.getIsFertile()==false){
                try{
                    Thread.sleep(SettingsManager.getInt("farmer.repairTime"));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                currentTile.restoreField();
            }
            if(currentTile.getHasCarrot()==false && currentTile.getIsCarrotGrowing()==false){
                System.out.println("planting a carrot");
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