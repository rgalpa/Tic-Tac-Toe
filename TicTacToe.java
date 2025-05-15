import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private boolean playerXTurn = true;
    private boolean singlePlayer = true;
    private Random rand = new Random();
    private JLabel statusLabel;

    public TicTacToe() {
        setTitle("Tic Tac Toe - Java Game");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        int mode = JOptionPane.showOptionDialog(this,
                "Choose Game Mode:",
                "Tic Tac Toe",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Single Player", "Multiplayer"},
                "Single Player");

        singlePlayer = (mode == 0);

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        Font font = new Font("Arial", Font.BOLD, 60);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(font);
                buttons[i][j].addActionListener(this);
                gamePanel.add(buttons[i][j]);
            }
        }

        statusLabel = new JLabel("Player X's turn");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(gamePanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (!clicked.getText().equals("")) return;

        clicked.setText(playerXTurn ? "X" : "O");
        clicked.setEnabled(false);

        if (checkWin()) {
            endGame((playerXTurn ? "Player X" : "Player O") + " wins!");
            return;
        } else if (isBoardFull()) {
            endGame("It's a draw!");
            return;
        }

        playerXTurn = !playerXTurn;
        statusLabel.setText(playerXTurn ? "Player X's turn" : (singlePlayer ? "Computer's turn" : "Player O's turn"));

        if (singlePlayer && !playerXTurn) {
            computerMove();
        }
    }

    private void computerMove() {
        int row, col;
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (!buttons[row][col].getText().equals(""));

        buttons[row][col].setText("O");
        buttons[row][col].setEnabled(false);

        if (checkWin()) {
            endGame("Computer wins!");
        } else if (isBoardFull()) {
            endGame("It's a draw!");
        } else {
            playerXTurn = true;
            statusLabel.setText("Player X's turn");
        }
    }

    private boolean checkWin() {
        
        for (int i = 0; i < 3; i++) {

            if (checkThree(buttons[i][0], buttons[i][1], buttons[i][2])) return true;

            if (checkThree(buttons[0][i], buttons[1][i], buttons[2][i])) return true;
        }

        return checkThree(buttons[0][0], buttons[1][1], buttons[2][2]) ||
               checkThree(buttons[0][2], buttons[1][1], buttons[2][0]);
    }

    private boolean checkThree(JButton b1, JButton b2, JButton b3) {
        String t1 = b1.getText(), t2 = b2.getText(), t3 = b3.getText();
        return !t1.equals("") && t1.equals(t2) && t2.equals(t3);
    }

    private boolean isBoardFull() {
        for (JButton[] row : buttons) {
            for (JButton b : row) {
                if (b.getText().equals("")) return false;
            }
        }
        return true;
    }

    private void endGame(String message) {
        statusLabel.setText(message);
        for (JButton[] row : buttons) {
            for (JButton b : row) {
                b.setEnabled(false);
            }
        }

        int option = JOptionPane.showConfirmDialog(this, message + "\nPlay Again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            dispose();
            new TicTacToe(); 
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToe());
    }
}
