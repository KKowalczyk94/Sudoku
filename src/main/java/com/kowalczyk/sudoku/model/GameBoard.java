package com.kowalczyk.sudoku.model;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@AllArgsConstructor
public class GameBoard {
    private int boardSize = Integer.parseInt(SystemProperties.PROPS.getProperty("sudokuBoard.size"));
    private final int numberOfBoxes = boardSize;
    private Puzzle puzzle;

}
