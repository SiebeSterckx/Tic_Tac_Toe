import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int score;
    private static List<Player> players = new ArrayList<>();


    public Player(String name) {
        this.name = name;
        this.score = 0;
        players.add(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Totaal aantal gewonnen spelletjes
    public int getScore() {
        return score;
    }

    public void increaseScore() {
        score++;
    }

    public static void resetPlayerList() {
        players.clear();
    }

    public static Player getPlayer(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public String toString() {
        return "Player: " + name + " Score: " + score;
    }
}
