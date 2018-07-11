package com.summerproject.messenger.pgp.zip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    public static byte[] zip(byte[] inputData, String name) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipOutputStream zout = new ZipOutputStream(bos)) {
            ZipEntry entry = new ZipEntry(name);
            zout.putNextEntry(entry);

            zout.write(inputData);
            zout.closeEntry();
            zout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }

    public static byte[] unzip(byte[] data) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipInputStream zin = new ZipInputStream(byteArrayInputStream)) {
            ZipEntry entry = zin.getNextEntry();

            for (int b = zin.read(); b != -1; b = zin.read()) {
                byteArrayOutputStream.write(b);
            }
            byteArrayOutputStream.flush();
            zin.closeEntry();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String name = "README.md";
        //zip("hello world".getBytes(), "new.txt", "out.zip");
        //unzip(new FileInputStream("output.zip"));
    }
}
