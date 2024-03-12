package com.example.javaclient.LoginRegister;

import java.util.Scanner;

public class Register {
    private static String nomeUtente;
    private static String passwordUtente;
    private static String emailUtente;
    private static final char[] caratteriSpeciali = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '-', '=', '{', '}', '[', ']', '\\', '|', ':', ';', '"', '\'', '<', '>', ',', '.', '?', '/', '~', '`'};

    public static void register() {
        boolean isValidEmail;
        boolean isValidPassword;
        String nome = "";
        String password = "";
        String email = "";

        do {
            System.out.print("Inserisci Nome: ");
            nome = new Scanner(System.in).nextLine();

            System.out.println("Requisiti = Almeno Lunga 10 - Una Maiuscola - Carattere Speciale");
            System.out.println("Esempio: Oggiil$olesplende ");

            System.out.print("Inserisci Password: ");
            password = new Scanner(System.in).nextLine();
            isValidPassword = controlloPassword(password);

            System.out.print("Inserisci Email: ");
            email = new Scanner(System.in).nextLine();
            isValidEmail = controlloEmail(email);

            if (!isValidEmail || !isValidPassword) {
                System.out.println("Email non valida o Password non validi");
            }
        } while (!isValidEmail || !isValidPassword);

        nomeUtente = nome; //DA PASSARE
        passwordUtente = password; //DA PASSARE
        emailUtente= email;
        System.out.println("Registrazione completata!");
    }

    public static boolean controlloEmail(String email) {
        return email.contains("@");
    }

    public static boolean controlloPassword(String password) {
        boolean hasUpperCase = false;
        boolean hasSpecialChar = false;
        boolean hasUpperThen10 = password.length() >= 10;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            }
            for (char specialChar : caratteriSpeciali) {
                if (ch == specialChar) {
                    hasSpecialChar = true;
                    break;
                }
            }
        }

        return hasUpperCase && hasSpecialChar && hasUpperThen10;
    }
}
