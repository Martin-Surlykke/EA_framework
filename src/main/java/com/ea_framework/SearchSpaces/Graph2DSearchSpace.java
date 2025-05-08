package com.ea_framework.SearchSpaces;

public class Graph2DSearchSpace implements SearchSpace<int []>{

    public Graph2DSearchSpace() {
    }

    @Override
    public boolean isValidSolution(int[] solution) {
        int nodeCount = solution.length;
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
