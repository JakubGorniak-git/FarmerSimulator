import java.util.concurrent.locks.ReentrantLock;

public class Tile {
    private int x;
    private int y;
    private boolean isFertile;
    private boolean hasCarrot;
    private boolean isCarrotGrowing;
    private Entity.EntityType currentEntity;
    
    private final ReentrantLock stateLock;
    private final ReentrantLock occupancyLock;

    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.isFertile = true;
        this.hasCarrot = false;
        this.isCarrotGrowing = false;
        this.currentEntity = Entity.EntityType.NONE;
        this.stateLock = new ReentrantLock();
        this.occupancyLock = new ReentrantLock();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean getIsFertile() {
        stateLock.lock();
        try {
            return this.isFertile;
        } finally {
            stateLock.unlock();
        }
    }

    public boolean getHasCarrot() {
        stateLock.lock();
        try {
            return this.hasCarrot;
        } finally {
            stateLock.unlock();
        }
    }

    public boolean getIsCarrotGrowing() {
        stateLock.lock();
        try {
            return this.isCarrotGrowing;
        } finally {
            stateLock.unlock();
        }
    }

    public boolean canPlantCarrot() {
        stateLock.lock();
        try {
            return isFertile && !hasCarrot && !isCarrotGrowing;
        } finally {
            stateLock.unlock();
        }
    }

    public boolean canEatCarrot() {
        stateLock.lock();
        try {
            return hasCarrot && !isCarrotGrowing;
        } finally {
            stateLock.unlock();
        }
    }

    public boolean enterTile(Entity.EntityType entity) {
        if (occupancyLock.tryLock()) {
            try {
                this.currentEntity = entity;
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public void leaveTile() {
        if (occupancyLock.isHeldByCurrentThread()) {
            stateLock.lock();
            try {
                this.currentEntity = Entity.EntityType.NONE;
            } finally {
                stateLock.unlock();
            }
            occupancyLock.unlock();
        }
    }

    public void plantCarrot() {
        stateLock.lock();
        try {
            if (!canPlantCarrot()) {
                return;
            }
            this.isCarrotGrowing = true;
            startGrowingCarrot();
        } finally {
            stateLock.unlock();
        }
    }

    public void removeCarrot() {
        stateLock.lock();
        try {
            if (!canEatCarrot()) {
                return;
            }
            this.hasCarrot = false;
            this.isFertile = false;
        } finally {
            stateLock.unlock();
        }
    }

    public void restoreField() {
        stateLock.lock();
        try {
            Thread.sleep(1500); // Simulate restoration time
            this.isFertile = true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            stateLock.unlock();
        }
    }

    private void startGrowingCarrot() {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                stateLock.lock();
                try {
                    this.isCarrotGrowing = false;
                    this.hasCarrot = true;
                } finally {
                    stateLock.unlock();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                stateLock.lock();
                try {
                    this.isCarrotGrowing = false;
                } finally {
                    stateLock.unlock();
                }
            }
        }).start();
    }

    public Entity.EntityType getCurrentEntity() {
        stateLock.lock();
        try {
            return this.currentEntity;
        } finally {
            stateLock.unlock();
        }
    }
}