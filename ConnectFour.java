package four;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectFour extends JFrame {
    private final Map<String, JButton> buttonMap = new HashMap<>();
    private ColumnManager columnManager;
    private WinChecker winChecker;
    private boolean isXTurn = true;
    private boolean gameOver = false;

    // Colors
    private static final Color BASELINE_COLOR = new Color(144, 238, 144);  // Light green (as in image)
    //private static final Color PLAYER_X_COLOR = new Color(255, 0, 0);      // RED
    //private static final Color PLAYER_O_COLOR = new Color(255, 255, 0);    // YELLOW
    private static final Color WIN_COLOR = new Color(0, 255, 0);           // Bright GREEN

    public ConnectFour() {
        setTitle("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Force system look and feel for better color support
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            // Continue with default
        }
        initBoard();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void initBoard(){
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Game board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(6, 7, 5, 5));
        char[] cols = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};

        for (int row = 6; row >= 1; row--) {
            for (int col = 0; col < 7; col++) {
                String label = "" + cols[col] + row;
                JButton cell = getButton(label);

                buttonMap.put(label, cell);

                final char column = cols[col];
                cell.addActionListener(e -> handleColumnClick(column));

                boardPanel.add(cell);
            }
        }

        columnManager = new ColumnManager(buttonMap);
        winChecker = new WinChecker(buttonMap);

        // Bottom panel with reset button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton resetButton = getResetButton();
        bottomPanel.add(resetButton);

        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }


    private JButton getResetButton() {
        JButton resetButton = new JButton("Reset");
        //set reset button: external
        resetButton.setName("ButtonReset");
        //set reset button: internal
        resetButton.setFont(new Font("Arial", Font.BOLD, 18));
        resetButton.setPreferredSize(new Dimension(150, 50));
        resetButton.setEnabled(true);
        resetButton.setBackground(new Color(255, 215, 0)); // Gold color as in image
        resetButton.setOpaque(true);
        resetButton.addActionListener(e -> resetGame());
        return resetButton;
    }


    private static JButton getButton(String label) {
        JButton cell = new JButton(" ");
        cell.setName("Button" + label);
        cell.setFocusPainted(false);

        Dimension size = new Dimension(80, 80);
        cell.setPreferredSize(size);
        cell.setMinimumSize(size);
        cell.setMaximumSize(size);

        cell.setFont(new Font("Arial", Font.BOLD, 40));

        // CRITICAL: These settings force the background to show
        cell.setBackground(BASELINE_COLOR);
        cell.setOpaque(true);
        //Forces button to paint its content area
        cell.setContentAreaFilled(true);
        cell.setBorderPainted(true);
        return cell;
    }

    private void handleColumnClick(char column) {
        if (gameOver) {
            return;
        }

        String firstFreeCell = columnManager.getFirstFreeCell(column);

        if (firstFreeCell == null) {
            return;
        }

        JButton button = buttonMap.get(firstFreeCell);

        if (button == null) {
            return;
        }

        String piece = isXTurn ? "X" : "O";
        button.setText(piece);

        // Set player color
        if (isXTurn) {
            //button.setBackground(PLAYER_X_COLOR);
            button.setForeground(Color.BLACK);
        } else {
            //button.setBackground(PLAYER_O_COLOR);
            button.setForeground(Color.BLACK);
        }

        // Force the UI to update
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.revalidate();
        button.repaint();

        // Check for win
        List<String> winningCells = winChecker.getWinningCells(piece);
        if (winningCells != null && !winningCells.isEmpty()) {
            gameOver = true;

            for (String cellLabel : winningCells) {
                JButton winCell = buttonMap.get(cellLabel);
                if (winCell != null) {
                    winCell.setBackground(WIN_COLOR);
                    winCell.setForeground(Color.BLACK);
                    winCell.setContentAreaFilled(true);
                    winCell.setOpaque(true);
                    winCell.revalidate();
                    winCell.repaint();
                }
            }

            String winner = isXTurn ? "Player X" : "Player O";

            /*SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                        winner + " wins!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            });*/
        } else if (winChecker.isBoardFull()) {
            gameOver = true;

            /*SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                        "It's a draw!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            });*/
        } else {
            isXTurn = !isXTurn;
        }
    }

    private void resetGame() {
        for (JButton button : buttonMap.values()) {
            //empty the button
            button.setText(" ");
            button.setEnabled(true);
            button.setBackground(BASELINE_COLOR);
            button.setForeground(Color.BLACK);
            //Forces button to paint its content area
            button.setContentAreaFilled(true);
            button.setOpaque(true);
            //Forces immediate UI update
            button.revalidate();
            button.repaint();
        }

        isXTurn = true;
        gameOver = false;
    }

}

