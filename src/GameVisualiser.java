public class GameVisualiser implements Runnable {
    private final Game game;

    // Constructor to initialize the Game object
    public GameVisualiser(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            printGameGrid(); // Print the updated grid
            try {
                Thread.sleep(100); // Refresh every 500 milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Method to print the game grid in the console
    public void printGameGrid() {
        int size = game.getFieldSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Tile tile = game.getTile(i,j);
                char tileChar = getTileRepresentation(tile);
                System.out.print(tileChar + " ");
            }
            System.out.println();  // Move to the next line after each row
        }
    }

    // Helper method to get the character representation for each tile
    private char getTileRepresentation(Tile tile) {
        Entity.EntityType entity = tile.getCurrentEntity();
        
        // Check for entities and return appropriate characters
        if (entity == Entity.EntityType.FARMER) {
            return 'F';
        } else if (entity == Entity.EntityType.RABBIT) {
            return 'R';
        } else if (entity == Entity.EntityType.DOG) {
            return 'W';
        } else if (tile.getIsFertile()) {
            return '.';
        } else {
            return 'D';
        }
    }
}
