package com.summerproject.messenger.pgp;

import com.summerproject.messenger.pgp.hash.Ripemd160;
import com.summerproject.messenger.pgp.rsa.PrivateKey;
import com.summerproject.messenger.pgp.rsa.PublicKey;
import com.summerproject.messenger.pgp.rsa.RSA;
import com.summerproject.messenger.pgp.zip.ZipUtil;

public class PGP {
    private PrivateKey privateRSAkey;
    private PublicKey publicRSAkey;
    private static int PGP_KEYS_BIT_COUNT = 2048;
    public void encode(byte[] inputData) {
        ZipUtil.zip(inputData, "data.txt", "tmp.zip");
        String dataHash = Ripemd160.hash(inputData);

        RSA rsa = new RSA();
        rsa.generateKeys(PGP_KEYS_BIT_COUNT);
        privateRSAkey = rsa.getPrivateKey();
        publicRSAkey = rsa.getPublicKey();
    }

    public static void main(String[] args) {
        PGP pgp = new PGP();
        pgp.encode("hello world!".getBytes());
    }
}
