package com.filenetce;

import com.filenetce.dao.CEDAO;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        try {
//            File jarPath=new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//            String propertiesPath=jarPath.getParentFile().getAbsolutePath();
//            System.out.println(" propertiesPath-"+propertiesPath);
            FileReader fr = new FileReader("./config/moamalat.json");



            BufferedReader br = new BufferedReader(fr);



            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String json = sb.toString();







            CEDAO dao = new CEDAO();


            dao.createClassFromJson(json);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // write your code here
    }
}
