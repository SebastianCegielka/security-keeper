package com.github.sebastiancegielka.securitykeeper.view;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.console.*;
import com.github.sebastiancegielka.securitykeeper.model.PasswordEntry;


public class ConsoleView {
    private TextIO textIO = TextIoFactory.getTextIO();

    public int chooseAction() {
        int action = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(4)
                .read("What do you want to do?\n1.add entry\n2.remove entry\n3.get password\n4.change password\nEnter number from 1-4:");
        if (action > 0 && action < 5) {
            return action;
        } else throw new IllegalArgumentException("That's not a proper value");
    }

    public PasswordEntry getFullEntry() {
        String address = textIO.newStringInputReader()
                .withDefaultValue("website")
                .withMinLength(1)
                .read("Enter website adress:");
        String login = textIO.newStringInputReader()
                .withDefaultValue("admin")
                .withMinLength(1)
                .read("Enter your login:");
        String password = textIO.newStringInputReader()
                .withMinLength(3)
                .withInputMasking(true)
                .read("Enter your password:");
        char[] passArray = new char[password.length()];
        for (int i = 0; i < password.length(); i++) {
            passArray[i] = password.charAt(i);
        }
        return PasswordEntry.Builder
                .create()
                .withWebsite(address)
                .withLogin(login)
                .withPassword(passArray)
                .build();
    }

    public String getWebsiteNameForCheck() {
        return textIO.newStringInputReader()
                .withDefaultValue("website")
                .withMinLength(1)
                .read("Enter website adress to which you would like to perform that action: ");
    }

    public String getLoginForCheck() {
        return textIO.newStringInputReader()
                .withDefaultValue("admin")
                .withMinLength(1)
                .read("You've got at least two accounts on this website. Enter a login for the account you meant: ");
    }

    public char[] getNewPassword() {
        String password = textIO.newStringInputReader()
                .withMinLength(3)
                .withInputMasking(true)
                .read("Enter your password:");
        char[] passArray = new char[password.length()];
        for (int i = 0; i < password.length(); i++) {
            passArray[i] = password.charAt(i);
        }
        return passArray;
    }

    public boolean closeApp() {
        String decision = textIO.newStringInputReader()
                .withDefaultValue("no")
                .withInlinePossibleValues("no", "yes")
                .read("Do you want to exit?");
        return decision.toLowerCase().equals("yes");

    }
}
