package com.kowalczyk.sudoku.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Setter
@Getter
public class Cell {
    private int row;
    private int column;
    private int number;
    private Status cellStatus;
}
