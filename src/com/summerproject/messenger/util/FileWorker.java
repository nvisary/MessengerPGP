package com.summerproject.messenger.util;

import com.summerproject.messenger.model.Model;
import com.summerproject.messenger.pgp.rsa.PrivateKey;
import com.summerproject.messenger.pgp.rsa.PublicKey;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

public class FileWorker {
    public static String publicKey;
    public static String privateKey;
    public static String username;
    public static void writeProperties(String fileName, PublicKey publicKey, PrivateKey privateKey, String username) {
        Properties properties = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream(fileName);

            properties.setProperty("publicKey", publicKey.toString());
            properties.setProperty("privateKey", privateKey.toString());
            properties.setProperty("username", username);

            properties.store(output, null);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void readProperties(String fileName) {
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(fileName);
            properties.load(input);

            publicKey = properties.getProperty("publicKey");
            privateKey = properties.getProperty("privateKey");
            username = properties.getProperty("username");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static byte[] loadFile(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] result = Files.readAllBytes(file.toPath());
        return result;
    }

    public static void saveFile(String fileName, byte[] data) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(data);
        fos.close();
    }
}
