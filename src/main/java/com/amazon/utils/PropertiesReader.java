package com.amazon.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    public static String readyKey(String key){
        Properties p = new Properties();

        String file_path = System.getProperty("user.dir")+"/src/main/resources/data.properties";
        try {
            FileInputStream fis = new FileInputStream(file_path);
            p.load(fis);
        } catch (FileNotFoundException e) {
            System.out.println("File Not found at: "+file_path);
        } catch (IOException e) {
            System.out.println("Failed to read properties at: "+file_path);
        }

        return p.getProperty(key);

    }
}
