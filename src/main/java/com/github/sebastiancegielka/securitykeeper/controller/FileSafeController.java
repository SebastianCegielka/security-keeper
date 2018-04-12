package com.github.sebastiancegielka.securitykeeper.controller;

import com.github.sebastiancegielka.securitykeeper.model.PasswordEntry;
import com.github.sebastiancegielka.securitykeeper.model.PasswordSafe;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

class FileSafeController {

    void writeToFile(PasswordSafe safe, File file) {
        Gson gson = new Gson();
        List<String> entryJson = new ArrayList<>();
        entryJson.add("decrypted");
        for (int i = 0; i < safe.getSize(); i++) {
            entryJson.add(gson.toJson(safe.getEntryFromMap(i)));
        }
        try {
            FileUtils.writeLines(file, entryJson, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void encryptTheFile(File file){
        CipherTheFile ctf;
        try {
            ctf = new CipherTheFile(")(*#@#$!@Egg", 16, "AES");
            ctf.encryptFile(file);
        } catch (IOException | NoSuchPaddingException | BadPaddingException | InvalidKeyException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    PasswordSafe readFromFile(File file) {
        Gson gson = new Gson();
        PasswordSafe safe = new PasswordSafe();
        try {
            List<String> list = FileUtils.readLines(file, "UTF-8");

            for (int i = 1; i < list.size() ; i++) {
                PasswordEntry pe = gson.fromJson(list.get(i), PasswordEntry.class);
                safe.addEntryToMap(pe);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return safe;
    }

    void decryptTheFile(File file){
        try {
            List<String> checkList = FileUtils.readLines(file, "UTF-8");
            if (!(checkList.get(0).equals("decrypted"))) {
                CipherTheFile ctf;
                try {
                    ctf = new CipherTheFile(")(*#@#$!@Egg", 16, "AES");
                    ctf.decryptFile(file);
                } catch (IOException | NoSuchPaddingException | BadPaddingException | InvalidKeyException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
