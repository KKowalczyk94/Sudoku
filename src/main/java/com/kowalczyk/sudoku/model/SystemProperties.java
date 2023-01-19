package com.kowalczyk.sudoku.model;

import com.kowalczyk.sudoku.Sudoku;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@UtilityClass
public class SystemProperties {
    public Properties PROPS;

    static {
        try (InputStream input = Sudoku.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPS = new Properties();
            if (Objects.isNull(input))
                System.out.println("Sorry, unable to find config.properties");
            else
                PROPS.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
