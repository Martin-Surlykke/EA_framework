package com.ea_framework.Filehandlers;

import com.ea_framework.Coordinate;
import com.ea_framework.Loaders.ProblemLoader;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Problems.TSP2DProblem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class TSPFileHandler implements ProblemLoader {

    @Override
    public TSP2DProblem load(InputStream in) throws IOException {
        Scanner sc = new Scanner(in);
        sc.useLocale(Locale.US);

        String name = readValue(sc.nextLine());
        String comment = readValue(sc.nextLine());
        String type = readValue(sc.nextLine());
        String edgeType = readValue(sc.nextLine());

        while (sc.hasNextLine()) {
            if (sc.nextLine().trim().equals("NODE_COORD_SECTION")) break;
        }

        List<Coordinate> coords = new ArrayList<>();
        double maxX = Integer.MIN_VALUE;
        double maxY = Integer.MIN_VALUE;
        double minX = Integer.MAX_VALUE;
        double minY = Integer.MAX_VALUE;

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equals("EOF")) break;

            Scanner lineScanner = new Scanner(line);
            lineScanner.useLocale(Locale.US);
            int index = lineScanner.nextInt() - 1;
            double x = lineScanner.nextDouble();
            double y = lineScanner.nextDouble();
            coords.add(new Coordinate(index, x, y));

            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
        }

        int[] permutation = getIndexList(coords);
        double[][] coordsArray = getCoordArray(coords);
        double[][] distMatrix = computeDistanceMatrix(coordsArray);

        return new TSP2DProblem(name, comment, type, edgeType, coords, distMatrix, permutation, maxX, maxY, minX, minY);
    }

    @Override
    public Problem createFromSize(int size) {
        Random rand = new Random();

        List<Coordinate> coords = new ArrayList<>();
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;

        for (int i = 0; i < size; i++) {
            double x = rand.nextDouble() * 1000;
            double y = rand.nextDouble() * 1000;

            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);

            coords.add(new Coordinate(i, x, y));
        }

        double[][] coordsArray = getCoordArray(coords);
        double[][] distMatrix = computeDistanceMatrix(coordsArray);
        int[] permutation = getIndexList(coords);

        String name = "Random TSP " + size;
        return new TSP2DProblem(
                name, "Random instance", "TSP2D", "EUC_2D",
                coords, distMatrix, permutation, maxX, maxY, minX, minY
        );
    }

    private static String readValue(String line) {
        return line.substring(line.indexOf(":") + 1).trim();
    }

    private static int[] getIndexList(List<Coordinate> coords) {
        int[] indexList = new int[coords.size()];
        for (int i = 0; i < coords.size(); i++) {
            indexList[i] = coords.get(i).id();
        }
        return indexList;
    }

    private static double[][] getCoordArray(List<Coordinate> coords) {
        double[][] coordsArray = new double[coords.size()][2];
        for (int i = 0; i < coords.size(); i++) {
            coordsArray[i][0] = coords.get(i).x();
            coordsArray[i][1] = coords.get(i).y();
        }
        return coordsArray;
    }

    private static double[][] computeDistanceMatrix(double[][] coords) {
        int n = coords.length;
        double[][] d = new double[n][n];
        for (int i = 0; i < n; i++) {
            double x1 = coords[i][0], y1 = coords[i][1];
            for (int j = 0; j < n; j++) {
                double x2 = coords[j][0], y2 = coords[j][1];
                d[i][j] = Math.hypot(x2 - x1, y2 - y1);
            }
        }
        return d;
    }
}