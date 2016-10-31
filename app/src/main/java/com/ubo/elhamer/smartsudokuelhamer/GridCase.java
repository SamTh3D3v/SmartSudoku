package com.ubo.elhamer.smartsudokuelhamer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oussa on 10/31/2016.
 */

public class GridCase {

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    private int val;

    public ArrayList<Integer> getPossibleValues() {
        return possibleValues;
    }

    public void setPossibleValues(ArrayList<Integer> possibleValues) {
        this.possibleValues = possibleValues;
    }

    private ArrayList<Integer> possibleValues;

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
