package minesweeper;

import minesweeper.objects.Grid;
import minesweeper.objects.State;
import minesweeper.objects.Tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Main {
    static Grid grid;
    static Display display = new Display();

    public static void main(String[] args) {
        System.out.println("Welcome to Minesweeper.");
        startGame();

    }

    static void processInput() {
        String input = awaitInput().toLowerCase();
        String[] inputArr = input.split(" ");
        String command = inputArr[0];
        inputArr = inputArr[1].split(":");
        try {
            int x = Integer.parseInt(inputArr[0]);
            int y = Integer.parseInt(inputArr[1]);
            System.out.println("DEBUG " + command + " " + x + " " + y);
            Tile tile = grid.getTile(x, y);
            if (Objects.equals(command, "o")) {
                tile.open();
                if (tile.isMine) {
                    endGame();
                } else {
                    update();
                }
            } else {
                if (tile.getState() != State.OPENED){
                    tile.flag();
                }
                else {
                    System.out.println("Tile is already open!");
                }
                update();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Command. Please Try Again");
        }
    }

    static void endGame() {
        grid.reveal();
        display.drawGrid(grid);
        System.out.println("Thank you for playing!");
    }

    static void startGame() {
        System.out.println("How Many Rows/Columns Do You Want The Grid To Be?");
        int size = Integer.parseInt(awaitInput());
        System.out.println("How Many Mines Do You Want?");
        int mines = Integer.parseInt(awaitInput());
        grid = new Grid(size, mines);
        update();
    }

    static void update() {
        display.drawGrid(grid);
        processInput();
    }

    static String awaitInput() {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
            return r.readLine();
        } catch (IOException _) {
            //TODO error message
            return "";
        }
    }
}