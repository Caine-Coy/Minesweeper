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
    //Settings
    static boolean alwaysCascade = true;
    static final int maxGridSize = 99;
    static final int minGridSize = 2;

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
                if (firstTurn) {
                    grid.placeMines();
                }
                if (tile.isMine()) {
                    endGame('l');
                } else if (firstTurn || alwaysCascade) {
                    grid.updateTilesSurrounding(tile, 'o');
                    firstTurn = false;
                    update();
                }
                else {
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
            int _size = Integer.parseInt(awaitInput());
            if (_size >= maxGridSize) {
                throw new Exception("Size too big!");
            }
            if (_size < minGridSize) {
                throw new Exception("Size too small!");
            }

            System.out.println("Do you want to chose how many mines? (y/n)");
            int _mines;
            String _input = awaitInput();
            if (_input.equalsIgnoreCase("y")) {
                System.out.println("How Many Mines Do You Want?");
                _mines = Integer.parseInt(awaitInput());
            }
            else if (isStringInt(_input)){
                _mines = Integer.parseInt(_input);
            } else {
                System.out.println("Setting Mines To Default Amount.");
                _mines = (_size * _size) / 4;
            }
            if (!validateMineAmount(_mines,_size)) throw new Exception("Too many mines! " + _mines + " Mines Out Of " + (_size*_size) + " Tiles!");
            grid = new Grid(_size, _mines);
            update();

        } catch (Exception e) {
            System.out.println("ERROR!: " + e + "\nPlease Try Again");
            startGame();
        }
    }

    static boolean isStringInt(String _string){
        try
        {
            Integer.parseInt(_string);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }

    static boolean validateMineAmount(int _mines,int _size){
        return _mines < _size * _size;
    }

    static void update() {
        if (grid.minesRemaining() == 0 && !firstTurn && grid.unopenedTilesRemaining() == 0) {
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