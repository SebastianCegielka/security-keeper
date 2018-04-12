package com.github.sebastiancegielka.securitykeeper.controller;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

class CipherTheFile {
    private SecretKeySpec secretKey;
    private Cipher cipher;

    CipherTheFile(String secret, int length, String algorithm)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
        byte[] key;
        key = fixSecret(secret, length);
        this.secretKey = new SecretKeySpec(key, algorithm);
        this.cipher = Cipher.getInstance(algorithm);
    }

    private byte[] fixSecret(String s, int length) throws UnsupportedEncodingException {
        if (s.length() < length) {
            int missingLength = length - s.length();
            for (int i = 0; i < missingLength; i++) {
                s += " ";
            }
        }
        return s.substring(0, length).getBytes("UTF-8");
    }

    void encryptFile(File f)
            throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
        this.writeToFile(f);
    }

    void decryptFile(File f)
            throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
        this.writeToFile(f);
    }

    private void writeToFile(File f) throws IOException, IllegalBlockSizeException, BadPaddingException {
        FileInputStream in = new FileInputStream(f);
        byte[] input = new byte[(int) f.length()];
        in.read(input);

        FileOutputStream out = new FileOutputStream(f);
        byte[] output = this.cipher.doFinal(input);
        out.write(output);

        out.flush();
        out.close();
        in.close();
    }
}
