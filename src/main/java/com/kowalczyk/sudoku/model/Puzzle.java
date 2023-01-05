package com.kowalczyk.sudoku.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Puzzle {
    private Cell[][] cells;

    public int getPuzzleCellNumber(int row, int column){
       return cells[row][column].getNumber();
    }
}
