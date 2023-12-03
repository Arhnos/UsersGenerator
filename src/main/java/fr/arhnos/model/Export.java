package fr.arhnos.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class Export {
    private Map<String, String> map;
    private static String filesep = File.separator;

    public Export(CreateUser cu){
        this.map = cu.getResult();
    }

    public void exportCSV(){
        try {
            File f = new File(System.getProperty("user.dir") + filesep + "usersgenerator" + filesep + "result" + filesep + "users.csv");
            if(f.exists()){
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f);
            fw.write("login,password");
            Iterator<String> tmp = this.map.keySet().iterator();
            while(tmp.hasNext()){
                String login = tmp.next();
                fw.write("\n" + login + "," + this.map.get(login));
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void exportSQL(){
        try{
            File f = new File(System.getProperty("user.dir") + filesep + "usersgenerator" + filesep + "result" + filesep + "users.sql");
            if(f.exists()){
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f);
            fw.write("\\set QUIET 1\n");
            fw.write("DROP TABLE users;\n");
            fw.write("CREATE TABLE users(login text, password text);");
            Iterator<String> tmp = this.map.keySet().iterator();
            while(tmp.hasNext()){
                String login = tmp.next();
                fw.write("\nINSERT INTO users VALUES ('" + login + "','" + this.map.get(login) + "');");
            }
            fw.write("\n\\set QUIET 0");
            fw.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
