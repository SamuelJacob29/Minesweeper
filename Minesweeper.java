/**
 * @author Luke Genna, Samuel Bostic
 * @version 9/28/17
 * Project One - Minesweeper
 * This class initializes variables and creates the original 2D array. It then
 *  fills each slot in the array with Cell objects, these objects can be either
 *  a number ranging from 0-8 or a mine represented by the letter M. It then
 *  prints the board for the user to see and take a guess on which slot is to be
 *  revealed. This class also has a peak function which shows all the locations
 *  of mines when prompted.
 */

import java.util.Random;
import java.util.Scanner;

public class Minesweeper {

    private final Cell[][] board;
    private boolean win;
    private boolean lose;
    private int moves;

    public Minesweeper() {
        board = new Cell[8][8];
        this.win = false;
        this.lose = false;
        this.moves = 0;
    }

    public boolean getWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean getLose() {
        return lose;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    /**
     * This class is called for each slot in the 2D array. It holds getters 
     *  and setters for each possible value it can contain.
     */
    private class Cell {

        private boolean isMine;
        private boolean isGuessed;
        private int neighborMines;

        public Cell() {
            this.isMine = false;
            this.isGuessed = false;
            this.neighborMines = 0;
        }

        public boolean getIsMine() {
            return isMine;
        }

        public void setIsMine(boolean isMine) {
            this.isMine = isMine;
        }

        public boolean getIsGuessed() {
            return isGuessed;
        }

        public void setIsGuessed(boolean isGuessed) {
            this.isGuessed = isGuessed;
        }

        public int getNeighborMines() {
            return neighborMines;
        }

        public void setNeighborMines(int neighborMines) {
            this.neighborMines = neighborMines;
        }
    }

    /**
     * This method is called whenever a new game starts. It initializes all the
     *  slots equal to the Cell class. It also reverts all the values for 
     *  isGuessed to false if they were previously changed.
     */
    
    private void startGame() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new Cell();
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getIsGuessed() == true) {
                    board[i][j].setIsGuessed(false);
                }
            }

        }

        placeMines();
    }

    /**
     * This method allows the user to peak at the board to see where all the
     *  current mines are located. The second loop reverts all mines back to
     *  their previous state.
     */
    
    private void peak() {

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getIsMine() == true) {
                    board[i][j].setIsGuessed(true);
                    System.out.print("M ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println("");
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getIsMine() == true) {
                    board[i][j].setIsGuessed(false);
                }
            }
        }

    }
    /**
     * This method places 10 mines randomly throughout the board then
     *  increments all nearby cells by one.
     */

    private void placeMines() {
        Random rand = new Random();
        int row, col, count = 0;

        while (count < 10) {
            row = rand.nextInt(8);
            col = rand.nextInt(8);
            if (board[row][col].getIsMine() == false) {
                board[row][col].setIsMine(true);
                count++;
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getIsMine()) {
                    if (i + 1 < board.length) {
                        board[i + 1][j].setNeighborMines(board[i + 1][j].getNeighborMines() + 1);
                    }
                    if (i + 1 < board.length && j + 1 < board.length) {
                        board[i + 1][j + 1].setNeighborMines(board[i + 1][j + 1].getNeighborMines() + 1);
                    }
                    if (j + 1 < board.length) {
                        board[i][j + 1].setNeighborMines(board[i][j + 1].getNeighborMines() + 1);
                    }
                    if (i - 1 >= 0) {
                        board[i - 1][j].setNeighborMines(board[i - 1][j].getNeighborMines() + 1);
                    }
                    if (j - 1 >= 0) {
                        board[i][j - 1].setNeighborMines(board[i][j - 1].getNeighborMines() + 1);
                    }
                    if (i - 1 >= 0 && j - 1 >= 0) {
                        board[i - 1][j - 1].setNeighborMines(board[i - 1][j - 1].getNeighborMines() + 1);
                    }
                    if (i + 1 < board.length && j - 1 >= 0) {
                        board[i + 1][j - 1].setNeighborMines(board[i + 1][j - 1].getNeighborMines() + 1);
                    }
                    if (i - 1 >= 0 && j + 1 < board.length) {
                        board[i - 1][j + 1].setNeighborMines(board[i - 1][j + 1].getNeighborMines() + 1);
                    }
                }
            }
        }
    }
    
    /**
     * @return This returns the board that the user sees.
     * This method simply returns the board so it can be returned to the user.
     */
    

    public String toString() {
        String gameBoard = "";

        for (int i = 0; i < board.length; i++) {
            gameBoard += "\n";
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getIsGuessed() == true) {
                    if (board[i][j].getIsMine() == true) {
                        gameBoard += "M ";
                    } else {
                        gameBoard += board[i][j].getNeighborMines() + " ";
                    }
                }
                if (board[i][j].getIsGuessed() == false) {
                    gameBoard += "- ";
                }
            }
        }
        return gameBoard;
    }

    /**
     * This first prompts the user if they want to peak at the board. Then it
     *  prompts the user for a row and a column to guess a slot on the board.
     *  If the user happens to guess where a mine is placed, it prompts the
     *  user that they lost, then asks if they would like to play another game.
     *  
     */
    
    
    public static void main(String[] args) {
        Minesweeper m = new Minesweeper();
        Scanner scan = new Scanner(System.in);
        int row, col;
        boolean startGame = true;
        boolean quitGame = false;

        while (quitGame == false) {

            m.startGame();

            while (startGame == true) {

                System.out.println("Peak Board? Yes or No");
                String answer = scan.next();

                if (answer.equals("Yes") || answer.equals("yes") || answer.equals("Y") || answer.equals("y")) {
                    m.peak();
                }

                while (m.getWin() == false || m.getLose() == false) {
                    System.out.println(m);

                    System.out.print("Enter a row: ");
                    row = scan.nextInt() - 1;
                    System.out.print("Enter a column: ");
                    col = scan.nextInt() - 1;
                    while (row + 1 <= 0 || row + 1 >= 9 || col + 1 <= 0 || col + 1 >= 9) {
                        System.out.println("Error: Input must be with the range 1-8, try again.");
                        System.out.print("Enter a row: ");
                        row = scan.nextInt() - 1;
                        System.out.print("Enter a column: ");
                        col = scan.nextInt() - 1;
                    }
                    while (m.board[row][col].getIsGuessed() == true) {
                        System.out.println("Error: Slot already guessed, try again.");
                        System.out.print("Enter a row: ");
                        row = scan.nextInt() - 1;
                        System.out.print("Enter a column: ");
                        col = scan.nextInt() - 1;
                    }

                    m.board[row][col].setIsGuessed(true);

                    if (m.board[row][col].getIsMine() == true) {
                        m.setLose(true);
                        m.setWin(true);
                        for (int i = 0; i < m.board.length; i++) {
                            for (int j = 0; j < m.board[i].length; j++) {
                                if (m.board[i][j].getIsMine() == true) {
                                    m.board[i][j].setIsGuessed(true);
                                }
                            }
                        }
                        System.out.println(m);
                        System.out.println("You lose!");
                        startGame = false;

                    }

                    m.setMoves(m.getMoves() + 1);

                    if (m.board.length * m.board.length - 10 == m.getMoves()) {
                        m.setLose(true);
                        m.setWin(true);
                        System.out.println("You Win!");
                        startGame = false;
                    }
                }
            }
            System.out.println("Start new game? Yes or no");
            String answer = scan.next();

            if (answer.equals("Yes") || answer.equals("yes") || answer.equals("Y") || answer.equals("y")) {
                System.out.println("Starting new game...");
                m.startGame();
                startGame = true;
                m.setLose(false);
                m.setWin(false);
            } else {
                System.out.println("Bye bye.");
                quitGame = true;
            }
        }
    }
}
