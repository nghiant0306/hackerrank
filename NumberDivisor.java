public class NumberDivisor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long n = 1000;

		System.out.println(numOfDivisors(n));

	}

	static int numOfDivisors(long n) {
		int max = (int) Math.sqrt(n);
		int count = 0;
		
		for (int i = 1; i <= max; i++) {
			if (n % i == 0) {
				count = count + 2;
			}
		}

		if (n % max == 0 && n == max * max) {
			count = count - 1;
		}

		return count;
	}
}
