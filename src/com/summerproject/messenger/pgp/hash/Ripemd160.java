package com.summerproject.messenger.pgp.hash;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Ripemd160 {

    private static final int[] R1 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static final int[] R2 = {7, 4, 13, 1, 10, 6, 15, 3, 12, 0, 9, 5, 2, 14, 11, 8};
    private static final int[] R3 = {3, 10, 14, 4, 9, 15, 8, 1, 2, 7, 0, 6, 13, 11, 5, 12};
    private static final int[] R4 = {1, 9, 11, 10, 0, 8, 12, 4, 13, 3, 7, 15, 14, 5, 6, 2};
    private static final int[] R5 = {4, 0, 5, 9, 7, 12, 2, 10, 14, 1, 3, 8, 11, 6, 15, 13};
    private static final int[] R1_ = {5, 14, 7, 0, 9, 2, 11, 4, 13, 6, 15, 8, 1, 10, 3, 12};
    private static final int[] R2_ = {6, 11, 3, 7, 0, 13, 5, 10, 14, 15, 8, 12, 4, 9, 1, 2};
    private static final int[] R3_ = {15, 5, 1, 3, 7, 14, 6, 9, 11, 8, 12, 2, 10, 0, 4, 13};
    private static final int[] R4_ = {8, 6, 4, 1, 3, 11, 15, 0, 5, 12, 2, 13, 9, 7, 10, 14};
    private static final int[] R5_ = {12, 15, 10, 4, 1, 5, 8, 7, 6, 2, 13, 14, 0, 3, 9, 11};
    private static final int[] S1 = {11, 14, 15, 12, 5, 8, 7, 9, 11, 13, 14, 15, 6, 7, 9, 8};
    private static final int[] S2 = {7, 6, 8, 13, 11, 9, 7, 15, 7, 12, 15, 9, 11, 7, 13, 12};
    private static final int[] S3 = {11, 13, 6, 7, 14, 9, 13, 15, 14, 8, 13, 6, 5, 12, 7, 5};
    private static final int[] S4 = {11, 12, 14, 15, 14, 15, 9, 8, 9, 14, 5, 6, 8, 6, 5, 12};
    private static final int[] S5 = {9, 15, 5, 11, 6, 8, 13, 12, 5, 12, 13, 14, 11, 8, 5, 6};
    private static final int[] S1_ = {8, 9, 9, 11, 13, 15, 15, 5, 7, 7, 8, 11, 14, 14, 12, 6};
    private static final int[] S2_ = {9, 13, 15, 7, 12, 8, 9, 11, 7, 7, 12, 7, 6, 15, 13, 11};
    private static final int[] S3_ = {9, 7, 15, 11, 8, 6, 6, 14, 12, 13, 5, 14, 13, 13, 7, 5};
    private static final int[] S4_ = {15, 5, 8, 11, 14, 14, 6, 14, 6, 9, 12, 9, 12, 5, 15, 8};
    private static final int[] S5_ = {8, 5, 12, 9, 12, 5, 14, 6, 8, 13, 6, 5, 15, 13, 11, 11};

    public static int f(int x, int y, int z, int j) {

        if (j >= 0 && j <= 15)
            return x ^ y ^ z;
        if (j >= 16 && j <= 31)
            return (x & y) | ((~x) & z);
        if (j >= 32 && j <= 47)
            return (x | (~y)) ^ z;
        if (j >= 48 && j <= 63)
            return (x & z) | (y & (~z));
        if (j >= 64 && j <= 79)
            return x ^ (y | (~z));
        return -1;
    }

    private static int getS(int j, boolean isApostrophe) {
        if (j >= 0 && j <= 15)
            if (isApostrophe) {
                return S1_[j % 16];
            } else {
                return S1[j % 16];
            }
        if (j >= 16 && j <= 31)
            if (isApostrophe) {
                return S2_[j % 16];
            } else {
                return S2[j % 16];
            }
        if (j >= 32 && j <= 47)
            if (isApostrophe) {
                return S3_[j % 16];
            } else {
                return S3[j % 16];
            }

        if (j >= 48 && j <= 63)
            if (isApostrophe) {
                return S4_[j % 16];
            } else {
                return S4[j % 16];
            }

        if (j >= 64 && j <= 79)
            if (isApostrophe) {
                return S5_[j % 16];
            } else {
                return S5[j % 16];
            }
        return -1;
    }

    private static int getK(int j, boolean isApostrophe) {
        if (j >= 0 && j <= 15)
            if (isApostrophe) {
                return 0x50A28BE6;
            } else {
                return 0x00000000;
            }
        if (j >= 16 && j <= 31)
            if (isApostrophe) {
                return 0x5C4DD124;
            } else {
                return 0x5A827999;
            }
        if (j >= 32 && j <= 47)
            if (isApostrophe) {
                return 0x6D703EF3;
            } else {
                return 0x6ED9EBA1;
            }

        if (j >= 48 && j <= 63)
            if (isApostrophe) {
                return 0x7A6D76E9;
            } else {
                return 0x8F1BBCDC;
            }

        if (j >= 64 && j <= 79)
            if (isApostrophe) {
                return 0x00000000;
            } else {
                return 0xA953FD4E;
            }
        return -1;
    }

    private static int getR(int j, boolean isApostrophe) {
        if (j >= 0 && j <= 15)
            if (isApostrophe) {
                return R1_[j % 16];
            } else {
                return R1[j % 16];
            }
        if (j >= 16 && j <= 31)
            if (isApostrophe) {
                return R2_[j % 16];
            } else {
                return R2[j % 16];
            }
        if (j >= 32 && j <= 47)
            if (isApostrophe) {
                return R3_[j % 16];
            } else {
                return R3[j % 16];
            }

        if (j >= 48 && j <= 63)
            if (isApostrophe) {
                return R4_[j % 16];
            } else {
                return R4[j % 16];
            }

        if (j >= 64 && j <= 79)
            if (isApostrophe) {
                return R5_[j % 16];
            } else {
                return R5[j % 16];
            }
        return -1;
    }

    private static String intToHex(int i) {

        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < 4; j++) {
            sb.append(Integer.toString((i >> 4) & 0xf, 16)).append(Integer.toString(i & 0xf, 16));
            i >>= 8;
        }
        return sb.toString();
    }

    private static int[][] getWords(byte[] bytes) {
        int[][] words = new int[bytes.length / 64][16];
        int id = 0;
        for (int i = 0; i < bytes.length / 64; i++) {
            for (int j = 0; j < 16; j++) {
                words[i][j] = ((int) bytes[id]) & 0xff
                        | (((int) bytes[id + 1]) & 0xff) << 8
                        | (((int) bytes[id + 2]) & 0xff) << 16
                        | (((int) bytes[id + 3]) & 0xff) << 24;
                id += 4;
            }
        }
        return words;
    }

    public static String hash(byte[] in) throws IOException {
        /*Шаг 1 - добавление недостающих битов */

        int countBytes = in.length;
        long countBits = countBytes * 8;
        int byteCountMod = countBytes % 64;

        int padding;
        if (byteCountMod < 56)
            padding = 64 - byteCountMod;
        else
            padding = (64 - byteCountMod) + 64;

        int newLen = in.length + padding;
        byte[] out = new byte[newLen];

        for (int i = 0; i < in.length; i++) {
            out[i] = in[i];
        }

        out[in.length] = (byte) 0x80;//0b10000000

        for (int i = out.length + 1; i < (newLen - 8); i++) {
            out[i] = 0;
        }

        /*Шаг 2 - Добавление длины сообщения*/
        for (int i = 0; i < 8; i++) {
            out[newLen - 8 + i] = (byte) (countBits & 0xff);
            countBits >>= 8;
        }

        /*Шаг 3 - Константы*/
        /*Шаг 4 - Выполнение алгоритма хеширования*/

        int[][] words = getWords(out);

        int h0 = 0x67452301;
        int h1 = 0xefcdab89;
        int h2 = 0x98badcfe;
        int h3 = 0x10325476;
        int h4 = 0xc3d2e1f0;
        int A, B, C, D, E;
        int A_, B_, C_, D_, E_;
        int T;
        for (int i = 0; i < out.length / 64; i++) {
            A = h0;
            B = h1;
            C = h2;
            D = h3;
            E = h4;
            A_ = h0;
            B_ = h1;
            C_ = h2;
            D_ = h3;
            E_ = h4;
            for (int j = 0; j < 80; j++) {
                int sum1 = A + f(B, C, D, j) + words[i][getR(j, false)] + getK(j, false);
                T = Integer.rotateLeft(sum1, getS(j, false)) + E;
                A = E;
                E = D;
                D = Integer.rotateLeft(C, 10);
                C = B;
                B = T;
                int sum2 = A_ + f(B_, C_, D_, 79 - j) + words[i][getR(j, true)] + getK(j, true);
                T = Integer.rotateLeft(sum2, getS(j, true)) + E_;
                A_ = E_;
                E_ = D_;
                D_ = Integer.rotateLeft(C_, 10);
                C_ = B_;
                B_ = T;
            }
            T = h1 + C + D_;
            h1 = h2 + D + E_;
            h2 = h3 + E + A_;
            h3 = h4 + A + B_;
            h4 = h0 + B + C_;
            h0 = T;
        }


        return intToHex(h0) + intToHex(h1) + intToHex(h2) + intToHex(h3) + intToHex(h4);
    }




    public static void main(String[] args) throws IOException {
        System.out.println(hash("The quick brown fox jumps over the lazy cog".getBytes()));


        /*byte[] arr = getByteArray("new.bmp");
        String hash = hash(arr);
        System.out.println(hash);*/
    }
}
