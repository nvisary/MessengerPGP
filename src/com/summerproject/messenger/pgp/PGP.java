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

public class PGP {
    private PrivateKey privateRSAkey;
    private PublicKey publicRSAkey;
    private PublicKey publicReceiverKey;
    private RSA curRSA;
    private static int PGP_KEYS_BIT_COUNT = 1024;
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
        BigInteger sessionKey = Util.generateBigPrime(PGP_KEYS_BIT_COUNT / 4, (int) System.currentTimeMillis());

        System.out.println("4 - encoding zip data with IDEA");
        IDEA idea = new IDEA();
        byte[] encoded = idea.encode(zipInputData, sessionKey); //inputData

        System.out.println("5 - sign encoded data");
        String dataHash = Ripemd160.hash(encoded);
        byte[] sign = rsa.mac(dataHash.getBytes());

        System.out.println("6 - encoding session key with RSA and receiver public key");
        RSA newRsa = new RSA();
        newRsa.setPublicKey(publicReceiverKey);
        BigInteger encodedSessionKey = newRsa.encode(sessionKey);

        PGPEncodedData pgpEncodedData = new PGPEncodedData(encoded, sign, encodedSessionKey, publicRSAkey);
        return pgpEncodedData;
    }

    public byte[] decode(PGPEncodedData pgpEncodedData) throws IOException {
        byte[] result = new byte[0];

        System.out.println("1 - check sign");
        RSA rsa = new RSA();
        rsa.setPublicKey(pgpEncodedData.getSenderPublicKey());
        String dataHash = Ripemd160.hash(pgpEncodedData.getEncodedData());
        if (rsa.checkMac(dataHash.getBytes(), pgpEncodedData.getSign())) {
            System.out.println("sign is true");
            System.out.println("2 - decoding session key");
            BigInteger encodedSessionKey = pgpEncodedData.getEncodedSessionKey();


            BigInteger decodedSessionKey = curRSA.decode(encodedSessionKey);
            IDEA idea = new IDEA();
            System.out.println("3 - decoding zip data");
            byte[] decodedZipData = idea.decode(pgpEncodedData.getEncodedData(), decodedSessionKey);
            System.out.println("4 - unzip data");
            result = ZipUtil.unzip(decodedZipData);
        }
        return result;
    }

    public void generatePGPKeys() {
        curRSA = new RSA();
        System.out.println("generate... use password: " + userSecretPassword);
        curRSA.generateKeys(PGP_KEYS_BIT_COUNT, userSecretPassword);
        publicRSAkey = curRSA.getPublicKey();
        privateRSAkey = curRSA.getPrivateKey();
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

    public PublicKey getPublicPGPKey() {
        return publicRSAkey;
    }

    public PrivateKey getPrivatePGPKey() {
        return privateRSAkey;
    }

    public void setPrivatePGPkey(PrivateKey privateRSAkey) {
        this.privateRSAkey = privateRSAkey;
    }

    public void setPublicPGPkey(PublicKey publicRSAkey) {
        this.publicRSAkey = publicRSAkey;
    }

    public static void main(String[] args) throws IOException {
        PGP pgp2 = new PGP();
        pgp2.setUserPassword("how are you");
        pgp2.generatePGPKeys();

        PGP pgp = new PGP();
        pgp.setPublicReceiverKey(pgp2.getPublicPGPKey());
        PGPEncodedData pgpEncodedData = pgp.encode("hello world!".getBytes());

        byte[] decodedData = pgp2.decode(pgpEncodedData);
        System.out.println(new String(decodedData));

        /*RSA first = new RSA();
        RSA sec = new RSA();

        sec.generateKeys(512, "hello");
        first.setPublicKey(sec.getPublicKey());
        byte[] encoded = first.encode("secret".getBytes());

        System.out.println(new String(sec.decode(encoded)));*/
    }
}
