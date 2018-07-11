package com.summerproject.messenger.pgp;

import com.summerproject.messenger.pgp.rsa.PublicKey;

import java.io.Serializable;
import java.math.BigInteger;

public class PGPEncodedData implements Serializable{
    private byte[] encodedData;
    private byte[] sign;
    private BigInteger encodedSessionKey;
    private PublicKey senderPublicKey;

    public PGPEncodedData(byte[] encodedData, byte[] sign, BigInteger encodedSessionKey, PublicKey senderPublicKey) {
        this.encodedData = encodedData;
        this.sign = sign;
        this.encodedSessionKey = encodedSessionKey;
        this.senderPublicKey = senderPublicKey;
    }

    public byte[] getEncodedData() {
        return encodedData;
    }

    public void setEncodedData(byte[] encodedData) {
        this.encodedData = encodedData;
    }

    public byte[] getSign() {
        return sign;
    }

    public void setSign(byte[] sign) {
        this.sign = sign;
    }

    public BigInteger getEncodedSessionKey() {
        return encodedSessionKey;
    }

    public void setEncodedSessionKey(BigInteger encodedSessionKey) {
        this.encodedSessionKey = encodedSessionKey;
    }

    public PublicKey getSenderPublicKey() {
        return senderPublicKey;
    }
}
