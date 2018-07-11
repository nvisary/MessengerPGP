package com.summerproject.messenger.pgp.rsa;

import java.io.Serializable;
import java.math.BigInteger;

public class PublicKey implements Serializable {
    private BigInteger e = new BigInteger("65537");
    private BigInteger n;

    public PublicKey(BigInteger n) {
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
