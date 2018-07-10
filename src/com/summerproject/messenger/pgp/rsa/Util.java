package com.summerproject.messenger.pgp.rsa;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Util {
    private static boolean isSimpleDel(BigInteger candidate, int del) {
        if (candidate.mod(new BigInteger(Integer.toString(del))).compareTo(BigInteger.ZERO) == 0 ) {
            return true;
        }
        return false;
    }
    //Тест рабина-миллера
    public static BigInteger generateBigPrime(int countBits, int seed) {
        BigInteger candidate;
        while (true) {
            candidate = new BigInteger(countBits, new Random(seed));
            if (isSimpleDel(candidate, 3) || isSimpleDel(candidate, 5) || isSimpleDel(candidate, 7)
                    || isSimpleDel(candidate, 11) ) {
                seed++;
                continue;
            }
            int b = 0;
            int two = 2;
            int step = 1;

            BigInteger candidateMinusOne = candidate.subtract(BigInteger.ONE);
            while (true) {
                String st_str = Integer.toString((int) Math.pow(two, step));
                if (candidateMinusOne.mod(new BigInteger(st_str)).compareTo(BigInteger.ZERO) == 0) {
                    b++;
                    step++;
                } else {
                    break;
                }
            }
            BigInteger m = candidate.subtract(BigInteger.ONE).divide(new BigInteger(Integer.toString((int) Math.pow(2, b))));
            BigInteger a = new BigInteger(countBits - 1, new Random());

            int j = 0;
            BigInteger z = modPow(a, m, candidate);

            while (true) {
                if (j > 0 && z.compareTo(BigInteger.ONE) == 0) {
                    seed++;
                    break;

                }
                if (z.compareTo(BigInteger.ONE) == 0 || z.compareTo(candidateMinusOne) == 0) {
                    return candidate;
                }

                if (j < b && z.compareTo(candidateMinusOne) != 0) {
                    z = modPow(z, new BigInteger("2"), candidate);
                }
                if (j == b && z.compareTo(candidateMinusOne) != 0) {
                    seed++;
                    break;
                }
                j++;
            }


        }
    }

    public static BigInteger modPow(BigInteger base, BigInteger exponent, BigInteger mod) {
        BigInteger res = new BigInteger("1");
        if (exponent.compareTo(BigInteger.ZERO) < 0) {
            exponent = exponent.abs();
        }
        if (base.compareTo(BigInteger.ZERO) < 0) {
            base = base.abs();
        }
        if (mod.compareTo(BigInteger.ZERO) < 0) {
            mod = mod.abs();
        }

        while (exponent.compareTo(BigInteger.ZERO) > 0) {
            if (exponent.and(BigInteger.ONE).compareTo(BigInteger.ONE) == 0) {
                res = res.multiply(base).mod(mod);
            }
            base = base.multiply(base).mod(mod);
            exponent = exponent.shiftRight(1);
        }
        return res;
    }


    public static BigInteger modInverse(BigInteger a, BigInteger m) {
        BigInteger m0 = m;
        BigInteger y = BigInteger.ZERO;
        BigInteger x = BigInteger.ONE;

        if (m.equals(BigInteger.ONE))
            return BigInteger.ZERO;

        while (a.compareTo(BigInteger.ONE) > 0) {
            BigInteger q = a.divide(m);
            BigInteger t = m;
            m = a.mod(m);
            a = t;
            t = y;

            y = x.subtract(q.multiply(y));
            x = t;
        }


        if (x.compareTo(BigInteger.ONE) < 0)
            x = x.add(m0);

        return x;
    }

    public static byte[] hashSha256(String message) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] messageBytes = message.getBytes();
        return sha256.digest(messageBytes);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String str = "1";
        String str1 = "3";
        System.out.println(new String(hashSha256(str)));
        System.out.println(new String(hashSha256(str1)));
    }
}
