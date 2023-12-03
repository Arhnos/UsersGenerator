package fr.arhnos.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class CreateUser {
    private ArrayList<String> prenoms;
    private ArrayList<String> noms;
    private ArrayList<String> passwords;
    private Map<String, String> result;
    private static String filesep = File.separator;

    public CreateUser(){
        try {
            String dir = System.getProperty("user.dir");
            this.noms = exploit(new FileInputStream(dir + filesep + "usersgenerator" + filesep + "res" + filesep + "noms.csv"));
            this.prenoms = exploit(new FileInputStream(dir + filesep +"usersgenerator" + filesep + "res" + filesep + "prenoms.csv"));
            this.passwords = exploitPasswords(new FileInputStream(dir + filesep +"usersgenerator" + filesep + "res" + filesep + "passwords.txt"));
            this.result = null;
            System.out.println("Noms chargés : " + noms.size() + "\nPrénoms chargés : " + prenoms.size() + "\nMots de passe chargés : " + passwords.size());
            
        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé \n" + e.getMessage());
        }
    }

    /**
     * 
     * @param file Fichier CSV sous la forme (String, int) où int est une fréquence
     * @return Liste de String 
     */
    private static ArrayList<String> exploit(FileInputStream file){
        ArrayList<String> result = new ArrayList<String>();
        Scanner sc = new Scanner(file);
        sc.next();
        while (sc.hasNext()) {
            String[] line = sc.next().split(",");
            if(Integer.parseInt(line[1])>10){
                result.add(line[0]);
            }
        }
        sc.close();
        return result;
    }

    private static ArrayList<String> exploitPasswords(FileInputStream file){
        ArrayList<String> result = new ArrayList<>();
        Scanner sc = new Scanner(file);
        while (sc.hasNext()){
            result.add(sc.nextLine());
        }
        sc.close();
        return result;
    }

    public void generateUser(boolean hash){
        if(this.result == null){
            this.result = new HashMap<>();
        }
        Random rd = new Random();
        String prenom = this.prenoms.get(rd.nextInt(this.prenoms.size()));
        String nom = this.noms.get(rd.nextInt(this.noms.size()));
        String login  = prenom + "." + nom + "@gmail.com";
        String password = this.passwords.get(rd.nextInt(passwords.size()));
        if(hash){
            result.put(hashString(login.toLowerCase()), hashString(password));
        } else {
            result.put(login.toLowerCase(), password);
        }
    }

    public void generateUsers(int nb, boolean hash){
        for (int i = 0; i < nb; i++) {
            this.generateUser(hash);
        }
    }

    public Map<String, String> getResult(){
        return this.result;
    }

    public static String hashString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());
            byte[] hashedBytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
