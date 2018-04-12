package com.github.sebastiancegielka.securitykeeper.controller;

import com.github.sebastiancegielka.securitykeeper.model.PasswordEntry;
import com.github.sebastiancegielka.securitykeeper.model.PasswordSafe;
import com.github.sebastiancegielka.securitykeeper.view.ConsoleView;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.io.File;

public class PasswordManagerController {
    private FileSafeController fileSafeController = new FileSafeController();
    private ConsoleView cV = new ConsoleView();
    private TextIO textIO = TextIoFactory.getTextIO();
    private File file = new File("password-manager-file.pwm");

    public void runApp(){

        PasswordSafe safe = fileSafeController.readFromFile(file);
        boolean close = false;
        while(!close){
            int action = cV.chooseAction();
            if(action == 1){
                PasswordEntry entry = cV.getFullEntry();
                    safe.addEntryToMap(entry);

            } else if (action == 2){
                String website = cV.getWebsiteNameForCheck();
                if(safe.isThereOneAccountsOnTheWebsite(website)) {
                    safe.removeEntryByWebsite(website);
                    textIO.getTextTerminal().print("Entry removed\n");
                } else {
                    String login = cV.getLoginForCheck();
                    safe.removeEntryByLogin(website, login);
                    textIO.getTextTerminal().print("Entry removed\n");
                }

            } else if (action == 3){
                String website = cV.getWebsiteNameForCheck();
                if(safe.isThereOneAccountsOnTheWebsite(website)) {
                    safe.getPasswordByWebsite(website);
                    textIO.getTextTerminal().print("You've got it in your clipboard!\n");
                } else {
                    String login = cV.getLoginForCheck();
                    safe.getPasswordByLogin(website, login);
                    textIO.getTextTerminal().print("You've got it in your clipboard!\n");
                }

            } else if (action == 4){
                String website = cV.getWebsiteNameForCheck();
                char[] pass = cV.getNewPassword();
                if(safe.isThereOneAccountsOnTheWebsite(website)) {
                    safe.changePasswordByWebsite(website, pass);
                } else {
                    String login = cV.getLoginForCheck();
                    safe.changePasswordByLogin(website, login, pass);
                }
            }
            close = cV.closeApp();
        }
        fileSafeController.writeToFile(safe, file);
    }
}

