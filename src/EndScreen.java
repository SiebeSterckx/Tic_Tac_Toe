import javax.swing.*;
import java.awt.*;

public class EndScreen extends JPanel {
    private JLabel winnerLabel;

    public EndScreen(CardLayout cl, JPanel cards, PlayScreen playScreen) {
        setLayout(new BorderLayout());

        winnerLabel = new JLabel("", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        add(winnerLabel, BorderLayout.CENTER);

        // JPanel met FlowLayout om knoppen naast elkaar te plaatsen
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton restartButton = new JButton("Play with the same players");
        restartButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        restartButton.addActionListener(e -> {
            playScreen.resetGame();
            cl.show(cards, "main");
        });
        buttonPanel.add(restartButton);

        // Voeg hier je andere knop toe
        JButton anotherButton = new JButton("Play with other players");
        anotherButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        anotherButton.addActionListener(e -> {
            playScreen.resetGame();
            cl.show(cards, "start");
        });
        buttonPanel.add(anotherButton);

        add(buttonPanel, BorderLayout.SOUTH);

    }

    public void setWinner(String winnerName) {
        if (winnerName == null) {
            winnerLabel.setText("<html><span style='color:blue'>It's a draw!</span></html>");
        } else {
            Player.getPlayer(winnerName).increaseScore();
            Player player= Player.getPlayer(winnerName);
            winnerLabel.setText("<html>" + "Winner: " + player.getName() + "<br>Win streak: " + player.getScore() + "🔥</html>");
        }
    }
}
