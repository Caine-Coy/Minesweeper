package minesweeper.objects;

public class Tile {
    State state = State.UNOPENED;
    int mineAdjacency = 0;
    boolean isMine = false;
    public boolean hasAdjacency = false;
    public int x;
    public int y;

    Tile(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public State getState() {
        return state;
    }

    public int getMineAdjacency() {
        return mineAdjacency;
    }

    public void open() {
        state = State.OPENED;
    }

    public void flag() {
        if (state == State.FLAGGED){
            state = State.UNOPENED;
        }
        else{
            state = State.FLAGGED;
        }
    }

    public void setMine() {
        isMine = true;
    }

    public boolean isMine() {
        return isMine;
    }

    public void addMineAdjacency(int adjacency) {
        hasAdjacency = true;
        mineAdjacency += adjacency;
    }
}
