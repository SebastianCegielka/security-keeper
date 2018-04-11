package com.github.sebastiancegielka.securitykeeper;

import com.github.sebastiancegielka.securitykeeper.controller.PasswordManagerController;

public class Main {
    public static void main(String[] args) {
        PasswordManagerController test = new PasswordManagerController();
        test.runApp();
    }
}
