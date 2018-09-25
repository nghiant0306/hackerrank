import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

public class MostSequenceWord {
	static String[] mostCommonWords(String text, int n) {
		String[] result = new String[n];
		Arrays.fill(result, " ");
		HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

		ArrayList<String> arrWords = getWordList(text);

		addWordMap(arrWords, wordMap);

		Comparator<Entry<String, Integer>> sortType = new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
				Integer value1 = obj1.getValue();
				Integer value2 = obj2.getValue();

				if (value1 == value2) {
					return obj1.getKey().compareTo(obj2.getKey());
				} else {
					return (value2 - value1);
				}
			}
		};

		ArrayList<Entry<String, Integer>> list_entries = new ArrayList<Entry<String, Integer>>(wordMap.entrySet());
		Collections.sort(list_entries, sortType);

		int index = 0;
		for (Entry<String, Integer> item : list_entries) {
			if (index < n) {
				result[index] = item.getKey();
				index++;
			}
		}
		return result;
	}

	public static ArrayList<String> getWordList(String text) {
		int len = text.length();
		ArrayList<String> arrWords = new ArrayList<String>();

		String temp = "";
		for (int i = 0; i < len; i++) {
			char x = text.charAt(i);
			if (isCharacter(x)) {
				temp += String.valueOf(x);
			} else {
				if (temp != "") {
					arrWords.add(temp);
					temp = "";
				}
			}
		}

		if (temp != "") {
			arrWords.add(temp);
		}
		return arrWords;
	}

	public static boolean isCharacter(char x) {
		if ((x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z')) {
			return true;
		}
		return false;
	}

	public static void addWordMap(ArrayList<String> arrWords, HashMap<String, Integer> wordMap) {
		for (String strTemp : arrWords) {
			String word = strTemp.toLowerCase();
			if (wordMap.containsKey(word)) {
				wordMap.put(word, wordMap.get(word) + 1);
			} else {
				wordMap.put(word, 1);
			}
		}
	}

	public static void main(String[] args) {
		// String text = "He is a pupil";
		// int n = 1;
		String text = "He is a is pupil, and and she is a student";
		int n = 10;
		String[] result = mostCommonWords(text, n);
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i] + "*");
		}
	}
}
