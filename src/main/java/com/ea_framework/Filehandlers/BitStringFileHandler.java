package com.ea_framework.Filehandlers;

import com.ea_framework.Loaders.ProblemLoader;
import com.ea_framework.Problems.BitStringProblem;
import com.ea_framework.Problems.Problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BitStringFileHandler implements ProblemLoader<BitStringProblem> {

    @Override
    public boolean isValid(InputStream inputStream) {
        return true;
    }

    @Override
    public Problem load(InputStream stream) throws IOException {
        String name = stream.toString();
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String bitString = br.readLine();
        boolean[] bitArray = new boolean[bitString.length()];

        for (int i = 0; i < bitString.length(); i++) {
            bitArray[i] = bitString.charAt(i) == '1';
        }

        int length = bitString.length();
        return new BitStringProblem(name, length, bitArray);
    }
}
