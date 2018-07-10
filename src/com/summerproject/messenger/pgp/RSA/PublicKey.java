package com.summerproject.messenger.pgp.RSA;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class PublicKey {
    private BigInteger e;
    private BigInteger n;

    public PublicKey(BigInteger e, BigInteger n) {
        this.e = e;
        this.n = n;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(n.toString());
        return sb.toString();
    }
}
