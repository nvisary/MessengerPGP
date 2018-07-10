package com.summerproject.messenger.pgp.idea;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.Random;


public class IDEA {
    private int[] subKeys;
    private byte[] data;

    private int mod = 65537;  //2^16 + 1

    private void generateSubKeys(byte[] userKey) {
        if (userKey.length != 16) {
            return;
        }
        subKeys = new int[8 * 6 + 4]; //подключи для каждого из восьми раундов шифрования

        /*Разбиваем исходный ключ в 128 бит на 8 16-битных блоков. (Первые восемь подключей)*/
        for (int i = 0; i < 8; i++) {
            subKeys[i] = uniteTwoBytes(userKey[2 * i], userKey[2 * i + 1]);
        }

        /*128-битный ключ циклически сдвигается влево на 25 позиций,
         * после чего новый 128-битный блок снова разбивается на восемь 16-битных блоков. продолжается до тех пор,
         * пока не будет сгенерированы все 52 ключа
         */
        for (int i = 8; i < subKeys.length; i++) {
            int byte1, byte2;

            if ((i + 1) % 8 != 0) {
                byte1 = subKeys[i - 7] << 9;
            } else {
                byte1 = subKeys[i - 15] << 9;
            }

            if ((i + 2) % 8 < 2) {
                byte2 = subKeys[i - 14] >> 7;
            } else {
                byte2 = subKeys[i - 6] >>> 7;
            }

            subKeys[i] = (byte1 | byte2) & 0xFFFF;
        }
    }

    private void invertSubKeys() {
        int[] invertSubKeys = new int[subKeys.length];
        int keyN = 0;
        int i = 48;
        invertSubKeys[i] = mulInvert(subKeys[keyN++]);
        invertSubKeys[i + 1] = addInvert(subKeys[keyN++]);
        invertSubKeys[i + 2] = addInvert(subKeys[keyN++]);
        invertSubKeys[i + 3] = mulInvert(subKeys[keyN++]);

        for (int r = 7; r > 0; r--) {
            i = r * 6;
            invertSubKeys[i + 4] = subKeys[keyN++];
            invertSubKeys[i + 5] = subKeys[keyN++];
            invertSubKeys[i] = mulInvert(subKeys[keyN++]);
            invertSubKeys[i + 2] = addInvert(subKeys[keyN++]);
            invertSubKeys[i + 1] = addInvert(subKeys[keyN++]);
            invertSubKeys[i + 3] = mulInvert(subKeys[keyN++]);
        }
        invertSubKeys[4] = subKeys[keyN++];
        invertSubKeys[5] = subKeys[keyN++];
        invertSubKeys[0] = mulInvert(subKeys[keyN++]);
        invertSubKeys[1] = addInvert(subKeys[keyN++]);
        invertSubKeys[2] = addInvert(subKeys[keyN++]);
        invertSubKeys[3] = mulInvert(subKeys[keyN]);
        subKeys = invertSubKeys;

    }

    public byte[] encode(byte[] in, BigInteger key) {
        byte[] key16 = create16ByteKey(key.toString());
        generateSubKeys(key16);

        int count;
        int len = in.length;
        if (len % 8 != 0) {
            count = len / 8 + 1;
        } else {
            count = len / 8;
        }
        int zeros = count * 8 - len;
        byte[] result = new byte[len + zeros + 1];

        data = in;
        int pos = 0;
        for (int i = 0; i < count; i++) {
            encodeBlock(pos, result);
            pos += 8;
        }
        result[result.length - 1] = (byte) zeros;
        return result;
    }

    private byte getData(int index) {
        if (index >= data.length) {
            return 0;
        } else {
            return data[index];
        }
    }

    public byte[] decode(byte[] in, BigInteger key) {
        byte[] key16 = create16ByteKey(key.toString());
        generateSubKeys(key16);
        invertSubKeys();
        int zeros = in[in.length - 1];
        int len = in.length - zeros - 1;
        int count = 0;
        if (len % 8 != 0) {
            count = len / 8 + 1;
        } else {
            count = len / 8;
        }
        data = in;
        byte[] result = new byte[len];
        int pos = 0;
        for (int i = 0; i < count; i++) {
            encodeBlock(pos, result);
            pos += 8;
        }

        return result;
    }
    public String generateKey(int len) {
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < len; i++) {
            sb.append((char) rnd.nextInt());
        }

