package com.github.sebastiancegielka.securitykeeper.model;

import com.github.sebastiancegielka.securitykeeper.view.ConsoleView;

import java.util.HashMap;
import java.util.Map;

public class PasswordSafe {
    private Map<Integer, PasswordEntry> passMap;

    public PasswordSafe() {
        passMap = new HashMap<>();
    }

    public int getSize() {
        return passMap.size();
    }

    public void addEntryToMap(PasswordEntry entry) {
        passMap.put(entry.getId(), entry);
    }

    public PasswordEntry getEntryFromMap(int index) {
        return passMap.get(index);
    }

    private boolean isThereMultipleAccountsOnSameWebsite(String websiteName) {
        long count = passMap.values().stream()
                .filter(pe -> pe.getWebsite().equals(websiteName))
                .count();
        if (count < 1) System.out.println("There's no password saved for given entry");
        return count == 1;
    }

    public void removeEntry(String websiteName) {
        if (isThereMultipleAccountsOnSameWebsite(websiteName)) {
            int index = passMap.values().stream()
                    .filter(pe -> pe.getWebsite().equals(websiteName))
                    .findFirst()
                    .orElseThrow(RuntimeException::new)
                    .getId();

            passMap.remove(index);
        } else {
            ConsoleView cV = new ConsoleView();
            removeEntryByLogin(websiteName, cV.getLoginForCheck());
        }
    }

    private void removeEntryByLogin(String websiteName, String login) {
        int index = passMap.values().stream()
                .filter(pe -> pe.getWebsite().equals(websiteName))
                .filter(pe -> pe.getLogin().equals(login))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getId();

        passMap.remove(index);
    }

    public void getPasswordByWebsite(String websiteName) {
        if (isThereMultipleAccountsOnSameWebsite(websiteName)) {
            passMap.values().stream()
                    .filter(pe -> pe.getWebsite().equals(websiteName))
                    .findFirst()
                    .orElseThrow(RuntimeException::new)
                    .getPasswordToClipboard();
        } else {
            ConsoleView cV = new ConsoleView();
            getPasswordByLogin(websiteName, cV.getLoginForCheck());
        }
    }

    private void getPasswordByLogin(String websiteName, String login) {
        passMap.values().stream()
                .filter(pe -> pe.getWebsite().equals(websiteName))
                .filter(pe -> pe.getLogin().equals(login))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getPasswordToClipboard();
    }

    public void changePasswordByWebsite(String websiteName, char[] password) {
        if (isThereMultipleAccountsOnSameWebsite(websiteName)) {
            passMap.values().stream()
                    .filter(pe -> pe.getWebsite().equals(websiteName))
                    .findFirst()
                    .orElseThrow(RuntimeException::new)
                    .setPassword(password);
        } else {
            ConsoleView cV = new ConsoleView();
            changePasswordByLogin(websiteName, cV.getLoginForCheck(), password);
        }
    }

    private void changePasswordByLogin(String websiteName, String login, char[] password) {
        passMap.values().stream()
                .filter(pe -> pe.getWebsite().equals(websiteName))
                .filter(pe -> pe.getLogin().equals(login))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .setPassword(password);
    }

    public boolean containsWebsite(PasswordEntry pe){
        return passMap.containsValue(pe);
    }


}
