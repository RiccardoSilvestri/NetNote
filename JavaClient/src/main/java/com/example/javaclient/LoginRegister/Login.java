package com.example.javaclient.LoginRegister;

import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        Login login = new Login();
        login.eseguiLogin();
    }

    public void eseguiLogin() {
        String nome = inserisciNome();
        String password = inserisciPassword();
    }

    public String inserisciNome() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci Nome: ");
        String nomeInput = scanner.nextLine();
        return nomeInput;
    }

    public String inserisciPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci Password: ");
        String passwordInput = scanner.nextLine();
        return passwordInput;
    }
}
