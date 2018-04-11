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

    public PasswordEntry() {
        this.id = counter++;
    }

    public PasswordEntry(String internetPage, String login, char[] password) {
        this.website = internetPage;
        this.login = login;
        this.password = password;
        this.id = counter++;
    }

    public String getWebsite() {
        return website;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        StringBuilder sb = new StringBuilder();
        for (char letter : password) {
            sb.append(letter);
        }
        return sb.toString();
    }

    public void getPasswordToClipboard() {
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

    public int getId() {
        return id;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordEntry that = (PasswordEntry) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (website != null ? !website.equals(that.website) : that.website != null) return false;
        return login != null ? login.equals(that.login) : that.login == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        return result;
    }
}
