package com.github.sebastiancegielka.securitykeeper.model;

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

    public void addEntryToMap(PasswordEntry entry) throws IllegalArgumentException {
        if(!checkIfAlreadyInSafe(entry)) {
            passMap.put(entry.getId(), entry);
        } else throw new IllegalArgumentException("Password for given website and login is already in safe");
    }

    public PasswordEntry getEntryFromMap(int index) {
        return passMap.get(index);
    }

    public boolean isThereOneAccountsOnTheWebsite(String websiteName) {
        long count = passMap.values().stream()
                .filter(pe -> pe.getWebsite().equals(websiteName))
                .count();
        if (count < 1) System.out.println("There's no password saved for given entry");
        return count == 1;
    }

    public void removeEntryByWebsite(String websiteName) {
            int index = passMap.values().stream()
                    .filter(pe -> pe.getWebsite().equals(websiteName))
                    .findFirst()
                    .orElseThrow(RuntimeException::new)
                    .getId();

            passMap.remove(index);
    }

    public void removeEntryByLogin(String websiteName, String login) {
        int index = passMap.values().stream()
                .filter(pe -> pe.getWebsite().equals(websiteName))
                .filter(pe -> pe.getLogin().equals(login))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getId();

        passMap.remove(index);
    }

    public void getPasswordByWebsite(String websiteName) {
            passMap.values().stream()
                    .filter(pe -> pe.getWebsite().equals(websiteName))
                    .findFirst()
                    .orElseThrow(RuntimeException::new)
                    .getPasswordToClipboard();
    }

    public void getPasswordByLogin(String websiteName, String login) {
        passMap.values().stream()
                .filter(pe -> pe.getWebsite().equals(websiteName))
                .filter(pe -> pe.getLogin().equals(login))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getPasswordToClipboard();
    }

    public void changePasswordByWebsite(String websiteName, char[] password) {
            passMap.values().stream()
                    .filter(pe -> pe.getWebsite().equals(websiteName))
                    .findFirst()
                    .orElseThrow(RuntimeException::new)
                    .setPassword(password);
    }

    public void changePasswordByLogin(String websiteName, String login, char[] password) {
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

    boolean checkIfAlreadyInSafe(PasswordEntry pe){
       return passMap.values().stream()
               .anyMatch(x -> x.getWebsite().equals(pe.getWebsite()) && x.getLogin().equals(pe.getLogin()));
    }


}
