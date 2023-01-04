package com.kowalczyk.sudoku.model;

import lombok.AllArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@Setter
public class BoxCell {
    private int row;
    private int column;
    private int number;
    private Status cellStatus;
}
