package com.kowalczyk.sudoku.service;

import com.kowalczyk.sudoku.model.Cell;
import com.kowalczyk.sudoku.model.Puzzle;
import com.kowalczyk.sudoku.model.SystemProperties;
import com.kowalczyk.sudoku.model.enums.CellStatus;
import com.kowalczyk.sudoku.model.enums.PuzzleStatus;

import java.util.*;
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
                    boxCellBoard[i][j].setCellStatus(CellStatus.TOFILL);
                }));
        System.out.println("");
        puzzle = new Puzzle(boxCellBoard, PuzzleStatus.NOTCOMPLETED);
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

    public Puzzle createPuzzle9x9(Cell[][] cellBoard9x9) {
        puzzle.setCells(cellBoard9x9);
        return puzzle;
    }

    public Puzzle createPuzzle6x6(Cell[][] cellBoard6x6) {
        puzzle.setCells(cellBoard6x6);
        return puzzle;
    }

    public Puzzle createPuzzle4x4(Cell[][] cellBoard4x4) {
        puzzle.setCells(cellBoard4x4);
        return puzzle;
    }

    public Puzzle createRandomPuzzleBoardFilled9x9() throws Exception {
        Cell[][] board = createCellDeafaultBoard(intBoard9x9);
        shuffleNumbers(board);
        shuffleRows(board);
        shuffleColumns(board);
        shuffleBoxRows(board);
        shuffleBoxColumns(board);
        return createPuzzle9x9(board);
    }

    public Puzzle createRandomPuzzleBoardFilled6x6() throws Exception {
        Cell[][] board = createCellDeafaultBoard(intBoard6x6);
        shuffleNumbers(board);
        shuffleRows(board);
        shuffleColumns(board);
        shuffleBoxRows(board);
        shuffleColumns(board);
        return createPuzzle6x6(board);
    }

    public Puzzle createRandomPuzzleBoardFilled4x4() throws Exception {
        Cell[][] board = createCellDeafaultBoard(intBoard4x4);
        shuffleNumbers(board);
        shuffleRows(board);
        shuffleColumns(board);
        shuffleBoxRows(board);
        shuffleColumns(board);
        return createPuzzle4x4(board);
    }

// 1 Find any filled board of sudoku. (use trivial ones will not affect final result)

    private Cell[][] createCellDeafaultBoard(int[][] intBoard) {
        for (int i = 0; i < boxCellBoard.length; i++)
            for (int j = 0; j < boxCellBoard.length; j++) {
                boxCellBoard[i][j].setRow(i);
                boxCellBoard[i][j].setColumn(j);
                boxCellBoard[i][j].setNumber(intBoard[i][j]);
                boxCellBoard[i][j].setCellStatus(CellStatus.INITIALLYFILLED);
            }
        return boxCellBoard;
    }

// 2 for each number from 1 to 9 (say num), (i.e 1, 2, 3, 5, 6, 7, 8, 9)
// take a random number from range [1 to 9], traverse the board, swap num with your random number.

    private void shuffleNumbers(Cell[][] board) {
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

    private void shuffleRows(Cell[][] board) throws Exception {
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
                int randomNumber = random.nextInt(2);
                blockNumber = i / 2;
                swapRows(i, blockNumber * 2 + randomNumber, board);
            } else throw new Exception("Wrong size of the board");
        }
    }

    private void swapRows(int row1, int row2, Cell[][] board) {
        Cell[] row = board[row1];
        board[row1] = board[row2];
        board[row2] = row;
    }

// 4 Swap columns, again take block of 3 columns , shuffle them, and do it for all 3 blocks

    private void shuffleColumns(Cell[][] board) throws Exception {
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

    private void swapCols(int column1, int column2, Cell[][] board) {
        Cell columnValue;
        for (int i = 0; i < board.length; i++) {
            columnValue = board[i][column1];
            board[i][column1] = board[i][column2];
            board[i][column2] = columnValue;
        }
    }

// 5 swap the row blocks itself (ie 3X3 or 2x2 blocks)

    private void shuffleBoxRows(Cell[][] board) {
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
                int randomNumber = random.nextInt(2);
                swapBoxRows(i, randomNumber, board);
            }
        }
    }

    private void swapBoxRows(int row1, int row2, Cell[][] board) {
        if (board.length == 6 || board.length == 9) {
            for (int i = 0; i < 3; i++) {
                swapRows(row1 * 3 + i, row2 * 3 + i, board);
            }
        } else if (board.length == 4) {
            for (int i = 0; i < 2; i++) {
                swapRows(row1 * 2 + i, row2 * 2 + i, board);
            }
        }
    }

