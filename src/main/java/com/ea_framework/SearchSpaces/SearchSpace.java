package com.ea_framework.SearchSpaces;

public interface SearchSpace <T> {
    boolean isValidSolution(T solution);

    String stringify(T solution);

}
