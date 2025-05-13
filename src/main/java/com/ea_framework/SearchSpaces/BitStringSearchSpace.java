package com.ea_framework.SearchSpaces;

public class BitStringSearchSpace implements SearchSpace {

    @Override
    public boolean isValidSolution(Object solution) {
        return solution instanceof boolean[];
    }

    @Override
    public String stringify(Object solution) {
        if (!(solution instanceof boolean[] bits)) return "Invalid input";
        StringBuilder sb = new StringBuilder();
        for (boolean bit : bits) sb.append(bit ? '1' : '0');
        return sb.toString();
    }
}
