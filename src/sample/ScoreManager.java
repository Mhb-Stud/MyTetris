package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScoreManager {
    private static List<Player> players= new ArrayList<>();
    public static void addPlayer(Player player){
        players.add(player);
        try {
            writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeToFile() throws IOException {
        File playersData = new File("playerData.txt");
        if(playersData.exists()){
            BufferedWriter b1 = new BufferedWriter(new FileWriter("playerData.txt",true));
            for (Player a:players) {
                b1.write(a.getName() + " " + a.getScore() + "\r\n");
            }
            b1.close();
        }else {
            playersData.createNewFile();
            BufferedWriter b1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("playerData.txt")));
            for (Player a:players) {
                b1.write(a.getName() + " " + a.getScore() + "\r\n");
            }
            b1.close();
        }
    }
    public static List<Player> readFromFile() throws IOException {
        File playersData = new File("playerData.txt");
        if(playersData.exists()) {
            BufferedReader b1 = new BufferedReader(new InputStreamReader(new FileInputStream("playerData.txt")));
            String[] inputs;
            while (b1.ready()) {
                inputs = b1.readLine().split(" ");
                players.add(new Player(inputs[0], Integer.parseInt(inputs[1])));
            }
            Collections.sort(players, new comparing());
            b1.close();
        }
        return players;
    }
    public static void resetScoreBoard(){
        File myPlayers = new File("playerData.txt");
        if(myPlayers.exists()){
            myPlayers.delete();
        }
    }

}
