package sample;

import java.util.Comparator;

public class Player  {
    private String name;
    private int score;
    private int rank;

    public Player(String name , int score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

}
class comparing implements Comparator<Player>{

    @Override
    public int compare(Player o1, Player o2) {
        return o2.getScore() - o1.getScore();
    }
}
