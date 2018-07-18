package com.summerproject.messenger.net;

import com.summerproject.messenger.pgp.PGPEncodedData;

import java.io.Serializable;

public class Data implements Serializable {
    private PGPEncodedData pgpEncodedData;
    private String message;
    private String username;

    public Data(PGPEncodedData pgpEncodedData) {
        this.pgpEncodedData = pgpEncodedData;
        this.message = message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public PGPEncodedData getPgpEncodedData() {
        return pgpEncodedData;
    }

    public void setPgpEncodedData(PGPEncodedData pgpEncodedData) {
        this.pgpEncodedData = pgpEncodedData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
