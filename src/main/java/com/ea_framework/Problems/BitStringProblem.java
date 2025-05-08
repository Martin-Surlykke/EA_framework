package com.ea_framework.Problems;

public record BitStringProblem (
        boolean [] defaultBitString,

        String name,

        int length
) implements Problem {


    @Override
    public String getName() {
        return name;
    }
}
