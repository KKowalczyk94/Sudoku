package com.kowalczyk.sudoku.service;

import com.kowalczyk.sudoku.model.*;

import java.util.Random;

public class PuzzleService {
    private int boardSize = Integer.parseInt(SystemProperties.PROPS.getProperty("sudokuBoard.size"));;
    private int cellsForPlayerToGuess = Integer.parseInt(SystemProperties.PROPS.getProperty("sudokuBoard.cellsForPlayerToGuess"));;
    Puzzle puzzle9x9;
    Random random = new Random();
    BoxCell[][] boxCellBoard9x9 = new BoxCell[9][9];
    int[][] intBoard = new int[][] {
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

    public Puzzle createRandomPuzzleFilled9x9(BoxCell[][] boxCellBoard9x9){
        puzzle9x9.setCell(boxCellBoard9x9);
        return puzzle9x9;
    }

    public BoxCell[][] createRandomBoxCellBoardFilled9x9(){
        createBoxCellDeafaultBoard9x9(intBoard);
        shuffleNumbers();
        shuffleRows();
        shuffleColumns();
        shuffle3X3Rows();
        shuffleColumns();
        return boxCellBoard9x9;
    }

// 1 Find any filled board of sudoku. (use trivial ones will not affect final result)


    private void createBoxCellDeafaultBoard9x9(int [][] intBoard){
        for (int i = 0; i < boxCellBoard9x9.length; i++)
            for (int j = 0; j < boxCellBoard9x9.length; j++){
                boxCellBoard9x9[i][j].setRow(i);
                boxCellBoard9x9[i][j].setColumn(j);
                boxCellBoard9x9[i][j].setNumber(intBoard[i][j]);
                boxCellBoard9x9[i][j].setCellStatus(Status.INITIALLYFILLED);
            }
    }
// 2 for each number from 1 to 9 (say num), (i.e 1, 2, 3, 5, 6, 7, 8, 9)
// take a random number from range [1 to 9], traverse the board, swap num with your random number.

    void shuffleNumbers() {
        for (int i = 0; i < 9; i++) {
            int randomNumber = random.nextInt(9);
            swapNumbers(i, randomNumber);
        }
    }

    private void swapNumbers(int n1, int n2) {
        for (int i = 0; i<9; i++) {
            for (int j = 0; j<9; j++) {
                if (boxCellBoard9x9[j][i].getNumber() == n1) {
                    boxCellBoard9x9[j][i].setNumber(n2);
                } else if (boxCellBoard9x9[j][i].getNumber() == n2) {
                    boxCellBoard9x9[j][i].setNumber(n1);
                }
            }
        }
    }

// 3 Now shuffle rows. Take the first group of 3 rows , shuffle them ,
// and do it for all rows. (in 9 X 9 sudoku), do it for second group and as well as third.

    void shuffleRows() {
        int blockNumber;
        for (int i = 0; i < 9; i++) {
            int randomNumber = random.nextInt(3);
            blockNumber = i / 3;
            swapRows(i, blockNumber * 3 + randomNumber);
        }
    }

    void swapRows(int row1, int row2) {
        BoxCell[] row = boxCellBoard9x9[row1];
        boxCellBoard9x9[row1] = boxCellBoard9x9[row2];
        boxCellBoard9x9[row2] = row;
    }

// 4 Swap columns, again take block of 3 columns , shuffle them, and do it for all 3 blocks

    void shuffleColumns() {
        int blockNumber;
        for (int i = 0; i < 9; i++) {
            int ranNum = random.nextInt(3);
            blockNumber = i / 3;
            swapCols(i, blockNumber * 3 + ranNum);
        }
    }

    void swapCols(int column1, int column2) {
        BoxCell columnValue;
        for (int i = 0; i < 9; i++){
            columnValue = boxCellBoard9x9[i][column1];
            boxCellBoard9x9[i][column1] = boxCellBoard9x9[i][column2];
            boxCellBoard9x9[i][column2] = columnValue;
        }
    }

// 5 swap the row blocks itself (ie 3X9 blocks)

    void shuffle3X3Rows() {
        for (int i = 0; i < 3; i++) {
            int ranNum = random.nextInt(3);
            swap3X3Rows(i, ranNum);
        }
    }

    void swap3X3Rows(int row1, int row2) {
        for (int i = 0; i < 3; i++) {
            swapRows(row1 * 3 + i, row2 * 3 + i);
        }
    }

// 6 do the same for columns, swap blockwise

    void shuffle3X3Cols() {
        for (int i = 0; i < 3; i++) {
            int ranNum = random.nextInt(3);
            swap3X3Cols(i, ranNum);
        }
    }

    private void swap3X3Cols(int column1, int column2) {
        for (int i = 0; i < 3; i++) {
            swapCols(column1 * 3 + i, column2 * 3 + i);
        }
    }
}
