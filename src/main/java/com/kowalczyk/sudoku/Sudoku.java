package com.kowalczyk.sudoku;

import com.kowalczyk.sudoku.model.Cell;
import com.kowalczyk.sudoku.service.GameBoardService;
import com.kowalczyk.sudoku.service.PuzzleService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class Sudoku {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        PuzzleService puzzleService = new PuzzleService();
        GameBoardService gameBoardService = new GameBoardService();
        try (InputStream input = Sudoku.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (Objects.isNull(input)) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            prop.load(input);

            System.out.println("Sudoku board size = " + prop.getProperty("sudokuBoard.size"));
            System.out.println("Empty cells = " + prop.getProperty("sudokuBoard.cellsForPlayerToGuess"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // gameBoardService.displayBoard(gameBoardService.createGameBoard9x9(puzzleService.createRandomPuzzleBoardFilled9x9()));
        //   gameBoardService.displayBoard(gameBoardService.createGameBoard6x6(puzzleService.createRandomPuzzleBoardFilled6x6()));
        //   gameBoardService.displayBoard(gameBoardService.createGameBoard4x4(puzzleService.createRandomPuzzleBoardFilled4x4()));

        Cell[][] board = puzzleService.createRandomPuzzleBoardFilled9x9().getCells();
        puzzleService.deleteNumbersFromCells(board);
        while (!puzzleService.isPuzzleCompleted(board)) {
            String action = "a";
            gameBoardService.displayBoard(gameBoardService.createGameBoard9x9(puzzleService.createPuzzle9x9(board)));
            System.out.println("To put number into cell press p");
            System.out.println("To delete number from the cell press d");
            action = scanner.next();
            if (action.equals("p")) {
                puzzleService.putNumberIntoBoard(board);
            } else if (action.equals("d")){
                puzzleService.deleteCellNumber(board);
            }else {
                System.out.println("Wrong input");
            }
        }
        System.out.println("GAME WON!");
    }
}
