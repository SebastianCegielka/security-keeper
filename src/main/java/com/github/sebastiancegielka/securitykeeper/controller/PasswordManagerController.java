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
                if(safe.isThereMultipleAccountsOnSameWebsite(website)) {
                    safe.removeEntryByWebsite(website);
                } else {
                    String login = cV.getLoginForCheck();
                    safe.removeEntryByLogin(website, login);
                }
            } else if (action == 3){
                String website = cV.getWebsiteNameForCheck();
                if(safe.isThereMultipleAccountsOnSameWebsite(website)) {
                    safe.getPasswordByWebsite(website);
                } else {
                    String login = cV.getLoginForCheck();
                    safe.getPasswordByLogin(website, login);
                }
            } else if (action == 4){
                String website = cV.getWebsiteNameForCheck();
                char[] pass = cV.getNewPassword();
                if(safe.isThereMultipleAccountsOnSameWebsite(website)) {
                    safe.changePasswordByWebsite(website, pass);
                } else {
                    String login = cV.getLoginForCheck();
                    safe.changePasswordByLogin(website, login, pass);
                }

            }
            close = cV.closeApp();
        }
        fileSafeController.writeToFile(safe);
    }
}
