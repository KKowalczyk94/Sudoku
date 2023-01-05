package com.kowalczyk.sudoku.service;

import com.kowalczyk.sudoku.model.GameBoard;
import com.kowalczyk.sudoku.model.Puzzle;
import com.kowalczyk.sudoku.model.SystemProperties;

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
        for (int i = 0; i < gameBoard.getPuzzle().getCell().length; i++)
            for (int j = 0; j < gameBoard.getPuzzle().getCell().length; j++){
                System.out.print(gameBoard.getPuzzle().getCell());
            }
    }
}
