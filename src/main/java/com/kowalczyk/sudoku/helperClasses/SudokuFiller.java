package com.kowalczyk.sudoku.helperClasses;

import java.util.Random;

//Temporary class for testing logic
public class SudokuFiller { //Temporary class for testing logic

    Random random = new Random();

// 1 Find any filled board of sudoku. (use trivial ones will not affect final result)
    int[][] board = new int[][] {
            {1,2,3,  4,5,6,  7,8,9},
            {4,5,6,  7,8,9,  1,2,3},
            {7,8,9,  1,2,3,  4,5,6},

            {2,3,1,  5,6,4,  8,9,7},
            {5,6,4,  8,9,7,  2,3,1},
            {8,9,7,  2,3,1,  5,6,4},

            {3,1,2,  6,4,5,  9,7,8},
            {6,4,5,  9,7,8,  3,1,2},
            {9,7,8,  3,1,2,  6,4,5}
    };
// 2 for each number from 1 to 9 (say num), (i.e 1, 2, 3, 5, 6, 7, 8, 9)
// take a random number from range [1 to 9], traverse the board, swap num with your random number.

    void shuffleNumbers() {
        for (int i = 0; i < 9; i++) {
            int ranNum = random.nextInt(9);
            swapNumbers(i, ranNum);
        }
    }

    private void swapNumbers(int n1, int n2) {
        for (int y = 0; y<9; y++) {
            for (int x = 0; x<9; x++) {
                if (board[x][y] == n1) {
                    board[x][y] = n2;
                } else if (board[x][y] == n2) {
                    board[x][y] = n1;
                }
            }
        }
    }

// 3 Now shuffle rows. Take the first group of 3 rows , shuffle them ,
// and do it for all rows. (in 9 X 9 sudoku), do it for second group and as well as third.

    void shuffleRows() {
        int blockNumber;

        for (int i = 0; i < 9; i++) {
            int ranNum = random.nextInt(3);
            blockNumber = i / 3;
            swapRows(i, blockNumber * 3 + ranNum);
        }
    }

    void swapRows(int r1, int r2) {
        int[] row = board[r1];
        board[r1] = board[r2];
        board[r2] = row;
    }

// 4 Swap columns, again take block of 3 columns , shuffle them, and do it for all 3 blocks

    void shuffleCols() {
        int blockNumber;

        for (int i = 0; i < 9; i++) {
            int ranNum = random.nextInt(3);
            blockNumber = i / 3;
            swapCols(i, blockNumber * 3 + ranNum);
        }
    }
    void swapCols(int c1, int c2) {
        int colVal;
        for (int i = 0; i < 9; i++){
            colVal = board[i][c1];
            board[i][c1] = board[i][c2];
            board[i][c2] = colVal;
        }
    }
// 5 swap the row blocks itself (ie 3X9 blocks)

    void shuffle3X3Rows() {

        for (int i = 0; i < 3; i++) {
            int ranNum = random.nextInt(3);
            swap3X3Rows(i, ranNum);
        }
    }

    void swap3X3Rows(int r1, int r2) {
        for (int i = 0; i < 3; i++) {
            swapRows(r1 * 3 + i, r2 * 3 + i);
        }
    }
// 6 do the same for columns, swap blockwise

void shuffle3X3Cols() {

    for (int i = 0; i < 3; i++) {
        int ranNum = random.nextInt(3);
        swap3X3Cols(i, ranNum);
    }
}
    private void swap3X3Cols(int c1, int c2) {
        for (int i = 0; i < 3; i++) {
            swapCols(c1 * 3 + i, c2 * 3 + i);
        }
    }
}
