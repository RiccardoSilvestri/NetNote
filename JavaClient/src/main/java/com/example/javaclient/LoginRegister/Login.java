package com.example.javaclient.LoginRegister;

import javax.swing.*;

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
        String nomeInput = JOptionPane.showInputDialog(null, "Inserisci Nome:");
        return nomeInput;
    }

    public String inserisciPassword() {
        String passwordInput = JOptionPane.showInputDialog(null, "Inserisci Password:");
        return passwordInput;
    }
}