package com.ea_framework.FitnessFunctions;

public interface Fitness <T, V extends Comparable<V>> {
    V evaluate(T solution);


}
