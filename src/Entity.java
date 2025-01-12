public class Entity implements Runnable{
    protected int x;
    protected int y;
    protected Entity.EntityType type;
    
    public enum EntityType {
        NONE,
        FARMER,
        RABBIT,
        DOG
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Entity.EntityType getType(){
        return this.type;
    }

    public void run(){

    }

}
