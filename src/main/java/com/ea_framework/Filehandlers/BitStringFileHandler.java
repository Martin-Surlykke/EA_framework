package com.ea_framework.Filehandlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BitStringFileHandler {

    public static boolean [] readFile(String filePath) throws IOException {
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);

        String bitString = br.readLine();
        boolean [] bitArray = new boolean[bitString.length()];
        for (int i = 0; i < bitString.length(); i++) {
            bitArray[i] = bitString.charAt(i) == '1';
        }
        return bitArray;

    }


}
