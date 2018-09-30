//https://www.hackerrank.com/challenges/walking-the-approximate-longest-path/problem -> 70

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.Random;

public class ApproximateLongestPath2 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int v = input.nextInt();
        int e = input.nextInt();

        Graph g = new Graph(v);

        for (int i = 0; i < e; i++) {
            g.addEdge(input.nextInt(), input.nextInt());
        }
        
        g.solve();

        input.close();
    }
    
    public static class Graph {
        int v;
        HashMap<Integer, HashSet<Integer>> adjList; // parent(id), childs (id)

        Graph(int v) {
            this.v = v;
            adjList = new HashMap<>();

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

        public int getMinNode() {
            int result = 0;
            int min = Integer.MAX_VALUE;

            for(Map.Entry<Integer, HashSet<Integer>> entry : adjList.entrySet()) {
                int size = entry.getValue().size();
                if(size < min) {
                    min = size;
                    result = entry.getKey();
                }                
            }
            return result;
        }
        
        public int getNextChild(int parent) {
            int minChild = -1;
            int minRank = Integer.MAX_VALUE;

            for (Integer child : this.adjList.get(parent)) {
                Integer rank = this.adjList.get(child).size();
                if (rank < minRank) {
                    minRank = rank;
                    minChild = child;
                }
            }

            return minChild;
        }
        
        public void removeParent(int parent) {
        	this.adjList.remove(parent);
        	for (int current : adjList.keySet()) {
        		adjList.get(current).remove(parent);
        	}
        }

        public void solve() {

            ArrayList<Integer> maxPath = new ArrayList<>();
            int maxLen = 0;
            
            int current = getMinNode();
            
            while (true) {
            	maxPath.add(current);
                maxLen++;
                
                int next = getNextChild(current);
                removeParent(current);
                if (next == -1) {
                	break;
                }
                else {
                	current = next;
                }
            }
            
            System.out.println(maxLen);
            for (int i = 0; i < maxLen; i++) {
                System.out.print((maxPath.get(i) + 1) + " ");
            } 
        }
    }
}
