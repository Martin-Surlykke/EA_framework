package com.ea_framework.SearchSpaces;

public class Graph2DSearchSpace implements SearchSpace {

    @Override
    public boolean isValidSolution(Object solution) {
        if (!(solution instanceof int[] arr)) return false;

        int nodeCount = arr.length;
        boolean[] visited = new boolean[nodeCount];
        for (int node : arr) {
            if (node < 0 || node >= nodeCount || visited[node]) return false;
            visited[node] = true;
        }
        return true;
    }

    @Override
    public String stringify(Object solution) {
        if (!(solution instanceof int[] arr)) return "Invalid input";
        StringBuilder sb = new StringBuilder();
        for (int i : arr) sb.append(i).append(" ");
        return sb.toString();
    }
}