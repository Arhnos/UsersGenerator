package fr.arhnos;

import java.util.Scanner;

import fr.arhnos.model.CreateUser;
import fr.arhnos.model.Export;

public class Main {
    public static void main(String[] args) {
        CreateUser cu = new CreateUser();

        Scanner input = new Scanner(System.in);
        System.out.println("Thanks to use \"UsersGenerator\"");
        System.out.println("How many users you want to create ?");

        try {
            Integer nb = Integer.parseInt(input.nextLine());
            
            System.out.println("Do you want to hash users and passwords : true/false");
    
            Boolean hash = Boolean.parseBoolean(input.nextLine());
    
            cu.generateUsers(nb, hash);
    
            System.out.println("1. Export CSV\n2.Export SQL");
    
            Integer export = Integer.parseInt(input.nextLine());
            Export ex = new Export(cu);
    
            if(export==1){
                ex.exportCSV();
            } else if (export==2){
                ex.exportSQL();
            }
        } catch (NumberFormatException e) {
            System.out.println("Saisie invalide");
        }
        
        input.close();
    }
}