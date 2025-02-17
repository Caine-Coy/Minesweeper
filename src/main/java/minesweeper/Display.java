package minesweeper;

import minesweeper.objects.Grid;
import minesweeper.objects.State;
import minesweeper.objects.Tile;

public class Display {
    Display(){

    }
    public void drawGrid(Grid grid){

        for (int y = grid.getSize()-1; y >= 0;y--){

            System.out.print("\n" + y + " ");
            if (y< 10){
                System.out.print(" ");
            }

            for (int x = 0; x < grid.getSize(); x++){

                Tile tile = grid.getTile(x,y);

                switch (tile.getState()){
                    case State.UNOPENED:
                        System.out.print("◼ ");
                        break;
                    case State.OPENED:
                        if (tile.isMine()){
                            System.out.print("⚠ ");
                        }
                        else if (!tile.hasAdjacency) {
                            System.out.print("☐ ");
                        }
                        else{
                            System.out.print(tile.getMineAdjacency() + " ");
                        }
                        break;
                    case State.FLAGGED:
                        System.out.print("⚑ ");
                        break;
                }
            }
        }
        for (int x = 0; x < grid.getSize();x++) {
            if (x==0){
                System.out.print("\n   ");
            }
            if (x<10){
                System.out.print(x + " ");
            }
            else{
                System.out.print(x);
            }

        }
        System.out.println("\n(o)pen (f)lag X:Y");
    }
}
