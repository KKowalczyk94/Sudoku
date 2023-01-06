package com.kowalczyk.sudoku.service;

import com.kowalczyk.sudoku.model.Cell;
import com.kowalczyk.sudoku.model.Puzzle;
import com.kowalczyk.sudoku.model.Status;
import com.kowalczyk.sudoku.model.SystemProperties;

import java.util.Random;
import java.util.stream.IntStream;

public class PuzzleService {
    private int boardSize = Integer.parseInt(SystemProperties.PROPS.getProperty("sudokuBoard.size"));
    private int cellsForPlayerToGuess = Integer.parseInt(SystemProperties.PROPS.getProperty("sudokuBoard.cellsForPlayerToGuess"));
    Cell[][] boxCellBoard = new Cell[boardSize][boardSize];
    Puzzle puzzle;
    Random random = new Random();

    public PuzzleService() {
        IntStream.rangeClosed(0, boardSize - 1).forEach(
                i -> IntStream.rangeClosed(0, boardSize - 1).forEach(j -> {
                    boxCellBoard[i][j] = new Cell();
                }));
        System.out.println("");
        puzzle = new Puzzle(boxCellBoard);
    }

    private int[][] intBoard9x9 = new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},

            {2, 3, 1, 5, 6, 4, 8, 9, 7},
            {5, 6, 4, 8, 9, 7, 2, 3, 1},
            {8, 9, 7, 2, 3, 1, 5, 6, 4},

            {3, 1, 2, 6, 4, 5, 9, 7, 8},
            {6, 4, 5, 9, 7, 8, 3, 1, 2},
            {9, 7, 8, 3, 1, 2, 6, 4, 5}
    };

    private int[][] intBoard6x6 = new int[][]{
            {1, 2, 3, 4, 5, 6},
            {4, 5, 6, 7, 8, 9},
            {7, 8, 9, 1, 2, 3},

            {2, 3, 1, 5, 6, 4},
            {5, 6, 4, 8, 9, 7},
            {8, 9, 7, 2, 3, 1},
    };

    private int[][] intBoard4x4 = new int[][]{
            {2, 1, 3, 4},
            {4, 3, 1, 2},

            {1, 2, 4, 3},
            {3, 4, 2, 1},
    };

    private Puzzle createFilledPuzzle9x9(Cell[][] boxCellBoard9x9) {
        puzzle.setCells(boxCellBoard9x9);
        return puzzle;
    }

    private Puzzle createFilledPuzzle6x6(Cell[][] boxCellBoard6x6) {
        puzzle.setCells(boxCellBoard6x6);
        return puzzle;
    }

    private Puzzle createFilledPuzzle4x4(Cell[][] boxCellBoard4x4) {
        puzzle.setCells(boxCellBoard4x4);
        return puzzle;
    }

    public Puzzle createRandomPuzzleBoardFilled9x9() throws Exception {
        Cell[][] board = createBoxCellDeafaultBoard(intBoard9x9);
        shuffleNumbers(board);
        shuffleRows(board);
        shuffleColumns(board);
        shuffleBoxRows(board);
        shuffleColumns(board);
        return createFilledPuzzle9x9(board);
    }

    public Puzzle createRandomPuzzleBoardFilled6x6() throws Exception {
        Cell[][] board = createBoxCellDeafaultBoard(intBoard6x6);
        shuffleNumbers(board);
        shuffleRows(board);
        shuffleColumns(board);
        shuffleBoxRows(board);
        shuffleColumns(board);
        return createFilledPuzzle6x6(board);
    }

    public Puzzle createRandomPuzzleBoardFilled4x4() throws Exception {
        Cell[][] board = createBoxCellDeafaultBoard(intBoard4x4);
        shuffleNumbers(board);
        shuffleRows(board);
        shuffleColumns(board);
        shuffleBoxRows(board);
        shuffleColumns(board);
        return createFilledPuzzle4x4(board);
    }

// 1 Find any filled board of sudoku. (use trivial ones will not affect final result)

    private Cell[][] createBoxCellDeafaultBoard(int[][] intBoard) {
        for (int i = 0; i < boxCellBoard.length; i++)
            for (int j = 0; j < boxCellBoard.length; j++) {
                boxCellBoard[i][j].setRow(i);
                boxCellBoard[i][j].setColumn(j);
                boxCellBoard[i][j].setNumber(intBoard[i][j]);
                boxCellBoard[i][j].setCellStatus(Status.INITIALLYFILLED);
            }
        return boxCellBoard;
    }

