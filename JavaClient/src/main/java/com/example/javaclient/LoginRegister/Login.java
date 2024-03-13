package com.example.javaclient.LoginRegister;
import java.util.Scanner;
public class Login {
    public static String login(String nomeInput) {
        boolean accessonegato = true;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci Nome: ");
        nomeInput = scanner.nextLine();

       // do {
            System.out.print("Inserisci Password: ");
            String passwordInput = scanner.nextLine();
                System.out.println(" ");
                return(nomeInput);
       //     }
       //     System.out.println("Accesso negato riprova");

       //     System.out.print("Inserisci Nome: ");
       //     nomeInput = scanner.nextLine();
      //  } while (accessonegato);
    }


}
