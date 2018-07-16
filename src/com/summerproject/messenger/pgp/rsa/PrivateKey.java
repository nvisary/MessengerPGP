package com.summerproject.messenger.pgp.rsa;

import java.math.BigInteger;

public class PrivateKey {
    private BigInteger d;
    private BigInteger n;

    public PrivateKey(BigInteger d, BigInteger n) {
        this.d = d;
        this.n = n;
    }

    public PrivateKey(String privateKeyString) {
        if (privateKeyString == null) {
            throw new NullPointerException("constructor parameter string is null");
        }

        int id = privateKeyString.indexOf(":");
        d = new BigInteger(privateKeyString.substring(0, id));
        n = new BigInteger(privateKeyString.substring(id + 1, privateKeyString.length()));
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    @Override
    public String toString() {
        StringBuilder sb =new StringBuilder(d.toString());
        sb.append(":").append(n.toString());

        return sb.toString();
    }
}
