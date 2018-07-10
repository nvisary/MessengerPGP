package com.summerproject.messenger.pgp.RSA;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // write your code here

        RSA rsa = new RSA();
        rsa.generateKeys(2048, "password");
        PublicKey publicKey = rsa.getPublicKey();

        String text = "hello";
        byte[] arr = text.getBytes();
        RSA rsa2 = new RSA();
        rsa2.setPublicKey(publicKey);
        byte[] res = rsa2.encode(arr);
        System.out.println(new String(res));
        System.out.println("PUBLIC:  " + publicKey);
        byte[] res2 = rsa.decode(res);
        System.out.println("res:" + new String(res2));


        //RSA rsa3 = new RSA();
        //rsa3.generateKeys(128);

        /*String message = "I am Alice";

        RSA alice = new RSA();
        RSA bob = new RSA();

        alice.generateKeys(1024);
        byte[] sign = alice.mac(message);
        PublicKey publicKey = alice.getPublicKey();

        bob.setPublicKey(publicKey);
        boolean isAlice = bob.checkMac(message, sign);
        System.out.println(isAlice);*/

    }
}
