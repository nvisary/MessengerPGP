package com.summerproject.messenger.pgp.rsa;

import java.math.BigInteger;

public class PrivateKey {
    private BigInteger d;
    private BigInteger n;

    public PrivateKey(BigInteger d, BigInteger n) {
        this.d = d;
        this.n = n;
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
}
