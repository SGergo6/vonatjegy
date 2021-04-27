package IO;

import line.Line;
import station.Station;

import java.io.*;
import java.util.*;

public abstract class Save {

    public static final String STATIONS_FILE = "stations";
    public static final String LINES_FILE = "lines";
    public static final String PASSENGERS_FILE = "passengers";
    public static final String TICKETS_FILE = "tickets";

    public static void save(String filename, Collection collection){
        try{
            FileOutputStream f = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(f);

            for (Object object : collection) {
                out.writeObject(object);
            }
            out.close();
            f.close();
        }catch (IOException e){
            System.out.println("A mentés nem sikerült! Oka: " + e.getMessage());
        }
    }

    public static void saveProperties(Properties p){
        try {
            FileOutputStream f = new FileOutputStream("global.txt");
            p.store(f, "Kézi ülésválasztás ára");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
