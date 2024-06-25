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
    private int gridSize;
    private JPanel gridPanel; // Paneel voor het grid

    public PlayScreen(CardLayout cl, JPanel cards) {
        this.cl = cl;
        this.cards = cards;
        this.gridSize = 3; // Default grid size

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

        // GridPaneel aanmaken en toevoegen aan het midden van het hoofdscherm
        gridPanel = new JPanel();
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
        // Controleer horizontale lijnen
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col <= gridSize - 3; col++) {
                String symbol = buttons[row][col].getText();
                if (!symbol.isEmpty() &&
                        symbol.equals(buttons[row][col + 1].getText()) &&
                        symbol.equals(buttons[row][col + 2].getText())) {
                    showEndScreen(symbol.equals("X") ? player1Label.getText() : player2Label.getText());
                    return;
                }
            }
        }
        // Controleer verticale lijnen
        for (int col = 0; col < gridSize; col++) {
            for (int row = 0; row <= gridSize - 3; row++) {
                String symbol = buttons[row][col].getText();
                if (!symbol.isEmpty() &&
                        symbol.equals(buttons[row + 1][col].getText()) &&
                        symbol.equals(buttons[row + 2][col].getText())) {
                    showEndScreen(symbol.equals("X") ? player1Label.getText() : player2Label.getText());
                    return;
                }
            }
        }
        // Controleer diagonale lijnen (van linksboven naar rechtsonder)
        for (int row = 0; row <= gridSize - 3; row++) {
            for (int col = 0; col <= gridSize - 3; col++) {
                String symbol = buttons[row][col].getText();
                if (!symbol.isEmpty() &&
                        symbol.equals(buttons[row + 1][col + 1].getText()) &&
                        symbol.equals(buttons[row + 2][col + 2].getText())) {
                    showEndScreen(symbol.equals("X") ? player1Label.getText() : player2Label.getText());
                    return;
                }
            }
        }
        // Controleer diagonale lijnen (van rechtsboven naar linksonder)
        for (int row = 0; row <= gridSize - 3; row++) {
            for (int col = gridSize - 1; col >= 2; col--) {
                String symbol = buttons[row][col].getText();
                if (!symbol.isEmpty() &&
                        symbol.equals(buttons[row + 1][col - 1].getText()) &&
                        symbol.equals(buttons[row + 2][col - 2].getText())) {
                    showEndScreen(symbol.equals("X") ? player1Label.getText() : player2Label.getText());
                    return;
                }
            }
        }
        // Controleer op gelijkspel (geen winnaar gevonden en geen lege cellen meer)
        boolean draw = true;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    draw = false;
                    break;
                }
            }
            if (!draw) {
                break;
            }
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
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                buttons[row][col].setText("");
            }
        }
        currentPlayer = 1;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
        initializeGrid();
    }

    private void initializeGrid() {
        gridPanel.removeAll(); // Verwijder alle bestaande componenten in het gridPanel
        gridPanel.setLayout(new GridLayout(gridSize, gridSize));
        buttons = new JButton[gridSize][gridSize]; // Maak nieuwe 2D array op basis van gridSize

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
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
        revalidate();
        repaint();
    }
}
