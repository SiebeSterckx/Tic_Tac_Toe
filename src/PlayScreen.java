import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayScreen extends JPanel {
    private JLabel player1Label;
    private JLabel player2Label;
    private JButton[][] buttons; // 2D array om de vakken bij te houden
    private int currentPlayer; // Houdt bij welke speler aan de beurt is (1 voor Speler 1, 2 voor Speler 2)
    private JPanel cards;
    private CardLayout cl;

    public PlayScreen(CardLayout cl, JPanel cards) {
        this.cl = cl;
        this.cards = cards;

        setLayout(new BorderLayout());

        // MenuBar aanmaken en toevoegen aan het noorden
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("⚙");
        JMenu m2 = new JMenu("Help");
        JMenuItem m11 = new JMenuItem("Exit");
        JMenuItem m22 = new JMenuItem("Save as");

        // Lettertype instellen
        Font menuFont = new Font("Segoe UI Symbol", Font.BOLD, 15);
        Font iconFont = new Font("Segoe UI Symbol", Font.BOLD, 20);
        m1.setFont(iconFont);
        m2.setFont(menuFont);
        m11.setFont(menuFont);
        m22.setFont(menuFont);
        mb.setFont(menuFont);

        mb.add(m1);
        mb.add(m2);
        m1.add(m11);
        m1.add(m22);
        add(BorderLayout.NORTH, mb);

        // Actie toekennen aan Exit menu-item
        m11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Programma afsluiten
            }
        });

        // Spelersinformatie weergeven
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        player1Label = new JLabel();
        player2Label = new JLabel();
        infoPanel.add(player1Label);
        infoPanel.add(player2Label);
        add(BorderLayout.SOUTH, infoPanel);

        // GridLayout aanmaken en toevoegen aan het midden van het hoofdscherm
        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3]; // 3x3 matrix voor de vakken

        // Initialiseren van de vakken
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Segoe UI Symbol", Font.BOLD, 40)); // Lettertype en grootte voor de tekst op de knoppen
                int finalRow = row;
                int finalCol = col;
                buttons[row][col].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton clickedButton = buttons[finalRow][finalCol];
                        if (clickedButton.getText().isEmpty()) { // Controleer of het vak leeg is
                            if (currentPlayer == 1) {
                                clickedButton.setText("X");
                                currentPlayer = 2; // Wissel naar Speler 2
                            } else {
                                clickedButton.setText("O");
                                currentPlayer = 1; // Wissel naar Speler 1
                            }
                            checkForWinner(); // Controleer of er een winnaar is na elke zet
                        }

                    }
                });
                buttons[row][col].addComponentListener(new java.awt.event.ComponentAdapter() {
                    public void componentResized(java.awt.event.ComponentEvent evt) {
                        adjustFontSize(buttons[finalRow][finalCol]);
                    }
                });
                gridPanel.add(buttons[row][col]);
            }
        }
        add(BorderLayout.CENTER, gridPanel);

        // Begin met Speler 1
        currentPlayer = 1;
    }

    private void adjustFontSize(JButton button) {
        Font font = button.getFont();
        int buttonWidth = button.getWidth();
        int buttonHeight = button.getHeight();

        if (buttonWidth <= 0 || buttonHeight <= 0) {
            return; // Voorkom deling door nul of negatieve waarden
        }

        // Bereken de nieuwe lettergrootte
        int newFontSize = Math.min(buttonWidth, buttonHeight) / 2; // Verdeel de knopgrootte door 2 voor de lettergrootte

        button.setFont(new Font(font.getName(), font.getStyle(), newFontSize));
    }


    public void setPlayers(Player player1, Player player2) {
        player1Label.setText(player1.getName());
        player2Label.setText(player2.getName());
    }

    private void checkForWinner() {
        // Controleer horizontale en verticale rijen
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) && buttons[i][1].getText().equals(buttons[i][2].getText()) && !buttons[i][0].getText().isEmpty()) {
                String winner = buttons[i][0].getText().equals("X") ? player1Label.getText() : player2Label.getText();
                showEndScreen(winner);
                return;
            }
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) && buttons[1][i].getText().equals(buttons[2][i].getText()) && !buttons[0][i].getText().isEmpty()) {
                String winner = buttons[0][i].getText().equals("X") ? player1Label.getText() : player2Label.getText();
                showEndScreen(winner);
                return;
            }
        }
        // Controleer diagonalen
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][2].getText()) && !buttons[0][0].getText().isEmpty()) {
            String winner = buttons[0][0].getText().equals("X") ? player1Label.getText() : player2Label.getText();
            showEndScreen(winner);
            return;
        }
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][0].getText()) && !buttons[0][2].getText().isEmpty()) {
            String winner = buttons[0][2].getText().equals("X") ? player1Label.getText() : player2Label.getText();
            showEndScreen(winner);
            return;
        }

        // Controleer of er nog lege vakken zijn; zo niet, dan is het gelijkspel
        boolean draw = true;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    draw = false;
                    break;
                }
            }
            if (!draw) break;
        }
        if (draw) {
            showEndScreen(null);
        }
    }


    private void showEndScreen(String winnerName) {
        EndScreen endScreen = (EndScreen) cards.getComponent(2); // Zorg ervoor dat EndScreen op de juiste index staat
        endScreen.setWinner(winnerName);
        cl.show(cards, "end");
    }

    public void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        currentPlayer = 1;
    }

}
