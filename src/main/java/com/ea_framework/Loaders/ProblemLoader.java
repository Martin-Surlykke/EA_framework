package com.ea_framework.Loaders;

import com.ea_framework.Problems.Problem;

import java.io.File;
import java.io.IOException;

public interface ProblemLoader {
    boolean isValid(File file);
    Problem load(File file) throws IOException;
}
