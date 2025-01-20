public class Dog extends Entity{
    private int sight;
    private int targetX;
    private int targetY;
    private int distance;
    private boolean hasTarget;
    private Entity targetEntity;

    private void findRabbits() {
        distance = sight*4+10;
        this.hasTarget = false;
        for (int dist = 0; dist < sight && dist<distance; dist++) {
            for (int dx = -dist; dx <= dist; dx++) {
                for (int dy = -dist; dy <= dist; dy++) {
                    int x = this.getX() + dx;
                    int y = this.getY() + dy;
    
                    if (game.isValidPosition(x, y) && distance>Math.abs(dx)+Math.abs(dy)) {
                        Entity entity = game.getTile(x, y).getCurrentEntity();
                        // System.err.println(entity);
                        if (entity!=null && entity.type==Entity.EntityType.RABBIT) {
                            targetEntity = entity;
                            distance = Math.abs(dx)+Math.abs(dy);
                            this.hasTarget = true;
                            this.targetX = x;
                            this.targetY = y;
                        }
                    }
                }
            }
        }
    }

    public Dog(Game g, int sx, int sy){
        this.isAlive=true;
        this.game = g;
        this.posX = sx;
        this.posY = sy;
        this.sight = SettingsManager.getInt("dog.sight");
        this.hasTarget=false;
        this.type = Entity.EntityType.DOG;
    }
    
    @Override
    public void run(){
        this.currentTile = game.getTile(this.getX(), this.getY());
        this.currentTile.enterTile(this);
        while(isAlive)
        {
            //System.out.println("isalivee");
            try{
                Thread.sleep(SettingsManager.getInt("dog.sleepTime"));
            } catch (InterruptedException e) {
                destroy();
                break;
            }

            findRabbits();
            if(hasTarget){
                int x = currentTile.getX();
                int y = currentTile.getY();
                if(Math.abs(targetX-this.getX()) > Math.abs(targetY-this.getY())){
                    if(targetX>this.getX()){
                        x+=1;
                    }
                    else{
                        x-=1;
                    }
                }
                else{
                    if(targetY>this.getY()){
                        y+=1;
                    }
                    else{
                        y-=1;
                    }
                }
                if(game.isValidPosition(x, y)){
                    Tile newTile = game.getTile(x, y);
                    if(newTile.enterTile(this)==true){
                        this.posX = x;
                        this.posY = y;
                        this.currentTile.leaveTile();
                        this.currentTile = newTile;
                        distance--;
                    }
                }
                if (distance<2){
                    targetEntity.destroy();
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
