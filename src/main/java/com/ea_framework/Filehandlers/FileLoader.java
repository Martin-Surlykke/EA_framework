package com.ea_framework.Filehandlers;

import com.ea_framework.Problems.Problem;

import java.io.InputStream;

public interface FileLoader<P extends Problem> {
    P load(InputStream stream) throws Exception;
}