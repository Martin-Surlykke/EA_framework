package com.ea_framework.SearchSpaces;

public interface SearchSpace {
    boolean isValidSolution(Object solution);
    String stringify(Object solution);
}
