package com.summerproject.messenger.pgp;

import com.summerproject.messenger.pgp.hash.Ripemd160;
import com.summerproject.messenger.pgp.idea.IDEA;
import com.summerproject.messenger.pgp.rsa.PrivateKey;
import com.summerproject.messenger.pgp.rsa.PublicKey;
import com.summerproject.messenger.pgp.rsa.RSA;
import com.summerproject.messenger.pgp.rsa.Util;
import com.summerproject.messenger.pgp.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.util.Arrays;

public class PGP {
    private PrivateKey privateRSAkey;
    private PublicKey publicRSAkey;
    private static int PGP_KEYS_BIT_COUNT = 2048;
    private static String zipFileName = "tmp.zip";
    private String userSecretPassword = "qwerty";
    public void encode(byte[] inputData) throws IOException {
        //System.out.println("1 - zip");

        //ZipUtil.zip(inputData, "data.txt", zipFileName);

        System.out.println("2 - RSA generate keys");
        RSA rsa = new RSA();
        rsa.generateKeys(PGP_KEYS_BIT_COUNT, userSecretPassword);

        System.out.println("3 - getting sign");

        IDEA idea = new IDEA();
        BigInteger SessionKey = Util.generateBigPrime(128, (int) System.currentTimeMillis());
        byte[] encoded = idea.encode(inputData, SessionKey);
        byte[] decoded = idea.decode(encoded, SessionKey);


        String dataHash = Ripemd160.hash(inputData); //change to loadFile(zipFileName)
        byte[] sign = rsa.mac(dataHash.getBytes());


        privateRSAkey = rsa.getPrivateKey();
        publicRSAkey = rsa.getPublicKey();
    }

    private byte[] loadFile(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] result = Files.readAllBytes(file.toPath());
        return result;
    }

    public static void main(String[] args) throws IOException {
        PGP pgp = new PGP();
        pgp.encode("hello world!".getBytes());
    }
}
