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
    static boolean firstTurn = true;
    static boolean running = true;

    public static void main(String[] args) {
        System.out.println("Welcome to Minesweeper.");
        while (running) {
            startGame();
            System.out.println("Do You Want To Play Again? (y/n)");
            if (!awaitInput().equalsIgnoreCase("y")) {
                running = false;
            }
        }
    }

    static void processInput() {
        String input = awaitInput().toLowerCase();
        String[] inputArr = input.split(" ");
        String command = inputArr[0];
        inputArr = inputArr[1].split(":");
        try {
            int x = Integer.parseInt(inputArr[0]);
            int y = Integer.parseInt(inputArr[1]);
            Tile tile = grid.getTile(x, y);
            if (Objects.equals(command, "o")) {
                tile.open();
                if (firstTurn){
                    grid.placeMines();
                }
                if (tile.isMine()) {
                    endGame('l');
                } else {
                    grid.updateTilesSurrounding(tile, 'o');
                    update();
                }
            } else {
                if (tile.getState() != State.OPENED) {
                    tile.flag();
                } else {
                    System.out.println("Tile is already open!");
                }
                update();
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid Command. Please Try Again");
            update();
        }
    }

    /// @param flag w for winning. l for losing
    static void endGame(char flag) {
        grid.reveal();
        display.drawGrid(grid);
        switch (flag) {
            case 'w':
                System.out.println("YOU WIN! CONGRATS!");
                break;
            case 'l':
                System.out.println("YOU HAVE LOST!");
                break;
            default:
                System.out.println("UNEXPECTED ITEM IN FLAGGING AREA.");
        }
        System.out.println("Thank you for playing!");
    }

    static void startGame() {
        try {
            firstTurn = true;
            System.out.println("How Many Rows/Columns Do You Want The Grid To Be?");
            int size = Integer.parseInt(awaitInput());
            if (size > 99) {
                throw new Exception("Size too big!");
            }
            System.out.println("Do you want to chose how many mines? (y/n)");
            int _mines;
            if (awaitInput().equalsIgnoreCase("y")) {
                System.out.println("How Many Mines Do You Want?");
                _mines = Integer.parseInt(awaitInput());
            } else {
                _mines = size;
            }

            grid = new Grid(size, _mines);
            update();
        } catch (Exception e) {
            System.out.println("ERROR!: " + e + "\nPlease Try Again");
            startGame();
        }
    }

    static void update() {
        if (grid.minesRemaining() == 0 && !firstTurn) {
            endGame('w');
        } else {
            display.drawGrid(grid);
            processInput();
        }
    }

    static String awaitInput() {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
            return r.readLine();
        } catch (IOException _) {
            return "";
        }
    }
}