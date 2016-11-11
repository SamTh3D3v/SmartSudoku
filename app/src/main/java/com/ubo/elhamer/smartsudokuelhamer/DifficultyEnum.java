package com.ubo.elhamer.smartsudokuelhamer;

/**
 * Created by Elhamer Oussama on 10/31/2016.
 */

public enum DifficultyEnum {
    Easy(45),Medium(50),Hard(55);

    private final int id;
    DifficultyEnum(int id) { this.id = id; }
    public int getValue() { return id; }
}
