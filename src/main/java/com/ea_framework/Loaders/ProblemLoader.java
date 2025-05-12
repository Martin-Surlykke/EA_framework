package com.ea_framework.Loaders;

import com.ea_framework.Problems.Problem;

import java.io.IOException;
import java.io.InputStream;

public interface ProblemLoader<T> {
    boolean isValid(InputStream inputStream);
    Problem load(InputStream stream) throws IOException;
}
