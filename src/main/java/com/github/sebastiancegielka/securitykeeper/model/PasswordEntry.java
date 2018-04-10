package com.github.sebastiancegielka.securitykeeper.model;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class PasswordEntry {

    private static int counter = 0;
    private final Integer id;
    private String website;
    private String login;
    private char[] password;

    public PasswordEntry(String internetPage, String login, char[] password) {
        this.website = internetPage;
        this.login = login;
        this.password = password;
        this.id = counter++;
    }

    String getWebsite() {
        return website;
    }

    String getLogin() {
        return login;
    }

    void getPassword() {
        StringBuilder sb = new StringBuilder();
        for (char letter : password) {
            sb.append(letter);
        }
        String pass = sb.toString();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(pass);
        clipboard.setContents(strSel, null);
    }

    int getId() {
        return id;
    }

    void setPassword(char[] password) {
        this.password = password;
    }
}
