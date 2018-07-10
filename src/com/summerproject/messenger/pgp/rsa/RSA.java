package com.summerproject.messenger.pgp.rsa;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class RSA {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public void generateKeys(int bitCount) {
        Random rnd = new Random();
        int i1 = rnd.nextInt(100000) + 1;
        byte[] password = new byte[i1];
        rnd.nextBytes(password);

        generateKeys(bitCount, new String(password));
    }

    public void generateKeys(int bitCount, String password)  {
        BigInteger publicExponenta = new BigInteger(Integer.toString(65537));
        BigInteger n;

        int seed = 0;
        int seed2 = 0;
        try {
            seed = new Integer(String.valueOf(new BigInteger(Util.hashSha256(password)).mod(BigInteger.valueOf(Integer.MAX_VALUE))));
            seed2 = new Integer(String.valueOf(new BigInteger(Util.hashSha256(password.toUpperCase())).mod(BigInteger.valueOf(Integer.MAX_VALUE))));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger p = Util.generateBigPrime(bitCount / 2, seed); //new BigInteger(bitCount, 0, new Random());
        BigInteger q = Util.generateBigPrime(bitCount / 2, seed2);//new BigInteger(bitCount, 0, new Random().nextInt(10000000));

        n = p.multiply(q);

        BigInteger phiFunction = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        BigInteger privateExponenta = Util.modInverse(publicExponenta, phiFunction);

        publicKey = new PublicKey(publicExponenta, n);
        privateKey = new PrivateKey(privateExponenta, n);
    }

    public byte[] mac(String message) throws NoSuchAlgorithmException {
        byte[] messageHash = Util.hashSha256(message);
        BigInteger messageHashInteger = new BigInteger(messageHash);
        messageHashInteger = messageHashInteger.abs();
        BigInteger sign = Util.modPow(messageHashInteger, privateKey.getD(), privateKey.getN());
        return sign.toByteArray();
    }

    public boolean checkMac(String message, byte[] sign) throws NoSuchAlgorithmException {
        BigInteger signInteger = new BigInteger(sign);
        BigInteger h = signInteger.modPow(publicKey.getE(), publicKey.getN());
        byte[] messageHash = Util.hashSha256(message);
        BigInteger messageHashInteger = new BigInteger(messageHash);
        messageHashInteger = messageHashInteger.abs();
        if (messageHashInteger.compareTo(h) == 0)
            return true;
        else
            return false;
    }

    public byte[] encode(byte[] arr) {
        BigInteger res = new BigInteger(arr);
        res = Util.modPow(res, publicKey.getE(), publicKey.getN());

        return res.toByteArray();
    }



    public byte[] decode(byte[] arr) {
        BigInteger res = new BigInteger(arr);
        res = Util.modPow(res, privateKey.getD(), privateKey.getN());
        return res.toByteArray();
    }

    public BigInteger encode(BigInteger in) {
        return Util.modPow(in, publicKey.getE(), publicKey.getN());
    }
    public BigInteger decode(BigInteger in) {
        return Util.modPow(in, privateKey.getD(), privateKey.getN());
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
