package edu.fau.COT4930;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Lucas Haase 
 * COT4930 Java Programming 
 * Final Project - TicTacToe
 */
public class TTTGUI extends JFrame implements Serializable {

    private final PlayerImpl player1 = new PlayerImpl("Player1");
    private final PlayerImpl player2 = new PlayerImpl("Player2");

    //layout
    private static final GridLayout CENTER_LAYOUT = new GridLayout(3, 3);

    //frame dimensions
    private static final int H = 400;
    private static final int W = 400;

    //menu bar
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuNewGame, menuExit, menuSave, menuLoad;

    //playing board spaces
    private JButton spaces[] = new JButton[9];

    //panels
    private JPanel mainPanel, buttonPanel, playerPanel;

    private String currentPlayer = "Player 1: start";
    private int turns = 0;
    private String mark = "";
    private boolean win = false;

    //default constructor
    public TTTGUI() {
        initializeGUI();
        setSize(W, H);
    }

    private void initializeGUI() {
        //menu bar initialization
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuNewGame = new JMenuItem("New Game");
        menuSave = new JMenuItem("Save Game");
        menuLoad = new JMenuItem("Load Game");
        menuExit = new JMenuItem("Exit");

        //title, player indication
        JLabel title = new JLabel(currentPlayer);
        playerPanel = new JPanel();

        //playing board
        mainPanel = new JPanel();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(CENTER_LAYOUT);

        //listener for game board
        class ButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                turns++;
                if (turns % 2 == 0) {
                    title.setText(player1.getName() + "'s turn");
                    mark = "O";
                } else {
                    title.setText(player2.getName() + "'s turn");
                    mark = "X";
                }
                JButton btn = (JButton) e.getSource();
                btn.setText(mark);
                btn.setEnabled(false);
                displayWinner();
            }
        }
        ActionListener buttonListener = new ButtonListener();
        for (int i = 0; i < 9; i++) {
            spaces[i] = new JButton("");
            spaces[i].addActionListener(buttonListener);
            buttonPanel.add(spaces[i]);
        }

        //listener for quit
        menuExit.addActionListener((ActionEvent e) -> {
            for (int i = 0; i < 9; i++) {
                win = false;
                spaces[i].setText("");
                spaces[i].setEnabled(true);
                turns = 0;
            }
        });

        //listener for new game
        menuNewGame.addActionListener((ActionEvent e) -> {
            for (int i = 0; i < 9; i++) {
                win = false;
                spaces[i].setText("");
                spaces[i].setEnabled(true);
                turns = 0;
            }  
        });

        //listener for save game
        menuSave.addActionListener((ActionEvent e) -> {
            saveState();
        });
        

        //listener for load game
        menuLoad.addActionListener((ActionEvent e) -> {
            try {
                loadState();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TTTGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //assembling menu bar
        menu.add(menuNewGame);
        menu.add(menuSave);
        menu.add(menuLoad);
        menu.add(menuExit);
        menuBar.add(menu);

        //adding title to panel
        playerPanel.add(title);

        //assemble the game panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(menuBar, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(playerPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    //check if three in a row is achieved
    public void checkWins() {
        //horizontal
        if (spaces[0].getText().equals(spaces[1].getText())
                && spaces[1].getText().equals(spaces[2].getText())
                && spaces[0].getText().equals("") == false) {
            win = true;
        } else if (spaces[3].getText().equals(spaces[4].getText())
                && spaces[4].getText().equals(spaces[5].getText())
                && spaces[3].getText().equals("") == false) {
            win = true;
        } else if (spaces[6].getText().equals(spaces[7].getText())
                && spaces[7].getText().equals(spaces[8].getText())
                && spaces[6].getText().equals("") == false) {
            win = true;
        }

        //vertical
        if (spaces[0].getText().equals(spaces[3].getText())
                && spaces[3].getText().equals(spaces[6].getText())
                && spaces[0].getText().equals("") == false) {
            win = true;
        } else if (spaces[1].getText().equals(spaces[4].getText())
                && spaces[4].getText().equals(spaces[7].getText())
                && spaces[1].getText().equals("") == false) {
            win = true;

        } else if (spaces[2].getText().equals(spaces[5].getText())
                && spaces[5].getText().equals(spaces[8].getText())
                && spaces[2].getText().equals("") == false) {
            win = true;
        }

        //diagonal
        if (spaces[0].getText().equals(spaces[4].getText())
                && spaces[4].getText().equals(spaces[8].getText())
                && spaces[0].getText().equals("") == false) {
            win = true;
        } else if (spaces[2].getText().equals(spaces[4].getText())
                && spaces[4].getText().equals(spaces[6].getText())
                && spaces[2].getText().equals("") == false) {
            win = true;
        }
    }

    //display the winner of the Game
    public void displayWinner() {
        if (turns >= 5 && turns <= 9) {

            checkWins();

            if (win == true) {
                JOptionPane.showMessageDialog(null, "Player " + mark
                        + " : HAS WON!");
                System.exit(0);
            } else if (turns == 9 && win == false) {
                JOptionPane.showMessageDialog(null, "GAME IS A TIE.");
                System.exit(0);
            }
        }
    }

    public void saveState() {
        try {
            try (ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("gameData.ser"))) {
                out.writeObject(spaces);
            }
        } catch (IOException e) {
            System.out.println("Serialization IO Error");
        }
    }

    public void loadState() throws ClassNotFoundException {
        try {
            try (ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("gameData.ser"))) {
                spaces = (JButton[]) in.readObject();
            }
        } catch (IOException e) {
            System.out.println("Deserialization IO Error");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new TTTGUI();
        frame.setTitle("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}