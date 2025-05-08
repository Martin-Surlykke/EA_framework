package com.ea_framework.Filehandlers;

import com.ea_framework.Problems.BitStringProblem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BitStringFileHandler {


    public static BitStringProblem Parse(String filePath) throws IOException {
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);

        String bitString = br.readLine();
        boolean [] bitArray = new boolean[bitString.length()];
        for (int i = 0; i < bitString.length(); i++) {
            bitArray[i] = bitString.charAt(i) == '1';
        }
        int length = bitString.length();
        return new BitStringProblem(bitArray, length);
    }


}