// 2 for each number from 1 to 9 (say num), (i.e 1, 2, 3, 5, 6, 7, 8, 9)
// take a random number from range [1 to 9], traverse the board, swap num with your random number.

    void shuffleNumbers(Cell[][] board) {
        for (int i = 1; i <= board.length; i++) {
            int randomNumber = random.nextInt(1, board.length);
            swapNumbers(i, randomNumber, board);
        }
    }

    private void swapNumbers(int number1, int number2, Cell[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[j][i].getNumber() == number1) {
                    board[j][i].setNumber(number2);
                } else if (board[j][i].getNumber() == number2) {
                    board[j][i].setNumber(number1);
                }
            }
        }
    }

// 3 Now shuffle rows. Take the first group of 3 rows , shuffle them ,
// and do it for all rows. (in 9 X 9 sudoku), do it for second group and as well as third.

    void shuffleRows(Cell[][] board) throws Exception {
        int blockNumber;
        for (int i = 0; i < board.length; i++) { // i=0????
            if (board.length == 9) {
                int randomNumber = random.nextInt(3);
                blockNumber = i / 3;
                swapRows(i, blockNumber * 3 + randomNumber, board);
            } else if (board.length == 6) {
                int randomNumber = random.nextInt(2);
                blockNumber = i / 2;
                swapRows(i, blockNumber * 2 + randomNumber, board);
            } else if (board.length == 4) {
                int randomNumber = random.nextInt(1);
                blockNumber = i / 2;
                swapRows(i, blockNumber * 2 + randomNumber, board);
            } else throw new Exception("Wrong size of the board");
        }
    }

    void swapRows(int row1, int row2, Cell[][] board) {
        Cell[] row = board[row1];
        board[row1] = board[row2];
        board[row2] = row;
    }

// 4 Swap columns, again take block of 3 columns , shuffle them, and do it for all 3 blocks

    void shuffleColumns(Cell[][] board) throws Exception {
        int blockNumber;
        for (int i = 0; i < board.length; i++) {
            if (board.length == 9) {
                int ranNum = random.nextInt(3);
                blockNumber = i / 3;
                swapCols(i, blockNumber * 3 + ranNum, board);
            } else if (board.length == 6) {
                int ranNum = random.nextInt(2);
                blockNumber = i / 2;
                swapCols(i, blockNumber * 2 + ranNum, board);
            } else if (board.length == 4) {
                int ranNum = random.nextInt(2);
                blockNumber = i / 2;
                swapCols(i, blockNumber * 2 + ranNum, board);
            } else throw new Exception("Wrong size of the board");
        }
    }

    void swapCols(int column1, int column2, Cell[][] board) {
        Cell columnValue;
        for (int i = 0; i < board.length; i++) {
            columnValue = board[i][column1];
            board[i][column1] = board[i][column2];
            board[i][column2] = columnValue;
        }
    }

// 5 swap the row blocks itself (ie 3X3 or 2x2 blocks)

    void shuffleBoxRows(Cell[][] board) {
        if (board.length == 9) {
            for (int i = 0; i < 3; i++) {
                int randomNumber = random.nextInt(3);
                swapBoxRows(i, randomNumber, board);
            }
        } else if (board.length == 6) {
            for (int i = 0; i < 2; i++) {
                int randomNumber = random.nextInt(2);
                swapBoxRows(i, randomNumber, board);
            }
        } else if (board.length == 4) {
            for (int i = 0; i < 2; i++) {
                int randomNumber = random.nextInt(1);
                swapBoxRows(i, randomNumber, board);
            }
        }
    }

    void swapBoxRows(int row1, int row2, Cell[][] board) {
        if (board.length == 6 || board.length == 9){
        for (int i = 0; i < 3; i++) {
            swapRows(row1 * 3 + i, row2 * 3 + i, board);
        }} else if (board.length == 4) {
            for (int i = 0; i < 2; i++) {
                swapRows(row1 * 2 + i, row2 * 2 + i, board);
            }
        }
    }

// 6 do the same for columns, swap blockwise

    void shuffleBoxColumns(Cell[][] board) {
        if (board.length == 6 || board.length == 9) {
            for (int i = 0; i < 3; i++) {
                int randomNumber = random.nextInt(3);
                swapBoxColumns(i, randomNumber, board);
            }
        } else if (board.length == 4) {
            for (int i = 0; i < 2; i++) {
                int randomNumber = random.nextInt(2);
                swapBoxColumns(i, randomNumber, board);
            }
        }
    }

    private void swapBoxColumns(int column1, int column2, Cell[][] board) {
        for (int i = 0; i < 3; i++) {
            swapCols(column1 * 3 + i, column2 * 3 + i, board);
        }
    }

    public void deleteNumbersFromCells(int numbersOfCellsToDelete, Cell[][] board) {

    }
}