        return sb.toString();
    }
    private void encodeBlock(int position, byte[] result) {
        /*Каждый блок по 64 бита делится на четыре подблока по 16 бит каждый*/
        int x1 = uniteTwoBytes(getData(position), getData(position + 1));
        int x2 = uniteTwoBytes(getData(position + 2), getData(position + 3));
        int x3 = uniteTwoBytes(getData(position + 4), getData(position + 5));
        int x4 = uniteTwoBytes(getData(position + 6), getData(position + 7));
        int keyN = 0;
        for (int round = 0; round < 8; round++) {
            int y1 = mul(x1, subKeys[keyN++]);
            int y2 = add(x2, subKeys[keyN++]);
            int y3 = add(x3, subKeys[keyN++]);
            int y4 = mul(x4, subKeys[keyN++]);

            int y5 = y1 ^ y3;
            int y6 = y2 ^ y4;

            int y7 = mul(y5, subKeys[keyN++]);
            int y8 = add(y6, y7);
            int y9 = mul(y8, subKeys[keyN++]);
            int y10 = add(y7, y9);

            x1 = y1 ^ y9;
            x2 = y3 ^ y9;
            x3 = y2 ^ y10;
            x4 = y4 ^ y10;
        }

        int z0 = mul(x1, subKeys[keyN++]);
        int z1 = add(x3, subKeys[keyN++]);
        int z2 = add(x2, subKeys[keyN++]);
        int z3 = mul(x4, subKeys[keyN]);

        setByteToArr(result, position, (byte) (z0 >> 8));
        setByteToArr(result, position + 1, (byte) z0);
        setByteToArr(result, position + 2, (byte) (z1 >> 8));
        setByteToArr(result, position + 3, (byte) z1);
        setByteToArr(result, position + 4, (byte) (z2 >> 8));
        setByteToArr(result, position + 5, (byte) z2);
        setByteToArr(result, position + 6, (byte) (z3 >> 8));
        setByteToArr(result, position + 7, (byte) z3);
    }

    private void setByteToArr(byte[] arr, int pos, byte value) {
        if (arr.length > pos) {
            arr[pos] = value;
        } else {
            return;
        }
    }

    private int mulInvert(int x) {
        if (x <= 1) {
            return x;
        }
        int y = mod;
        int t0 = 1;
        int t1 = 0;
        while (true) {
            t1 += y / x * t0;
            y %= x;
            if (y == 1) {
                return (1 - t1) & 0xFFFF;
            }
            t0 += x / y * t1;
            x %= y;
            if (x == 1) {
                return t0;
            }
        }
    }

    public byte[] loadByteArrayFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] result = Files.readAllBytes(file.toPath());
        return result;
    }

    public void saveByteArrayToFile(byte[] arr, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(arr);
        fos.close();
    }
    private int addInvert(int x) {
        return (0x10000 - x) & 0xFFFF;
    }

    private int add(int x, int y) {
        return (x + y) & 0xFFFF;
    }

    /*Умножение по модулю 2^16 + 1*/
    private int mul(int x, int y) {
        long m = (long) x * y;
        if (m != 0) {
            return (int) (m % mod) & 0xFFFF;  //2^16 + 1
        } else {
            if (x != 0 || y != 0) {
                return (1 - x - y) & 0xFFFF;
            }
            return 0;
        }
    }

    private byte[] create16ByteKey(String userKey) {
        byte[] key = new byte[16];
        int i, j;
        for (i = 0, j = 0; i < key.length; i++) {
            j = (j + 1) % userKey.length();
            key[i] ^= (byte) userKey.charAt(j);
        }
        return key;
    }

    /*Объединение двух байт в один интежер*/
    private int uniteTwoBytes(int byte1, int byte2) {
        byte1 &= 0xFF;
        byte1 <<= 8;
        byte2 &= 0xFF;
        return (byte1 | byte2);
    }

    public static void main(String[] args) {

    }
}
