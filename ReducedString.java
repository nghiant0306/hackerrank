import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// https://www.hackerrank.com/challenges/reduced-string/problem
public class ReducedString {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String s = bufferedReader.readLine();
		System.out.println(superReducedString(s));
	}

    static String superReducedString(String s) {
    	int n = s.length();
    	if (n == 0) {
    		return "Empty String";
    	}
    		
    	String temp = "";
    	boolean nextRun = false;
    	
    	for (int i = 0; i < n; i++) {
    		if (i == n - 1) {
    			temp += s.substring(i, i+1);
    			break;
    		}
    		
    		if (s.charAt(i) == s.charAt(i + 1)) {
    			i++;
    			nextRun = true;
    		} else {
    			temp += s.substring(i, i+1);
    		}
    	}
    	
    	if(nextRun) {
    		return superReducedString(temp);
    	} else {
    		return temp;
    	}
    	
    }
}
