package com.kowalczyk.sudoku.service;

import com.kowalczyk.sudoku.model.GameBoard;
import com.kowalczyk.sudoku.model.Puzzle;
import com.kowalczyk.sudoku.model.SystemProperties;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

public class PuzzleService {
    private int boardSize = Integer.parseInt(SystemProperties.PROPS.getProperty("sudokuBoard.size"));;
    private int cellsForPlayerToGuess = Integer.parseInt(SystemProperties.PROPS.getProperty("sudokuBoard.cellsForPlayerToGuess"));;
    GameBoard gameBoard;

    private void newCompletePuzzle(int cellsForPlayerToGuess){
        Random random = new Random();
        int k = random.nextInt(0, 10);
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++){
            }
    }
}
