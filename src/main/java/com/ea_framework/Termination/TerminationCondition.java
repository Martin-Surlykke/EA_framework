package com.ea_framework.Termination;

import com.ea_framework.Algorithms.Algorithm;

public interface TerminationCondition {
    boolean shouldTerminate(int iteration, Algorithm algorithm);
}
