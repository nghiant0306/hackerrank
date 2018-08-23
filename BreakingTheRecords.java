import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

//https://www.hackerrank.com/challenges/breaking-best-and-worst-records/problem
class Game {
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getMinScore() {
		return minScore;
	}
	public void setMinScore(int minScore) {
		this.minScore = minScore;
	}
	public int getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}
	public int getMinCount() {
		return minCount;
	}
	public void setMinCount(int minCount) {
		this.minCount = minCount;
	}
	public int getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	
	private int score;
	private int minScore;
	private int maxScore;
	private int minCount = 0;
	private int maxCount = 0;
	
	public Game(int _score) {
		score = _score;
		minScore = _score;
		maxScore = _score;
	}

	public Game(Game cur, int _score) {
		score = _score;
		minScore = cur.getMinScore();
		maxScore = cur.getMaxScore();
		minCount = cur.getMinCount();
		maxCount = cur.getMaxCount();
	}
}

public class BreakingTheRecords {


	public static Game SetNextItem(Game cur, int _score) {
		Game result = new Game(cur, _score);

		// Check update minScore, minCount ??
		if (cur.getMinScore() > _score) {
			result.setMinScore(_score);
			result.setMinCount(cur.getMinCount() + 1);
		}
		
		// Check update maxScore, maxCount ??
		if (cur.getMaxScore() < _score) {
			result.setMaxScore(_score);
			result.setMaxCount(cur.getMaxCount() + 1);
		}
		
		return result;
	}
	
    // Complete the breakingRecords function below.
    public static int[] breakingRecords(int[] scores, int n) {
    	// init current value
    	Game curItem = new Game(scores[0]);
    	for (int i = 1; i < n; i++) {
    		curItem = SetNextItem(curItem, scores[i]);
    	}
    	
    	int[] result = {curItem.getMaxCount(), curItem.getMinCount()}; 
    	return result;

    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
    	System.out.println("start:");
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] scores = new int[n];

        String[] scoresItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int scoresItem = Integer.parseInt(scoresItems[i]);
            scores[i] = scoresItem;
        }
        
        int[] result = breakingRecords(scores, n);

        System.out.println("result:");
        for (int i = 0; i < result.length; i++) {
            //bufferedWriter.write(String.valueOf(result[i]));
        	System.out.println(String.valueOf(result[i]));
        }

        //bufferedWriter.newLine();

        //bufferedWriter.close();

        scanner.close();
    }
}
