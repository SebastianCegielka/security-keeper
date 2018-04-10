package com.github.sebastiancegielka.securitykeeper.model;

import com.github.sebastiancegielka.securitykeeper.view.ConsoleView;

import java.util.HashMap;
import java.util.Map;

public class PasswordSafe {
    private Map<Integer, PasswordEntry> passMap;

    public PasswordSafe() {
        passMap = new HashMap<>();
    }

    public void addEntryToMap(PasswordEntry entry){
        passMap.put(entry.getId(), entry);
    }

    public void removeEntry(String websiteName){
        int index = -1;
        int count = 0;
        for (Map.Entry<Integer, PasswordEntry> entry : passMap.entrySet()){
            if(websiteName.equals(entry.getValue().getWebsite())){
                index = entry.getKey();
                count++;
            }
        }
        if(count == 1) passMap.remove(index);
        else {
            ConsoleView cV = new ConsoleView();
            removeEntryByLogin(websiteName, cV.getLogin());
        }
    }

    private void removeEntryByLogin(String websiteName, String login){
        int index = -1;
        for (Map.Entry<Integer, PasswordEntry> entry : passMap.entrySet()){
            if(websiteName.equals(entry.getValue().getWebsite())
                    && login.equals(entry.getValue().getLogin())){
                index = entry.getKey();
            }
        }
        passMap.remove(index);
    }

    public void getPasswordByWebsite(String websiteName){
        long count = passMap.values().stream()
                .filter(pe -> pe.getWebsite().equals(websiteName))
                .count();
        if(count == 1){
            passMap.values().stream()
                    .filter(pe -> pe.getWebsite().equals(websiteName))
                    .findFirst()
                    .orElseThrow(RuntimeException::new)
                    .getPassword();
        } else {
            ConsoleView cV = new ConsoleView();
            getPasswordByLogin(websiteName, cV.getLogin());
        }
    }

    private void getPasswordByLogin(String websiteName, String login){
        passMap.values().stream()
                .filter(pe -> pe.getWebsite().equals(websiteName))
                .filter(pe -> pe.getLogin().equals(login))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getPassword();
    }

    public void changePassword(String websiteName, char[] password){
        long count = passMap.values().stream()
                .filter(pe -> pe.getWebsite().equals(websiteName))
                .count();
        if(count == 1){
            passMap.values().stream()
                    .filter(pe -> pe.getWebsite().equals(websiteName))
                    .findFirst()
                    .orElseThrow(RuntimeException::new)
                    .setPassword(password);
        } else {
            ConsoleView cV = new ConsoleView();
            changePasswordByLogin(websiteName, cV.getLogin(), password);
        }
    }

    private void changePasswordByLogin(String websiteName, String login, char[] password){
        passMap.values().stream()
                .filter(pe -> pe.getWebsite().equals(websiteName))
                .filter(pe -> pe.getLogin().equals(login))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .setPassword(password);
    }



}
