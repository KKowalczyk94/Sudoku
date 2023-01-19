package com.kowalczyk.sudoku.model.enums;

public enum CellStatus {
    INITIALLYFILLED, // cell is filled in the begining of the game
    FILLEDBYPLAYER,  // cell is filled by player and can be emptied by him
    TOFILL,          // cell is empty
    WRONGLYFILLED,   // player guess is against the rules
    CORRECTLYFILLED  // player guess is correct
}
