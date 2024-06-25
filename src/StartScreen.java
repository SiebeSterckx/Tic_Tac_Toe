import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {


    public StartScreen(CardLayout cl, JPanel cards, PlayScreen playScreen) {

        setLayout(new GridLayout(3, 2));

        JLabel nameLabel1 = new JLabel("Name P1:");
        JTextField nameField1 = new JTextField();

        JLabel nameLabel2 = new JLabel("Name P2:");
        JTextField nameField2 = new JTextField();

        JButton submitButton = new JButton("Start Game");

        add(nameLabel1);
        add(nameField1);
        add(nameLabel2);
        add(nameField2);
        add(submitButton);

        // Actie toekennen aan de submit-knop
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name1 = nameField1.getText().trim();
                String name2 = nameField2.getText().trim();

                if (name1.isEmpty() || name2.isEmpty()) {
                    JOptionPane.showMessageDialog(StartScreen.this, "Both name fields must be filled in.", "2 players required", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    Player.resetPlayerList();
                    Player player1 = new Player(name1);
                    Player player2 = new Player(name2);
                    System.out.println(player1);
                    System.out.println(player2);
                    playScreen.setPlayers(player1, player2);
                    cl.show(cards, "main");
                }
            }
        });
    }
}
