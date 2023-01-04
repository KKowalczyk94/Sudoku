package com.kowalczyk.sudoku.model;

public class Puzzle {
    private BoxCell[][] cell;
    private Status status;

    public Puzzle(BoxCell[][] cell, Status status) {
        this.cell = cell;
        this.status = status;
    }
}
