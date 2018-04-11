package com.github.sebastiancegielka.securitykeeper.controller;

import com.github.sebastiancegielka.securitykeeper.model.PasswordEntry;
import com.github.sebastiancegielka.securitykeeper.model.PasswordSafe;
import com.github.sebastiancegielka.securitykeeper.view.ConsoleView;

public class PasswordManagerController {
    private FileSafeController fileSafeController = new FileSafeController();
    private ConsoleView cV = new ConsoleView();

    public void runApp(){

        PasswordSafe safe = fileSafeController.readFromFile();
        boolean close = false;
        while(!close){
            int action = cV.chooseAction();
            if(action == 1){
                PasswordEntry entry = cV.getFullEntry();
                safe.addEntryToMap(entry);
            } else if (action == 2){
                String website = cV.getWebsiteNameForCheck();
                safe.removeEntry(website);
            } else if (action == 3){
                String website = cV.getWebsiteNameForCheck();
                safe.getPasswordByWebsite(website);
            } else if (action == 4){
                String website = cV.getWebsiteNameForCheck();
                char[] pass = cV.getNewPassword();
                safe.changePasswordByWebsite(website, pass);
            }
            close = cV.closeApp();
        }
        fileSafeController.writeToFile(safe);
    }
}
