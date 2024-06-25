import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        // Hoofdframe
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        // Stel de minimale grootte van het frame in
        frame.setMinimumSize(new Dimension(500, 500));

        // CardLayout aanmaken en instellen
        JPanel cards = new JPanel(new CardLayout());

        CardLayout cl = (CardLayout) (cards.getLayout());

        // Hoofdscherm aanmaken en toevoegen aan de CardLayout
        PlayScreen playScreen = new PlayScreen(cl, cards);

        // Startscherm aanmaken en toevoegen aan de CardLayout
        StartScreen startScreen = new StartScreen(cl, cards, playScreen);

        // Eindscherm aanmaken en toevoegen aan de CardLayout
        EndScreen endScreen = new EndScreen(cl, cards, playScreen);

        cards.add(startScreen, "start");
        cards.add(playScreen, "main");
        cards.add(endScreen, "end");


        // Voeg CardLayout paneel toe aan het frame
        frame.getContentPane().add(cards);

        // Maak het frame zichtbaar
        frame.setVisible(true);
    }
}
