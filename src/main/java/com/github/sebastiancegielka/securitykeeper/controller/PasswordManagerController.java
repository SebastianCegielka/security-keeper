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


    public void runApp() {
        int count = 0;
        String userKey = "";
        boolean close = false;
        boolean verify = false;
        while (count < 4 && !(verify)) {
            userKey = cV.getCipherKey();
            verify = fileSafeController.keyVerification(userKey);
            count++;
        }
        if(count>=4) {
            textIO.dispose();
            close = true;
        }
        fileSafeController.decryptTheFile(file, userKey);
        PasswordSafe safe = fileSafeController.readFromFile(file);
        fileSafeController.encryptTheFile(file, userKey);
        while (!close) {
            int action = cV.chooseAction();
            if (action == 1) {
                PasswordEntry entry = cV.getFullEntry();
                try {
                    safe.addEntryToMap(entry);
                } catch (IllegalArgumentException e) {
                    textIO.getTextTerminal().println("There's already an entry for this website and login. Use change password.");
                }
            } else if (action == 2) {
                String website = cV.getWebsiteNameForCheck();
                if (safe.isThereOneAccountsOnTheWebsite(website)) {
                    safe.removeEntryByWebsite(website);
                    textIO.getTextTerminal().println("Entry removed");
                } else if (!(safe.isThereOneAccountsOnTheWebsite(website)) && safe.isThereAnyWebsite(website)) {
                    String login = cV.getLoginForCheck();
                    safe.removeEntryByLogin(website, login);
                    textIO.getTextTerminal().println("Entry removed");
                } else {
                    textIO.getTextTerminal().println("There's no such website saved");
                }

            } else if (action == 3) {
                String website = cV.getWebsiteNameForCheck();
                if (safe.isThereOneAccountsOnTheWebsite(website)) {
                    safe.getPasswordByWebsite(website);
                    textIO.getTextTerminal().println("You've got it in your clipboard!");
                } else if (!(safe.isThereOneAccountsOnTheWebsite(website)) && safe.isThereAnyWebsite(website)) {
                    String login = cV.getLoginForCheck();
                    safe.getPasswordByLogin(website, login);
                    textIO.getTextTerminal().println("You've got it in your clipboard!");
                } else {
                    textIO.getTextTerminal().println("There's no such website saved");
                }

            } else if (action == 4) {
                String website = cV.getWebsiteNameForCheck();
                char[] pass = cV.getNewPassword();
                if (safe.isThereOneAccountsOnTheWebsite(website)) {
                    safe.changePasswordByWebsite(website, pass);
                } else if (!(safe.isThereOneAccountsOnTheWebsite(website)) && safe.isThereAnyWebsite(website)) {
                    String login = cV.getLoginForCheck();
                    safe.changePasswordByLogin(website, login, pass);
                } else {
                    textIO.getTextTerminal().println("There's no such website saved");
                }
            }
            close = cV.closeApp();
            textIO.dispose();
        }
        fileSafeController.writeToFile(safe, file);
        fileSafeController.encryptTheFile(file, userKey);

    }
}


