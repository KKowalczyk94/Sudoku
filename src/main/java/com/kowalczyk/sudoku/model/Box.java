package com.kowalczyk.sudoku.model;

import lombok.Getter;

@Getter
public class Box { // box of 9 cells that has to be filled with numbers <1,9>
    private Cell[][] cell;
    private int [][] boxNumber;
    private int size = 9; //parametrize

    public Box(Cell[][] cell, int[][] boxNumber) {
        this.cell = cell;
        this.boxNumber = boxNumber;
    }

    public void setCell(int row, int column, int number) {
        cell[row][column].setNumber(number);
    }
}
