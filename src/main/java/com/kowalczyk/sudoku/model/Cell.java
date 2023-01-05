package com.kowalczyk.sudoku.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Cell {
    private int row;
    private int column;
    private int number;
    private Status cellStatus;

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
