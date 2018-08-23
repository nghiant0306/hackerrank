import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;


public class ConnectedGrid {
	static boolean[][] visited;
	
    // Complete the connectedCell function below.
    static int connectedCell(int[][] matrix, int row, int col) {
    	visited = new boolean[row][col];
    	// using for test list of region
    	ArrayList<Integer> result = new ArrayList<Integer>();
    	int max = 0;
    	
    	for (int i = 0; i < row; i++) {
    		for (int j = 0; j < col; j++) {
    			if(matrix[i][j] == 1 && !visited[i][j]) {
    				int temp = bfs(matrix, row, col, new Node(i, j));
    				result.add(temp);
    				if (temp > max) max = temp;
    			}
    		}
    	}
    	
    	for (int i = 0; i < result.size(); i++) {
    		System.err.println(result.get(i));
    	}
    	return max;
    }

    
    static int bfs(int[][] matrix, int r, int c, Node startNode){
        Queue<Node> open = new LinkedList<Node>();
        ArrayList<Node> closed = new ArrayList<Node>();
        int count = 0;
        
        open.add(startNode);
        visited[startNode.row][startNode.col] = true;
        
        Node currentNode = null;
        
        while(!open.isEmpty()){
            currentNode = open.poll();
            count++;
            
            closed.add(currentNode);
            
            ArrayList<Node> childs = getChilds(matrix, currentNode, r, c);
            for (Node temp : childs) {
                if (!closed.contains(temp)) {                  
                    open.add(temp);                    
                }
            }
           
        }
        
        return count;
    }

	static class Node {
	    public int row = -1;
	    public int col = -1; 
	    
	    Node(int _row, int _col) {
	        row = _row;
	        col = _col;
	    }
	}
	
    static ArrayList<Node> getChilds(int[][] matrix, Node pos, int r, int c) {
        ArrayList<Node> childs = new ArrayList<Node>();
        
        // Pos: if (1,1) has all its neighbors not visited, (0,1), (1,0), (1,2), (2,1) then,
        if(pos.row != 0) {
        	if(!visited[pos.row-1][pos.col] && (matrix[pos.row-1][pos.col] != 0)) {
        		childs.add(new Node(pos.row-1, pos.col));
        		visited[pos.row-1][pos.col] = true;
        	}
        	if((pos.col != 0) && !visited[pos.row-1][pos.col-1] && (matrix[pos.row-1][pos.col-1] != 0)) {
                childs.add(new Node(pos.row-1, pos.col-1));
                visited[pos.row-1][pos.col-1] = true;        		
        	}
            if((pos.col+1 != c) && !visited[pos.row-1][pos.col+1] && (matrix[pos.row-1][pos.col+1] != 0)) {
                childs.add(new Node(pos.row-1, pos.col+1));
                visited[pos.row-1][pos.col+1] = true;
            }        	
        }
        // (1,0)
        if (pos.col != 0) {
        	if (!visited[pos.row][pos.col-1] && (matrix[pos.row][pos.col-1] != 0)) {
        		childs.add(new Node(pos.row, pos.col-1));
        		visited[pos.row][pos.col-1] = true;
        	}       	
        }        
        // (1,2)
        if((pos.col+1 != c) && !visited[pos.row][pos.col+1] && (matrix[pos.row][pos.col+1] != 0)) {
            childs.add(new Node(pos.row, pos.col+1));
            visited[pos.row][pos.col+1] = true;
        }
        // (2,1)
        if (pos.row+1 != r) {
        	if (!visited[pos.row+1][pos.col] && (matrix[pos.row+1][pos.col] != 0)) {
        		childs.add(new Node(pos.row+1, pos.col));
        		visited[pos.row+1][pos.col] = true;
        	}
        	if((pos.col != 0) && !visited[pos.row+1][pos.col-1] && (matrix[pos.row+1][pos.col-1] != 0)) {
                childs.add(new Node(pos.row+1, pos.col-1));
                visited[pos.row+1][pos.col-1] = true;        		
        	}
            if((pos.col+1 != c) && !visited[pos.row+1][pos.col+1] && (matrix[pos.row+1][pos.col+1] != 0)) {
                childs.add(new Node(pos.row+1, pos.col+1));
                visited[pos.row+1][pos.col+1] = true;
            }            
        }
        return childs;
    }

	
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int m = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[][] matrix = new int[n][m];

        for (int i = 0; i < n; i++) {
            String[] matrixRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < m; j++) {
                int matrixItem = Integer.parseInt(matrixRowItems[j]);
                matrix[i][j] = matrixItem;
            }
        }

        int result = connectedCell(matrix, n, m);

        //bufferedWriter.write(String.valueOf(result));
        //bufferedWriter.newLine();

        //bufferedWriter.close();

        scanner.close();
    }
}
