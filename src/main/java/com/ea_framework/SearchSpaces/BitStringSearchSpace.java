package com.ea_framework.SearchSpaces;

import java.util.Random;

public class BitStringSearchSpace implements SearchSpace<boolean[]> {

    private final int length;
    private final Random rand = new Random();

    public BitStringSearchSpace(int length) {
        this.length = length;
    }

    @Override
    public boolean isValidSolution(boolean[] solution) {
        return solution.length == length;
    }

    @Override
    public String stringify(boolean[] solution) {
        StringBuilder sb = new StringBuilder();
        for (boolean bit : solution) {
            sb.append(bit ? '1' : '0');
        }
        return sb.toString();
    }
}
