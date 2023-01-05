package com.kowalczyk.sudoku;

import com.kowalczyk.sudoku.helperClasses.SudokuFiller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Sudoku {
    public static void main(String[] args) {
        try (InputStream input = Sudoku.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (Objects.isNull(input)) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            prop.load(input);

            System.out.println(prop.getProperty("sudokuBoard.size"));
            System.out.println(prop.getProperty("sudokuBoard.cellsForPlayerToGuess"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
