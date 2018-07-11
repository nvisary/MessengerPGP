package com.summerproject.messenger.net;

import com.summerproject.messenger.pgp.PGPEncodedData;

import java.io.Serializable;

public class Data implements Serializable {
    private PGPEncodedData pgpEncodedData;
    private String message;

    public Data(PGPEncodedData pgpEncodedData, String message) {
        this.pgpEncodedData = pgpEncodedData;
        this.message = message;
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
