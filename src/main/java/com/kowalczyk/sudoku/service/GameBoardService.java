package com.kowalczyk.sudoku.service;

import com.kowalczyk.sudoku.model.GameBoard;
import com.kowalczyk.sudoku.model.Puzzle;

import java.util.Arrays;

public class GameBoardService {

    public GameBoard createGameBoard9x9(Puzzle puzzle){
        return new GameBoard(9, puzzle);
    }

    public GameBoard createGameBoard6x6(Puzzle puzzle){
        return new GameBoard(6, puzzle);
    }

    public GameBoard createGameBoard4x4(Puzzle puzzle){
        return new GameBoard(4, puzzle);
    }

    public void displayBoard(GameBoard gameBoard){
        for (int i = 0; i < gameBoard.getPuzzle().getCells().length; i++) {
            for (int j = 0; j < gameBoard.getPuzzle().getCells().length; j++) {
                System.out.print(gameBoard.getPuzzle().getPuzzleCellNumber(i, j) + "\t");
            }System.out.println();
        }
    }
}
