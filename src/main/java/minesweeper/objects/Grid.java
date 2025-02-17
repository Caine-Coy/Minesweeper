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
    }

    /// @param flag o for open, a for adjacency
    public void updateTilesSurrounding(Tile tile, char flag) {
        for (int x = -1; x <= 1; x++) {
            if (tile.x + x >= 0 && tile.x + x < getSize()) {
                for (int y = -1; y <= 1; y++) {
                    if (tile.y + y >= 0 && tile.y + y < getSize()) {
                        Tile adjTile = getTile(tile.x + x, tile.y + y);
                        switch (flag) {
                            case 'a':
                                adjTile.addMineAdjacency(1);
                                break;
                            case 'o':
                                if (!adjTile.isMine() && adjTile.getState() == State.UNOPENED) {
                                    if (adjTile.getMineAdjacency() <= 1 && Math.abs(x) != Math.abs(y)) {
                                        adjTile.open();
                                        updateTilesSurrounding(adjTile, 'o');
                                    }
                                }
                                break;
                            default:
                                System.out.println("UNEXPECTED ITEM IN FLAGGING AREA.");
                        }
                    }
                }
            }
        }
    }

    public void placeMines() {
        while (placedMines < mineAmount) {
            for (int x = 0; x < getSize(); x++) {
                for (int y = getSize() - 1; y >= 0; y--) {
                    Tile tile = getTile(x, y);
                    if (tile.state == State.UNOPENED && !tile.isMine() && placedMines < mineAmount) {
                        if (random.nextInt(0, getSize() * getSize()) == 0) {
                            tile.isMine = true;
                            updateTilesSurrounding(tile, 'a');
                            placedMines++;
                        }
                    }
                }
            }
        }
    }

    /// gets the amount of unflagged mines remaining.
    public int minesRemaining() {
        int _minesRemaining = 0;
        for (int x = 0; x < getSize(); x++) {
            for (int y = getSize() - 1; y >= 0; y--) {
                Tile _tile = getTile(x, y);
                if (_tile.isMine() && _tile.getState() != State.FLAGGED) {
                    _minesRemaining++;
                }
            }
        }
        return _minesRemaining;
    }

    /// used for gameend, shows the entire grid.
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
