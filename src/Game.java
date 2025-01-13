public class Game {
    private final Tile[][] field;
    private final int size;

    public Game() {
        this.size = SettingsManager.getInt("game.size");
        this.field = new Tile[size][size];

        // Initialize the field with Tile objects
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                field[x][y] = new Tile(x, y);  // Initialize each Tile with coordinates
            }
        }
    }

    // Returns the tile at position (x, y)
    public Tile getTile(int x, int y) {
        if (isValidPosition(x, y)) {
            return field[x][y];  // Return the Tile at the specified coordinates
        }
        return null;
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public int getFieldSize() {
        return size;
    }
}
