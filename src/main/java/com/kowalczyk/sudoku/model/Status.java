package com.kowalczyk.sudoku.model;

public enum Status {
    INITIALLYFILLED, // cell is filled in the begining of the game
    TOFILL,          // cell is empty
    WRONGLYFILLED,   // player guess is against the rules
    CORRECTLYFILLED  // player guess is correct
}
