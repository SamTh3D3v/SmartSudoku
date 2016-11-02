package com.ubo.elhamer.smartsudokuelhamer;

/**
 * Created by oussa on 10/31/2016.
 */

public enum Difficulty {
    Easy(45),Medium(50),Hard(55);

    private final int id;
    Difficulty(int id) { this.id = id; }
    public int getValue() { return id; }
}