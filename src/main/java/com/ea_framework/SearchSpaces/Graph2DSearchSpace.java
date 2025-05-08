package com.ea_framework.SearchSpaces;

public class Graph2DSearchSpace implements SearchSpace<int []>{

    private final int nodeCount;

    public Graph2DSearchSpace(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    @Override
    public boolean isValidSolution(int[] solution) {
        if (solution.length != nodeCount) {
            return false;
        }
        boolean[] visited = new boolean[nodeCount];
        for (int node : solution) {
            if (node < 0 || node >= nodeCount || visited[node]) {
                return false;
            }
            visited[node] = true;
        }
        return true;
    }

    @Override
    public String stringify(int[] solution) {
        StringBuilder sb = new StringBuilder();
        for (int i : solution) {
            sb.append(i).append(" ");
        }
        return sb.toString();
    }
}
