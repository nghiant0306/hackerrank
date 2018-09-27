// https://www.hackerrank.com/challenges/walking-the-approximate-longest-path/problem
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.Random;

public class ApproximateLongestPath {

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
        debug(g.toString());

        g.solve();

        input.close();
    }
    static int timeout = 4700;
    static int loopCount = 130;                 // try loop is small. In case loopCount = 150 -> case 20 will be false.
    public static void debug(Object test) {
        if (false) {                            // false -> true change this code for debug
            System.out.println(test);
        }
    }

    public static boolean isTimeOut(long startTime) {
        long endTime = System.nanoTime();
        long timeElapsed = (long) ((endTime - startTime) / Math.pow(10, 6));
        if (timeElapsed > timeout) {
            return true;
        }
        return false;

    }

    public static class Graph {
        int v;
        long startTime;
        HashMap<Integer, HashMap<Integer, Integer>> adjList; // parent(id) ->
                                                                // child (id,
                                                                // rank)

        public boolean[] visited;

        Graph(int v) {
            this.v = v;
            adjList = new HashMap<>();
            visited = new boolean[v];
            for (int i = 0; i < v; i++) {
                adjList.put(i, new HashMap<Integer, Integer>());
            }
        }

        public void addEdge(int v1, int v2) {
            int a = v1 - 1;
            int b = v2 - 1;

            // Add for node v1
            HashMap<Integer, Integer> currentNode = this.adjList.get(a);
            if (currentNode.containsKey(b)) {
                currentNode.put(b, currentNode.get(b) + 1);
            } else {
                currentNode.put(b, 1);
            }
            this.adjList.put(a, currentNode);

            // Add for node v2
            currentNode = this.adjList.get(b);
            if (currentNode.containsKey(a)) {
                currentNode.put(a, currentNode.get(a) + 1);
            } else {
                currentNode.put(a, 1);
            }
            this.adjList.put(b, currentNode);
        }

        public List<Entry<Integer, HashMap<Integer, Integer>>> sortMinVertex() {
            List<Entry<Integer, HashMap<Integer, Integer>>> listEntry = new ArrayList<Entry<Integer, HashMap<Integer, Integer>>>(
                    adjList.entrySet());

            Collections.sort(listEntry, new Comparator<Entry<Integer, HashMap<Integer, Integer>>>() {
                public int compare(Entry<Integer, HashMap<Integer, Integer>> obj1,
                        Entry<Integer, HashMap<Integer, Integer>> obj2) {
                    return obj1.getValue().size() - obj2.getValue().size();    // Problem: using size -> O(n)
                }
            });

            return listEntry;
        }

        public int getNextChild(int v) {
            this.visited[v] = true;

            int minChild = -1;
            int minRank = Integer.MAX_VALUE;

            for (Map.Entry<Integer, Integer> childs : this.adjList.get(v).entrySet()) {
                int v2 = childs.getKey();
                int r2 = childs.getValue();
                if (!visited[v2]) {
                    if (r2 < minRank) {
                        minRank = r2;
                        minChild = v2;
                    }
                }
            }

            return minChild;
        }

        public ArrayList<Integer> search(int currentNode) {
            ArrayList<Integer> path = new ArrayList<>();
            Arrays.fill(visited, false);

            while (currentNode != -1) {
                path.add(currentNode);

                // Check end time
                if (isTimeOut(startTime))
                    break;

                currentNode = getNextChild(currentNode);
                debug(currentNode);
            }

            return path;
        }

        public ArrayList<Integer> traversal(List<Entry<Integer, HashMap<Integer, Integer>>> minLstVertex) {
            ArrayList<Integer> maxPath = new ArrayList<>();
            ArrayList<Integer> path;
            int maxLen = Integer.MIN_VALUE;
            int count = 0;

            for (Entry<Integer, HashMap<Integer, Integer>> item : minLstVertex) {
                count++;
                path = search(item.getKey());

                int len = path.size();
                if (len > maxLen) {
                    maxLen = len;
                    maxPath = new ArrayList<>(path);      // Problem: Copy max path -> take time.
                }

                if (isTimeOut(startTime) || maxLen == v || count > loopCount) {
                    debug(isTimeOut(startTime));
                    debug(maxLen == v);
                    break;
                }
            }

            return maxPath;
        }

        public void solve() {
            List<Entry<Integer, HashMap<Integer, Integer>>> minLstVertex = sortMinVertex();

            ArrayList<Integer> maxPath = traversal(minLstVertex);

            int maxLen = maxPath.size();
            System.out.println(maxLen);
            for (int i = 0; i < maxLen; i++) {
                System.out.print((maxPath.get(i) + 1) + " ");
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
