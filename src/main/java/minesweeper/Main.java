package minesweeper;

import minesweeper.objects.Grid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static Grid grid;
    static Display display = new Display();
    public static void main(String[] args) {
        System.out.println("Welcome to Minesweeper.\n Coordinates Start At The Top Left ☺");
        startGame();
        processInput();
    }
    static void processInput(){
        String input = awaitInput().toLowerCase();
        String[] inputArr = input.split(" ");
        String command = inputArr[0];
        inputArr = inputArr[1].split(":");
        int x = Integer.parseInt(inputArr[0]);
        int y = Integer.parseInt(inputArr[1]);

        System.out.println("DEBUG " + command + " " + x + " " + y);
    }

    static void startGame(){
        System.out.println("How Many Rows/Columns Do You Want The Grid To Be?");
        int size = Integer.parseInt(awaitInput());
        System.out.println("How Many Mines Do You Want?");
        int mines = Integer.parseInt(awaitInput());
        grid = new Grid(size,mines);
        display.drawGrid(grid);
    }

    static String awaitInput(){
        try{
            BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
            return r.readLine();
        }
        catch (IOException _) {
            //TODO error message
            return "";
        }
    }
}