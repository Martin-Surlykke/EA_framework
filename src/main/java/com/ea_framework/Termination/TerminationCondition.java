package com.ea_framework.Termination;

import com.ea_framework.Algorithms.Algorithm;

import java.util.Map;

public interface TerminationCondition {
    boolean shouldTerminate(int iteration, Algorithm algorithm);

    void configure(Map<String, String> value);


}
