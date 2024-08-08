 import java.util.Random;

public class PrimeKeyGenerator {

    public static void main(String[] args) {
        int rangeStart = 50;
        int rangeEnd = 100;

        int p = generateRandomPrime(rangeStart, rangeEnd);
        int q;
        do {
            q = generateRandomPrime(rangeStart, rangeEnd);
        } while (q == p);  // Ensure q is different from p

        System.out.println("Randomly Generated Prime Numbers:");
        System.out.println("p: " + p);
        System.out.println("q: " + q);
    }

    public static int generateRandomPrime(int rangeStart, int rangeEnd) {
        Random random = new Random();
        int prime;

        do {
            prime = random.nextInt(rangeEnd - rangeStart + 1) + rangeStart;
        } while (!isPrime(prime));

        return prime;
    }

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}

