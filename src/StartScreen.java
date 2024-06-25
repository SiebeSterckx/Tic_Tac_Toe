import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {
    private int gridSize = 3; // Default grid size

    public StartScreen(CardLayout cl, JPanel cards, PlayScreen playScreen) {
        setLayout(new BorderLayout());
        //setLayout(new GridLayout(3, 2));

        // JPanel met FlowLayout om knoppen naast elkaar te plaatsen
        JPanel namesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namesPanel.setSize(500, 10);
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel nameLabel1 = new JLabel("Name P1:");
        JTextField nameField1 = new JTextField();
        nameField1.setPreferredSize(new Dimension(100, 30));

        JLabel nameLabel2 = new JLabel("Name P2:");
        JTextField nameField2 = new JTextField();
        nameField2.setPreferredSize(new Dimension(100, 30));

        JButton submitButton = new JButton("Start Game");

        namesPanel.add(nameLabel1);
        namesPanel.add(nameField1);
        namesPanel.add(nameLabel2);
        namesPanel.add(nameField2);
        submitPanel.add(submitButton);

        // Paneel voor grid size selectie
        JPanel gridSizePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gridSizePanel.add(new JLabel("Select grid size:"));

        ButtonGroup gridSizeGroup = new ButtonGroup();
        for (int i = 3; i <= 9; i++) {
            JRadioButton gridSizeButton = new JRadioButton(String.valueOf(i));
            gridSizeButton.addActionListener(e -> gridSize = Integer.parseInt(gridSizeButton.getText()));
            gridSizeGroup.add(gridSizeButton);
            gridSizePanel.add(gridSizeButton);
        }

        // Standaard geselecteerde grootte
        ((JRadioButton) gridSizePanel.getComponent(1)).setSelected(true);

        add(namesPanel, BorderLayout.NORTH);
        add(gridSizePanel, BorderLayout.CENTER);
        add(submitPanel, BorderLayout.SOUTH);

        // Actie toekennen aan de submit-knop
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name1 = nameField1.getText().trim();
                String name2 = nameField2.getText().trim();

                if (name1.isEmpty() || name2.isEmpty()) {
                    JOptionPane.showMessageDialog(StartScreen.this, "Both name fields must be filled in.", "2 players required", JOptionPane.ERROR_MESSAGE);
                } else {
                    Player.resetPlayerList();
                    Player player1 = new Player(name1);
                    Player player2 = new Player(name2);
                    System.out.println(player1);
                    System.out.println(player2);
                    playScreen.setPlayers(player1, player2);
                    playScreen.setGridSize(gridSize);
                    System.out.println("Grid size: " + gridSize);
                    cl.show(cards, "main");
                }
            }
        });
    }
}
