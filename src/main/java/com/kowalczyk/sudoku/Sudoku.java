package com.kowalczyk.sudoku;

import com.kowalczyk.sudoku.helperClasses.SudokuFiller;
import com.kowalczyk.sudoku.service.GameBoardService;
import com.kowalczyk.sudoku.service.PuzzleService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Sudoku {
    public static void main(String[] args) throws Exception {
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
        gameBoardService.displayBoard(gameBoardService.createGameBoard4x4(puzzleService.createRandomPuzzleBoardFilled4x4()));

    }
}
