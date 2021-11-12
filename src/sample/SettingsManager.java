package sample;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class SettingsManager {
    public static void writeToFile(int lvl) {
        File settings = new File("settings.txt");
        try {
            settings.createNewFile();
            BufferedWriter b1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("settings.txt")));
            b1.write(Integer.toString(lvl));
            b1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    public static int readFromFile()  {
        File settings = new File("settings.txt");
        if(settings.exists()) {
            try {
                BufferedReader b1 = new BufferedReader(new InputStreamReader(new FileInputStream("settings.txt")));
                int lvl = Integer.parseInt(b1.readLine());
                b1.close();
                return lvl;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }
}
