package com.summerproject.messenger.pgp.zip;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    public static void zip(byte[] inputData, String name, String outputName) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(outputName))) {
            ZipEntry entry = new ZipEntry(name);
            zout.putNextEntry(entry);

            zout.write(inputData);
            zout.closeEntry();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unzip(FileInputStream fileInputStream) {
        try (ZipInputStream zin = new ZipInputStream(fileInputStream)) {
            ZipEntry entry = zin.getNextEntry();
            String name = entry.getName();
            FileOutputStream fout = new FileOutputStream(name);
            for (int b = zin.read(); b != -1; b = zin.read()) {
                fout.write(b);
            }
            fout.flush();
            zin.closeEntry();
            fout.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        String name = "README.md";
        zip("hello world".getBytes(), "new.txt", "out.zip");
        //unzip(new FileInputStream("output.zip"));
    }
}
