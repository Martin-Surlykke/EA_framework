package com.ea_framework.Mutation;

public abstract class BitStringMutationOperator implements MutationOperator<boolean[]> {
    public abstract void mutate(boolean[] solution);
}
