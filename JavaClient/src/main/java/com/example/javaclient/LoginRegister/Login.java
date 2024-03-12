package com.example.javaclient.LoginRegister;
import java.util.Scanner;
public class Login {
    public static void login() {
        boolean accessonegato = true;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci Nome: ");
        String nomeInput = scanner.nextLine();

        do {
            System.out.print("Inserisci Password: ");
            String passwordInput = scanner.nextLine();

            // Controllo diretto dei valori delle variabili nomeUtente e passwordUtente
            if (nomeInput.equals("NOMEDAPASSARE") && passwordInput.equals("PASSWORDDAPASSARE")) {
                System.out.println(" ");
                System.out.println("Benvenuto " + nomeInput + "!");
            }
            System.out.println("Accesso negato riprova");

            System.out.print("Inserisci Nome: ");
            nomeInput = scanner.nextLine();
        } while (accessonegato);
    }


}
