package tictactoe;

import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static final char[][] board = new char[ROWS][COLS];

    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {
        buildBoard();
        printBoard();
        boolean hasWinner = false;
        char turn = 'X';
        while (!hasWinner) {
            switch(turn) {
                case 'X':
                    userMove(turn);
                    turn = 'O';
                    break;
                case 'O':
                    userMove(turn);
                    turn = 'X';
                    break;
            }
            printBoard();

            String status = checkField();
            switch (status) {
                case "Impossible":
                    System.out.println("Impossible to win!");
                    return;
                case "X":
                    System.out.println("X wins");
                    hasWinner = true;
                    break;
                case "O":
                    System.out.println("O wins");
                    hasWinner = true;
                    break;
                case "Draw":
                    System.out.println("Draw");
                    return;
            }
        }
    }

    private static void userMove(char sign) {
        // (1, 3) (2, 3) (3, 3)
        // (1, 2) (2, 2) (3, 2)
        // (1, 1) (2, 1) (3, 1)
        // column, row

        boolean validMove = false;
        while (!validMove) {
            System.out.print("Enter the coordinates: ");
            String[] userInput = sc.nextLine().split("\\s+");
            try {
                if (userInput.length < 2) {
                    throw new NumberFormatException();
                }
                int colC = Integer.parseInt(userInput[0]);
                int rowC = Integer.parseInt(userInput[1]);
                if (colC < 1 || colC > 3 || rowC < 1 || rowC > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
                if (board[ROWS - rowC][(colC - 1) % COLS] != ' ') {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
                board[ROWS - rowC][(colC - 1) % ROWS] = sign;
                validMove = true;
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            }
        }
    }

    private static String checkField() {
        // convert grid to single line string
        StringBuilder boardToString = new StringBuilder();
        for (char[] row : board) {
            for (char c : row) {
                boardToString.append(c);
            }
        }
        String in = boardToString.toString();

        // when the field has three X in a row as well as three O in a row.
        // field has a lot more X's that O's or vice versa
        // (if the difference is 2 or more, should be 1 or 0).
        if (Math.abs(in.replaceAll("[X\\s]", "").length() - in.replaceAll("[O\\s]", "").length()) >= 2
                || checkForWin(in, "XXX") && checkForWin(in, "OOO")) {
            return "Impossible";
        }

        // when the field has three X in a row
        if (checkForWin(in, "XXX")) { return "X"; }

        // when the field has three O in a row
        if (checkForWin(in, "OOO")) { return "O"; }

        // when no side has a three in a row and the field has no empty cells
        if (in.replaceAll("[XO]", "").length() == 0) { return "Draw"; }

        // when no side has a three in a row but the field has empty cells;
        return "Game not finished";

    }

    private static boolean checkForWin(String in, String seq) {
        // check rows
        if (in.substring(0, ROWS).equals(seq) || in.substring(ROWS, 2*ROWS).equals(seq) ||
                in.substring(2*ROWS, 3*ROWS).equals(seq))
        { return true; }

        // check columns
        char c = seq.charAt(0);
        for (int i = 0; i < in.length() - 2*COLS; i++) {
            if (in.charAt(i) == c && in.charAt(i + COLS) == c && in.charAt(i + 2*COLS) == c) {
                return true;
            }
        }

        // check diagonals
        if (in.charAt(0) == c && in.charAt(ROWS + 1) == c && in.charAt(in.length() - 1) == c
                || in.charAt(ROWS - 1) == c && in.charAt(ROWS + 1) == c && in.charAt(2 * ROWS) == c) {
            return true;
        }
        return false;
    }

    private static void buildBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private static void printBoard() {
        System.out.println("-".repeat(ROWS * COLS));
        for (char[] row : board) {
            System.out.print("| ");
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println("|");
        }
        System.out.println("-".repeat(ROWS * COLS));
    }

}
