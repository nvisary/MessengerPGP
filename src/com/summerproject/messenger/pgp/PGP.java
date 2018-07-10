package com.summerproject.messenger.pgp;

import com.summerproject.messenger.pgp.hash.Ripemd160;
import com.summerproject.messenger.pgp.rsa.PrivateKey;
import com.summerproject.messenger.pgp.rsa.PublicKey;
import com.summerproject.messenger.pgp.rsa.RSA;
import com.summerproject.messenger.pgp.zip.ZipUtil;

import java.io.IOException;
import java.util.Arrays;

public class PGP {
    private PrivateKey privateRSAkey;
    private PublicKey publicRSAkey;
    private static int PGP_KEYS_BIT_COUNT = 2048;
    private String userSecretPassword = "qwerty";
    public void encode(byte[] inputData) throws IOException {
        System.out.println("1 - zip");

        ZipUtil.zip(inputData, "data.txt", "tmp.zip");
        System.out.println("2 - RSA generate keys");
        RSA rsa = new RSA();
        rsa.generateKeys(PGP_KEYS_BIT_COUNT, userSecretPassword);

        System.out.println("3 - getting sign");
        String dataHash = Ripemd160.hash(inputData);
        byte[] sign = rsa.mac(dataHash.getBytes());


        privateRSAkey = rsa.getPrivateKey();
        publicRSAkey = rsa.getPublicKey();
    }

    public static void main(String[] args) throws IOException {
        PGP pgp = new PGP();
        pgp.encode("hello world!".getBytes());
    }
}
