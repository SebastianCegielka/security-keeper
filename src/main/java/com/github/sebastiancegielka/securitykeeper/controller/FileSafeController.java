package com.github.sebastiancegielka.securitykeeper.controller;

import com.github.sebastiancegielka.securitykeeper.model.PasswordEntry;
import com.github.sebastiancegielka.securitykeeper.model.PasswordSafe;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileSafeController {
    private File file = new File("password-manager-file.pwm");

    public void writeToFile(PasswordSafe safe){
        Gson gson = new Gson();
        List<String> entryJson = new ArrayList<>();
        for (int i = 0; i < safe.getSize() ; i++) {
            entryJson.add(gson.toJson(safe.getEntryFromMap(i)));
        }
        try{
            FileUtils.writeLines(file, entryJson, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PasswordSafe readFromFile(){
        Gson gson = new Gson();
        PasswordSafe safe = new PasswordSafe();
        try {
            List<String> list = FileUtils.readLines(file, "UTF-8");

            for (String gsonObj : list) {
                PasswordEntry pe = gson.fromJson(gsonObj, PasswordEntry.class);
                safe.addEntryToMap(pe);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return safe;
    }
}
