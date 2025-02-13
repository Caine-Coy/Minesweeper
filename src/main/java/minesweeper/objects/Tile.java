package minesweeper.objects;

public class Tile {
    State state = State.UNOPENED;
    int mineAdjacency = 0;
    public boolean isMine = false;
    public boolean hasAdjacency = false;

    public State getState(){
        return state;
    }

    public int getMineAdjacency(){
        return mineAdjacency;
    }

    public void open(){
        state = State.OPENED;
    }

    public void flag(){
        state = State.FLAGGED;
    }
}
