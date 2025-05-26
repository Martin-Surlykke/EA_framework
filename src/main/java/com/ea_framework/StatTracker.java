package com.ea_framework;

import com.ea_framework.Views.InfoViews.StatRecord;

public interface StatTracker {
    void onIteration(int iteration, Object solution, double fitness, StatRecord stat);
}
