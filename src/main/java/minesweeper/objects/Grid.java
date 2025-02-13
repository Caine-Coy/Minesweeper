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
            for (int y = 0; y < getSize(); y++){
                gridMatrix[x][y] = new Tile();
            }
        }
    }

    public void reveal(){

    }
    public int getSize(){
        return gridMatrix.length;
    }

    public Tile getTile(int x,int y){
        return gridMatrix[x][y];
    }




}
