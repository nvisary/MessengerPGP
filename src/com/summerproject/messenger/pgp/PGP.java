package com.summerproject.messenger.pgp;

import com.summerproject.messenger.pgp.hash.Ripemd160;
import com.summerproject.messenger.pgp.idea.IDEA;
import com.summerproject.messenger.pgp.rsa.PrivateKey;
import com.summerproject.messenger.pgp.rsa.PublicKey;
import com.summerproject.messenger.pgp.rsa.RSA;
import com.summerproject.messenger.pgp.rsa.Util;
import com.summerproject.messenger.pgp.zip.ZipUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

public class PGP {
    private PrivateKey privateRSAkey;
    private PublicKey publicRSAkey;
    private PublicKey publicReceiverKey;
    private static int PGP_KEYS_BIT_COUNT = 2048;
    private String userSecretPassword = "qwerty";
    public PGPEncodedData encode(byte[] inputData) throws IOException {
        System.out.println("1 - zip input data");
        byte[] zipInputData = ZipUtil.zip(inputData, "data.txt");

        System.out.println("2 - RSA generate keys");
        RSA rsa = new RSA();
        rsa.generateKeys(PGP_KEYS_BIT_COUNT, userSecretPassword);
        privateRSAkey = rsa.getPrivateKey();
        publicRSAkey = rsa.getPublicKey();

        System.out.println("3 - create sessionKey");
        BigInteger sessionKey = Util.generateBigPrime(1024, (int) System.currentTimeMillis());

        System.out.println("4 - encoding zip data with IDEA");
        IDEA idea = new IDEA();
        byte[] encoded = idea.encode(zipInputData, sessionKey);

        System.out.println("5 - sign encoded data");
        String dataHash = Ripemd160.hash(encoded);
        byte[] sign = rsa.mac(dataHash.getBytes());

        System.out.println("6 - encoding session key with RSA and receiver public key");
        BigInteger encodedSessionKey = rsa.encode(sessionKey);

        PGPEncodedData pgpEncodedData = new PGPEncodedData(encoded, sign, encodedSessionKey, publicRSAkey);
        return pgpEncodedData;
    }

    public byte[] decode(PGPEncodedData pgpEncodedData) throws IOException {
        System.out.println("1 - check sign");
        RSA rsa = new RSA();
        rsa.setPublicKey(pgpEncodedData.getSenderPublicKey());
        String dataHash = Ripemd160.hash(pgpEncodedData.getEncodedData());
        boolean yes = rsa.checkMac(dataHash.getBytes(), pgpEncodedData.getSign());
        System.out.println(yes);
        return new byte[0];
    }

    public void generatePGPKeys() {
        RSA rsa = new RSA();
        rsa.generateKeys(PGP_KEYS_BIT_COUNT, userSecretPassword);
        publicRSAkey = rsa.getPublicKey();
        privateRSAkey = rsa.getPrivateKey();
    }

    public void setUserPassword(String userPassword) {
        this.userSecretPassword = userPassword;
    }

    public String getUserPassword() {
        return this.userSecretPassword;
    }

    public void setPublicReceiverKey(PublicKey publicReceiverKey) {
        this.publicReceiverKey = publicReceiverKey;
    }

    public PublicKey getPublicSenderKey() {
        return publicRSAkey;
    }

    public static void main(String[] args) throws IOException {
        PGP pgp2 = new PGP();
        pgp2.setUserPassword("how are you");
        pgp2.generatePGPKeys();

        PGP pgp = new PGP();
        pgp.setPublicReceiverKey(pgp2.getPublicSenderKey());
        PGPEncodedData pgpEncodedData = pgp.encode("hello world!".getBytes());

        byte[] decodedData = pgp2.decode(pgpEncodedData);

    }
}
