package minesweeper.objects;

import java.util.Random;

public class Grid {

    Tile[][] gridMatrix;
    int mineAmount;
    int placedMines = 0;
    Random random = new Random();

    public Grid(int _size, int _mineAmount) {
        gridMatrix = new Tile[_size][_size];
        mineAmount = _mineAmount;
        generateGrid();
    }

    private void generateGrid() {
        for (int x = 0; x < getSize(); x++) {
            for (int y = getSize() - 1; y >= 0; y--) {
                gridMatrix[x][y] = new Tile(x, y);
            }
        }
        placeMines();
    }
    /// @param flag o for open, a for adjacency
    public void updateTilesSurrounding(Tile tile, char flag) {
        for (int x = -1; x <= 1; x++) {
            if (tile.x + x >= 0 && tile.x + x < getSize()) {
                for (int y = -1; y <= 1; y++) {
                    if (tile.y + y >= 0 && tile.y + y < getSize()) {
                        Tile adjTile = getTile(tile.x + x, tile.y + y);
                        switch (flag){
                            case 'a':
                                adjTile.addMineAdjacency(1);
                                break;
                            case 'o':
                                if (!adjTile.isMine() && adjTile.getMineAdjacency() <= 1){
                                    adjTile.open();
                                }
                                break;
                            default:
                                System.out.println("Critical Error, Flag " + flag + " Unknown.");
                        }
                    }
                }
            }
        }
    }

    private void placeMines() {
        while (placedMines < mineAmount) {
            for (int x = 0; x < getSize(); x++) {
                for (int y = getSize() - 1; y >= 0; y--) {
                    Tile tile = getTile(x, y);
                    if (tile.state == State.UNOPENED && !tile.isMine() && placedMines < mineAmount) {
                        if (random.nextInt(0, getSize() * getSize()) == 0) {
                            tile.isMine = true;
                            updateTilesSurrounding(tile,'a');
                            placedMines++;
                        }
                    }
                }
            }
        }
    }

    public void reveal() {
        for (int x = 0; x < getSize(); x++) {
            for (int y = getSize() - 1; y >= 0; y--) {
                getTile(x, y).open();
            }
        }
    }

    public int getSize() {
        return gridMatrix.length;
    }

    public Tile getTile(int x, int y) {
        return gridMatrix[x][y];
    }


}
