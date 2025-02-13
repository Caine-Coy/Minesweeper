package minesweeper.objects;

import java.util.Random;

public class Grid {

    Tile[][] gridMatrix;
    int mineAmount;
    int placedMines = 0;
    Random random = new Random();

    public Grid(int _size, int _mineAmount){
        gridMatrix = new Tile[_size][_size];
        mineAmount = _mineAmount;
        generateGrid();
    }

    private void generateGrid(){
        for (int x = 0; x < getSize(); x++){
            for (int y = getSize()-1; y >= 0 ; y--){
                gridMatrix[x][y] = new Tile();
            }
        }
        placeMines();
    }

    private void placeMines() {
        while (placedMines < mineAmount) {
            for (int x = 0; x < getSize(); x++) {
                for (int y = getSize() - 1; y >= 0; y--) {
                    Tile tile = getTile(x, y);
                    if (tile.state == State.UNOPENED && !tile.isMine && placedMines < mineAmount) {
                        if (random.nextInt(0,getSize()*getSize()) == 0) {
                            tile.isMine = true;
                            System.out.println("placed mine at " + x + ":" + y);
                            placedMines++;
                        }
                    }
                }
            }
        }
    }

    public void reveal(){
        for (int x = 0; x < getSize(); x++){
            for (int y = getSize()-1; y >= 0; y--){
                getTile(x,y).open();
            }
        }
    }
    public int getSize(){
        return gridMatrix.length;
    }

    public Tile getTile(int x,int y){
        return gridMatrix[x][y];
    }




}
