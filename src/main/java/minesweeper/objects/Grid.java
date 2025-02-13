package minesweeper.objects;

public class Grid {
    Tile[][] gridMatrix;
    Grid(int _size, int _mineAmount){
        gridMatrix = new Tile[_size][_size];
    }
}
