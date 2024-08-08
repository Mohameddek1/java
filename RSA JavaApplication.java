package javaapplication7;

import java.math.BigInteger;
import java.util.Random;

public class JavaApplication7 {

    public static void main(String[] args) {
        int numberOfPairs = 3;

        for (int i = 0; i < numberOfPairs; i++) {
            KeyPair keyPair = RSA.generateKeyPair();

            System.out.println("Key Pair " + (i + 1) + ":");
            System.out.println("Randomly Generated p: " + keyPair.getP());
            System.out.println("Randomly Generated q: " + keyPair.getQ());
            System.out.println("Totient (z): " + keyPair.getZ());
            System.out.println("Public Key (e, n): (" + keyPair.getPublicKey().getE() + ", " + keyPair.getPublicKey().getN() + ")");
            System.out.println("Private Key (d, n): (" + keyPair.getPrivateKey().getD() + ", " + keyPair.getPrivateKey().getN() + ")");

            int originalMessage = 42;
            System.out.println("Original Message: " + originalMessage);

            // Encrypt the message using the public key
            BigInteger encryptedMessage = RSA.encrypt(originalMessage, keyPair.getPublicKey());
            System.out.println("Encrypted Message: " + encryptedMessage);

            // Decrypt the message using the private key
            int decryptedMessage = RSA.decrypt(encryptedMessage, keyPair.getPrivateKey());
            System.out.println("Decrypted Message: " + decryptedMessage);

            System.out.println();
        }
    }
}

class RSA {

    static KeyPair generateKeyPair() {
        Random random = new Random();
        int p, q, n, z, d = 0, e;

        // Generate random prime numbers p and q
        p = generateRandomPrime(random);
        q = generateRandomPrime(random);

        n = p * q;
        z = (p - 1) * (q - 1);

        e = generatePublicKeyExponent(z, random);
        d = generatePrivateKeyExponent(e, z);

        PublicKey publicKey = new PublicKey(e, n);
        PrivateKey privateKey = new PrivateKey(d, n);

        return new KeyPair(publicKey, privateKey, p, q, z);
    }

    static int generateRandomPrime(Random random) {
        int prime;
        do {
            prime = random.nextInt(100) + 50;
        } while (!isPrime(prime));
        return prime;
    }

    static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    static int generatePublicKeyExponent(int z, Random random) {
        int e;
        do {
            e = random.nextInt(z - 2) + 2;
        } while (gcd(e, z) != 1);
        return e;
    }

    static int generatePrivateKeyExponent(int e, int z) {
        int d = 1;
        while ((d * e) % z != 1) {
            d++;
        }
        return d;
    }

    static int gcd(int e, int z) {
        if (e == 0) return z;
        else return gcd(z % e, e);
    }

    static BigInteger encrypt(int message, PublicKey publicKey) {
        BigInteger msg = BigInteger.valueOf(message);
        BigInteger e = BigInteger.valueOf(publicKey.getE());
        BigInteger n = BigInteger.valueOf(publicKey.getN());
        return msg.modPow(e, n);
    }

    static int decrypt(BigInteger encryptedMessage, PrivateKey privateKey) {
        BigInteger d = BigInteger.valueOf(privateKey.getD());
        BigInteger n = BigInteger.valueOf(privateKey.getN());
        BigInteger decrypted = encryptedMessage.modPow(d, n);
        return decrypted.intValue();
    }
}

class KeyPair {

    private final PublicKey publicKey;
    private final PrivateKey privateKey;
    private final int p;
    private final int q;
    private final int z;

    KeyPair(PublicKey publicKey, PrivateKey privateKey, int p, int q, int z) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.p = p;
        this.q = q;
        this.z = z;
    }

    int getP() {
        return p;
    }

    int getQ() {
        return q;
    }

    int getZ() {
        return z;
    }

    PublicKey getPublicKey() {
        return publicKey;
    }

    PrivateKey getPrivateKey() {
        return privateKey;
    }
}

class PublicKey {

    private final int e;
    private final int n;

    PublicKey(int e, int n) {
        this.e = e;
        this.n = n;
    }

    int getE() {
        return e;
    }

    int getN() {
        return n;
    }
}

class PrivateKey {

    private final int d;
    private final int n;

    PrivateKey(int d, int n) {
        this.d = d;
        this.n = n;
    }

    int getD() {
        return d;
    }

    int getN() {
        return n;
    }
}
