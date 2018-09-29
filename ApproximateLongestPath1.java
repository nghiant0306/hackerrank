//https://www.hackerrank.com/challenges/walking-the-approximate-longest-path/problem

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.Random;

public class ApproximateLongestPath1 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int v = input.nextInt();
        int e = input.nextInt();

        long startTime = System.nanoTime();
        Graph g = new Graph(v);
        g.startTime = startTime;

        for (int i = 0; i < e; i++) {
            g.addEdge(input.nextInt(), input.nextInt());
        }
        
        g.solve();

        input.close();
    }

    public static void debug(Object test) {
        if (false) { // false -> true change this code for debug
            System.out.println(test);
        }
    }
    
    public static class Graph {
        int v;
        long startTime;
        HashMap<Integer, HashSet<Integer>> adjList; // parent(id), childs (id)

        public boolean[] visited;
        public Integer maxLen = 0;
        ArrayList<Integer> maxPath;
        HashSet<Integer> lucky = new HashSet<>();

        Graph(int v) {
            this.v = v;
            adjList = new HashMap<>();
            visited = new boolean[v];
            for (int i = 0; i < v; i++) {
                adjList.put(i, new HashSet<Integer>());
            }
        }

        public void addEdge(int v1, int v2) {
            int a = v1 - 1;
            int b = v2 - 1;
            this.adjList.get(a).add(b);
            this.adjList.get(b).add(a);
        }

        public int[] getMinMax() {
            int[] result = new int[2];
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for(Map.Entry<Integer, HashSet<Integer>> entry : adjList.entrySet()) {
                int size = entry.getValue().size();
                if(size < min) {
                    min = size;
                    result[0] = entry.getKey();
                }
                if(size > max) {
                    max = size;
                    result[1] = entry.getKey();
                }                
            }
            return result;
        }
        
        public int getNextChild(int parent) {
            int minChild = -1;
            int minRank = Integer.MAX_VALUE;

            for (Integer child : this.adjList.get(parent)) {
                if (!visited[child]) {
                    Integer rank = this.adjList.get(child).size();
                    if (rank < minRank) {
                        minRank = rank;
                        minChild = child;
                    }
                }
            }

            return minChild;
        }

        public void solve() {
            // find from minmax item
            int[] minmax = getMinMax();
            
            getMaxPath(minmax[0]);
            
            getMaxPath(minmax[1]);

            int count = 0;
            while(maxLen != v && count++ < 500) {   // 500 times using lucky number
                int current = new Random().nextInt(v);
                getMaxPath(current);
            }
            
            System.out.println(maxLen);
            for (int i = 0; i < maxLen; i++) {
                System.out.print((maxPath.get(i) + 1) + " ");
            }            
        }

        public void getMaxPath(int current) {

            if(lucky.contains(current)) return;
            
            lucky.add(current);
            int len = 0;
            ArrayList<Integer> path = new ArrayList<>();
            Arrays.fill(visited, false);
            
            while (current != -1) {
                path.add(current);
                this.visited[current] = true;
                len++;
                
                current = getNextChild(current);
            }

            if (len > maxLen) {
                maxLen = len;
                maxPath = new ArrayList<>(path);
            }
        }
        
        public String toString() {
            StringBuffer s = new StringBuffer("");
            for (int i = 0; i < v; i++) {
                s.append(i + ": " + this.adjList.get(i) + "\n");
            }
            return s.toString();
        }
    }
}
