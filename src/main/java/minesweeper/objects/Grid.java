package minesweeper.objects;

public class Grid {
    Tile[][] gridMatrix;
    int mineAmount;
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
