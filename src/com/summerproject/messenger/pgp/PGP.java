package com.summerproject.messenger.pgp;

import java.io.FileInputStream;
import java.util.zip.ZipEntry;

public class PGP {
    public void encode(byte[] inputData) {
        ZipUtil.zip(inputData, "data.txt", "tmp.zip");
        String dataHash = Ripemd160.hash(inputData);
    }

    public static void main(String[] args) {
        PGP pgp = new PGP();
        pgp.encode("hello world!".getBytes());
    }
}
