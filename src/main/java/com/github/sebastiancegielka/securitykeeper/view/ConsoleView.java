package com.github.sebastiancegielka.securitykeeper.view;

import com.github.sebastiancegielka.securitykeeper.model.PasswordEntry;

import java.util.Scanner;

public class ConsoleView {


    public int chooseAction(){
        Scanner sc = new Scanner(System.in);
        System.out.println("What do you want to do?\n1.add entry : 2.remove entry : 3.get password : 4.change password");
        int action = sc.nextInt();
        if(action > 0 && action < 5){
            return action;
        } else throw new IllegalArgumentException("That's not a proper value");
    }

    public PasswordEntry getFullEntry(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter website adress:");
        String address = sc.next();
        System.out.println("Enter your login:");
        String login = sc.next();
        System.out.println("Enter your password:");
        String password = sc.next();
        char[] passArray = new char[password.length()];
        for (int i = 0; i < password.length() ; i++) {
            passArray[i] = password.charAt(i);
        }
        return new PasswordEntry(address, login, passArray);
    }

    public String getWebsiteNameForCheck(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter website adress to which you would like to perform that action: ");
        return sc.next();
    }

    public String getLoginForCheck(){
        Scanner sc = new Scanner(System.in);
        System.out.println("You've got at least two accounts on this website. Enter a login for the account you meant: ");
        return sc.next();
    }

    public char[] getNewPassword(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your new password:");
        String password = sc.next();
        char[] passArray = new char[password.length()];
        for (int i = 0; i < password.length() ; i++) {
            passArray[i] = password.charAt(i);
        }
        return passArray;
    }

    public boolean closeApp(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to exit?\n Yes : No");
        String decision = sc.next();
        return decision.toLowerCase().equals("yes");
    }
}
