package com.github.sebastiancegielka.securitykeeper.view;

import com.github.sebastiancegielka.securitykeeper.model.PasswordEntry;

import java.util.Scanner;

public class ConsoleView {
    private Scanner sc = new Scanner(System.in);

    public int chooseAction(){
        System.out.println("What do you want to do?\n1.add entry : 2.remove entry : 3.get password : 4.change password");
        int action = sc.nextInt();
        if(action > 0 && action < 5){
            return action;
        } else throw new IllegalArgumentException("That's not a proper value");
    }

    public PasswordEntry getFullEntry(){
        System.out.println("Enter website adress:");
        String address = sc.nextLine();
        System.out.println("Enter your login:");
        String login = sc.nextLine();
        System.out.println("Enter your password:");
        String password = sc.nextLine();
        char[] passArray = new char[password.length()];
        for (int i = 0; i < password.length() ; i++) {
            passArray[i] = password.charAt(i);
        }

        return new PasswordEntry(address, login, passArray);
    }

    public String getWebsiteName(){
        System.out.println("Enter website adress to which you would like to perform that action: ");
        return sc.nextLine();
    }

    public String getLogin(){
        System.out.println("You've got at least two accounts on this website. Enter a login for the account you meant: ");
        return sc.nextLine();
    }

    public char[] getPassword(){
        System.out.println("Enter your new password:");
        String password = sc.nextLine();
        char[] passArray = new char[password.length()];
        for (int i = 0; i < password.length() ; i++) {
            passArray[i] = password.charAt(i);
        }
        return passArray;
    }
}