// 6 do the same for columns, swap blockwise

    private void shuffleBoxColumns(Cell[][] board) {
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

// 7 methods for testing correctness of numbers and deleting numbers from cells

    public void deleteNumbersFromCells(Cell[][] board) {
        int[][] cellsDeleted = new int[board.length][board.length];
        int iterator = 0;
        while (iterator < cellsForPlayerToGuess) {
            Integer row = random.nextInt(board.length);
            Integer column = random.nextInt(board.length);
            if (cellsDeleted[row][column] == 0) {
                cellsDeleted[row][column] = 1;
                board[row][column] = new Cell();
                board[row][column].setRow(row);
                board[row][column].setColumn(column);
                board[row][column].setCellStatus(CellStatus.TOFILL);
                iterator++;
            }
        }
    }

    private boolean rowCheck(int row, Cell[][] board) {
        List<Integer> rowCheck = new ArrayList<>();
        List<Integer> rowNumbers = new ArrayList<>();
        for (int number = 1; number <= board.length; number++) {
            rowCheck.add(number);
            rowNumbers.add(board[row][number - 1].getNumber());
        }
        Collections.sort(rowNumbers);
        return rowNumbers.equals(rowCheck);
    }

    private boolean inputRowCheck(int row, int number, Cell[][] board) {
        boolean rowTest = false;
        for (int column = 0; column < board.length; column++) {
            if (number != board[row][column].getNumber()) {
                rowTest = true;
            } else {
                rowTest = false;
                break;
            }
        }
        return rowTest;
    }

    private boolean columnCheck(int column, Cell[][] board) {
        List<Integer> columnCheck = new ArrayList<>();
        List<Integer> columnNumbers = new ArrayList<>();
        for (int number = 1; number <= board.length; number++) {
            columnCheck.add(number);
            columnNumbers.add(board[number - 1][column].getNumber());
        }
        Collections.sort(columnNumbers);
        return columnNumbers.equals(columnCheck);
    }

    private boolean inputColumnCheck(int column, int number, Cell[][] board) {
        boolean columnTest = false;
        for (int row = 0; row < board.length; row++) {
            if (number != board[row][column].getNumber()) {
                columnTest = true;
            } else {
                columnTest = false;
                break;
            }
        }
        return columnTest;
    }

    public boolean isPuzzleFilledCorrectly(Cell[][] board) {
        boolean rowTest = false;
        boolean columnTest = false;
        for (int row = 0; row < board.length; row++) {
            //           for (int column = 0; column < board.length; column++) {
            rowTest = rowCheck(row, board);
            if (!rowTest) {
                break;
            }
        }
        if (rowTest) {
            System.out.println("Puzzle is filled correctly");
            puzzle.setPuzzleStatus(PuzzleStatus.RIGHTNUMBERS);
        } else {
            System.out.println("Puzzle is filled wrong");
            puzzle.setPuzzleStatus(PuzzleStatus.WRONGNUMERS);
        }
        return (rowTest);
    }

    public boolean isPlayerInputCorrect(int number, int row, int column, Cell[][] board) {
        boolean rowTest = false;
        boolean columnTest = false;
        rowTest = inputRowCheck(row, number, board);
        columnTest = inputColumnCheck(column, number, board);
        if (rowTest && columnTest) {
            System.out.println("Cell is filled correctly");
            board[row][column].setCellStatus(CellStatus.CORRECTLYFILLED);
        } else {
            System.out.println("Cell is filled wrong");
            puzzle.setPuzzleStatus(PuzzleStatus.WRONGNUMERS);
            board[row][column].setCellStatus(CellStatus.WRONGLYFILLED);
        }
        return (rowTest && columnTest);
    }

    private boolean isBoardFilled(Cell[][] board) {
        for (int row = 0; row < board.length; row++)
            for (int column = 0; column < board.length; column++) {
                if (!board[row][column].getCellStatus().equals(CellStatus.TOFILL))
                    return true;
            }
        return false;
    }

    public boolean isPuzzleCompleted(Cell[][] board) {
        return isPuzzleFilledCorrectly(board) && isBoardFilled(board);
    }

    public void putNumberIntoBoard(Cell[][] board) {
        Scanner scanner = new Scanner(System.in);
        int number, row, column;
        //    do {
        System.out.println("Chose row: ");
        row = scanner.nextInt();
        System.out.println("Chose column: ");
        column = scanner.nextInt();
        System.out.println("Chose number: ");
        number = scanner.nextInt();
        if (isPlayerInputCorrect(number, row, column, board) && number > 0 && number <= board.length) {
            board[row][column].setNumber(number);
            board[row][column].setCellStatus(CellStatus.CORRECTLYFILLED);
        }
        else {
//            board[row][column].setNumber(0);
            System.out.println("Wrong number!");
        }
    }
    public void deleteCellNumber(Cell[][] board){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chose row: ");
        int row = scanner.nextInt();
        System.out.println("Chose column: ");
        int column = scanner.nextInt();
        if (row < board.length && column < board.length && !board[row][column].getCellStatus().equals(CellStatus.INITIALLYFILLED)){
            board[row][column].setNumber(0);
            board[row][column].setCellStatus(CellStatus.TOFILL);
        }else {
            System.out.println("Wrong cell!");
        }
    }
//TODO method for changing CELLSTATUS for whole row and column
    //TODO method for changing PUZZLESTATUS
    //TODO or block incorrect input
}