package IO;

import station.Station;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

public abstract class Save {

    public static void saveStations(HashSet<Station> stations){
        try{
            FileOutputStream f = new FileOutputStream("stations.txt");
            ObjectOutputStream out = new ObjectOutputStream(f);

            for (Station station : stations) {
                out.writeObject(station);
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
