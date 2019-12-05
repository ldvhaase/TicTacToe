package edu.fau.COT4930;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Lucas Haase 
 * COT4930 Java Programming 
 * Final Project - TicTacToe
 */
public class TTTGUI extends JFrame {

    PlayerImpl player1 = new PlayerImpl("Player1");
    PlayerImpl player2 = new PlayerImpl("Player2");

    //layouts 
    private static final BorderLayout MAIN_LAYOUT = new BorderLayout();
    private static final GridLayout CENTER_LAYOUT = new GridLayout(3, 3);
    private static final GridLayout BUTTON_LAYOUT = new GridLayout(2, 1);
//    private static final BoxLayout TITLE_LAYOUT = new BoxLayout();

    //frame dimensions
    private static final int HEIGHT = 400;
    private static final int WIDTH = 400;

    //menu bar
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuNewGame;
    private JMenuItem menuExit;

    //playing board spaces
    private JButton spaces[] = new JButton[9];

    //private JButton newGame, exit;
    private JPanel titlePanel, mainPanel, markPanel;

    private String currentPlayer = "Player 1: start";
    private int turns = 0;
    private String mark = "";
    private boolean win = false;

    public TTTGUI() {
        initializeGUI();   
        setSize(WIDTH, HEIGHT);
    }

    private void initializeGUI() {
        //menu bar initialization
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuNewGame = new JMenuItem("New Game");
        menuExit = new JMenuItem("Exit");

        //title, player indication
        JLabel title = new JLabel(currentPlayer);
        titlePanel = new JPanel();

        //playing board
        mainPanel = new JPanel();
        markPanel = new JPanel();
        markPanel.setLayout(CENTER_LAYOUT);

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
            markPanel.add(spaces[i]);
        }

        //listener for quit
        class ExitListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }
        ActionListener exitListener = new ExitListener();
        menuExit.addActionListener(exitListener);

        //listener for new game
        class NewGameListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 9; i++) {
                    win = false;
                    spaces[i].setText("");
                    spaces[i].setEnabled(true);
                    turns = 0;
                }
            }
        }
        ActionListener newGameListener = new NewGameListener();
        menuNewGame.addActionListener(newGameListener);

        //assembling menu bar
        menu.add(menuNewGame);
        menu.add(menuExit);
        menuBar.add(menu);

        //adding title to panel
        titlePanel.add(title);

        //assemble the game panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(menuBar, BorderLayout.NORTH);        
        mainPanel.add(markPanel, BorderLayout.CENTER);
        mainPanel.add(titlePanel, BorderLayout.SOUTH);
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

    public static void main(String[] args) {
        JFrame frame = new TTTGUI();
        frame.setTitle("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
