package com.summerproject.messenger.model;

import com.summerproject.messenger.pgp.PGP;

import javax.jws.WebParam;

public class Model {
    private String userPassword;
    private PGP pgp;

    public Model() {

    }

    public void generatePgpKeys() {
        pgp = new PGP();
        pgp.setUserPassword(userPassword);
        pgp.generatePGPKeys();
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
