package com.kowalczyk.sudoku.model;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class Puzzle {
    private BoxCell[][] cell;
}
