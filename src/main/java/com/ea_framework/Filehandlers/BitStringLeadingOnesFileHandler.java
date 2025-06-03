package com.ea_framework.Filehandlers;

import com.ea_framework.Loaders.ProblemLoader;
import com.ea_framework.Problems.BitStringLeadingOnesProblem;
import com.ea_framework.Problems.Problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BitStringLeadingOnesFileHandler implements ProblemLoader {
    @Override
    public Problem load(InputStream inputStream) throws IOException {
        String name = inputStream.toString();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String bitString = br.readLine();
        boolean[] bitArray = new boolean[bitString.length()];

        for (int i = 0; i < bitString.length(); i++) {
            bitArray[i] = bitString.charAt(i) == '1';
        }

        int length = bitString.length();
        return new BitStringLeadingOnesProblem(name, length, bitArray);
    }

    @Override
    public Problem createFromSize(int size) {
        boolean[] bitArray = new boolean[size];
        for (int i = 0; i < size; i++) {
            bitArray[i] = Math.random() < 0.5;
        }

        return new BitStringLeadingOnesProblem("RandomLeadingOnes" + size, size, bitArray);
    }
}
