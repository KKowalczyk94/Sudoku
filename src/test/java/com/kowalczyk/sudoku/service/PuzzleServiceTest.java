package com.kowalczyk.sudoku.service;

import com.kowalczyk.sudoku.model.Cell;
import com.kowalczyk.sudoku.model.Puzzle;
import com.kowalczyk.sudoku.model.SystemProperties;
import com.kowalczyk.sudoku.model.enums.CellStatus;
import com.kowalczyk.sudoku.model.enums.PuzzleStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PuzzleServiceTest {
    private PuzzleService puzzleService = new PuzzleService();
    private int boardSize = Integer.parseInt(SystemProperties.PROPS.getProperty("sudokuBoard.size"));
    private int cellsForPlayerToGuess = Integer.parseInt(SystemProperties.PROPS.getProperty("sudokuBoard.cellsForPlayerToGuess"));

    private static Stream<Arguments> providedBoard() {
        return Stream.of(
                Arguments.of(new Cell[9][9], 9),
                Arguments.of(new Cell[6][6], 6),
                Arguments.of(new Cell[4][4], 4)
        );
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

    @ParameterizedTest
    @MethodSource("providedBoard")
    public void testCreatePuzzle(Cell[][] providedBoard, int expectedSize) {
        //given
        //when
        puzzleService.createPuzzle9x9(providedBoard);
        //then
        Assertions.assertEquals(expectedSize, providedBoard.length);
    }

    @Test
    public void testCreateRandomPuzzleBoardFilled9x9() throws Exception {
        //given
        List<Integer> listOfExpectedNumbers = new ArrayList<>();
        List<List<Integer>> listOfRowNumbers = new ArrayList<>();
        //when
        Puzzle puzzle = puzzleService.createRandomPuzzleBoardFilled9x9();
        for (int i = 0; i < 9; i++) {
            listOfExpectedNumbers.add(i + 1);
            List<Integer> numbers = Stream.of(puzzle.getCells()[i]).map(Cell::getNumber)
                    .sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            listOfRowNumbers.add(numbers);
        }
        //then
        Assertions.assertEquals(listOfRowNumbers.get(0), listOfExpectedNumbers);
    }

    @Test
    public void testCreateRandomPuzzleBoardFilled6x6() throws Exception {
        //given
        List<Integer> listOfExpectedNumbers = new ArrayList<>();
        List<List<Integer>> listOfRowNumbers = new ArrayList<>();
        //when
        Puzzle puzzle = puzzleService.createRandomPuzzleBoardFilled6x6();
        for (int i = 0; i < 6; i++) {
            listOfExpectedNumbers.add(i + 1);
            List<Integer> numbers = Stream.of(puzzle.getCells()[i]).map(Cell::getNumber)
                    .sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            listOfRowNumbers.add(numbers);
        }
        //then
        Assertions.assertEquals(listOfRowNumbers.get(0), listOfExpectedNumbers);
    }

    @Test
    public void testCreateRandomPuzzleBoardFilled4x4() throws Exception {
        //given
        List<Integer> listOfExpectedNumbers = new ArrayList<>();
        List<List<Integer>> listOfRowNumbers = new ArrayList<>();
        //when
        Puzzle puzzle = puzzleService.createRandomPuzzleBoardFilled4x4();
        for (int i = 0; i < 4; i++) {
            listOfExpectedNumbers.add(i + 1);
            List<Integer> numbers = Stream.of(puzzle.getCells()[i]).map(Cell::getNumber)
                    .sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            listOfRowNumbers.add(numbers);
        }
        //then
        Assertions.assertEquals(listOfRowNumbers.get(0), listOfExpectedNumbers);
    }

    @Test
    public void testDeleteNumbersFromCells() throws Exception {
        //given
        Puzzle puzzle = puzzleService.createRandomPuzzleBoardFilled6x6();
        List<Integer> listOfDeletedCells = new ArrayList<>();
        //when
        puzzleService.deleteNumbersFromCells(puzzle.getCells());
        for(int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++){
                if (puzzle.getCells()[i][j].getNumber() == 0){
                    listOfDeletedCells.add(puzzle.getCells()[i][j].getNumber());
                }
            }
        //then
        Assertions.assertEquals(cellsForPlayerToGuess,listOfDeletedCells.size());
    }

    @Test
    public void testIsPuzzleFilledCorrectly() throws Exception {
        //given
        Cell[][] board = new Cell[9][9];
        //when
        puzzleService.createPuzzle9x9(board);
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++){
                board[i][j] = new Cell();
                board[i][j].setNumber(intBoard9x9[i][j]);
            }
        //then
        Assertions.assertTrue(puzzleService.isPuzzleFilledCorrectly(board)); //assertFalse?
    }

    @Test
    public void testInputRowCheck(){
        //given
        int row = 2, column = 0, number = 7;
        Cell[][] board = new Cell[9][9];
        //when
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++){
                board[i][j] = new Cell();
                board[i][j].setNumber(intBoard9x9[i][j]);
            }
        board[row][column].setNumber(0);
        //then
        Assertions.assertTrue(puzzleService.isPlayerInputCorrect(number,row,column,board));
    }

    @Test
    public void testIsPuzzleCompleted() {
        //given
        Cell[][] board = new Cell[9][9];
        //when
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++){
                board[i][j] = new Cell();
                board[i][j].setCellStatus(CellStatus.INITIALLYFILLED);
                board[i][j].setNumber(intBoard9x9[i][j]);
            }
        //then
        Assertions.assertTrue(puzzleService.isPuzzleCompleted(board));
    }
}