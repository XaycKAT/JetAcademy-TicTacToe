package tictactoe;

import java.util.*;

public class MyMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] s = new char[9];
        Arrays.fill(s, ' ');
        String input = "";
        for (int i = 1; i < 11; i++) {
            printField(s);
            if(checkField(s).contains("wins")) {
                System.out.println(checkField(s));
                return;
            }
            boolean check = false;
            while (!check) {
                System.out.print("Enter the coordinates: ");
                input = scanner.nextLine().replace(" ","").trim();
                check = checkInput(input, s);
            }
            int x = input.charAt(0) - 48;
            int y = input.charAt(1) - 48;
            if (i % 2 == 0)
                s[fromMatrixToLine(x, y)] = 'O';
            else
                s[fromMatrixToLine(x, y)] = 'X';
        }
    }

    public static boolean checkInput(String str, char[] field) {
        if (!str.matches("\\d{2}")) {
            System.out.println("You should enter numbers!");
            return false;
        }
        if (!str.matches("[1-3]{2}")) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }
        int x = str.charAt(0) - 48;
        int y = str.charAt(1) - 48;
        if (field[fromMatrixToLine(x, y)] == 'X' || field[fromMatrixToLine(x, y)] == 'O') {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
        return true;
    }

    public static int fromMatrixToLine(int x, int y) {
        for (int i = 0; i < 3; i++) {
            if (x == i + 1) {
                if (y == 1)
                    return 6 + i;
                if (y == 2)
                    return 3 + i;
                if (y == 3)
                    return 0 + i;
            }
        }
        return -1;
    }

    public static String checkField(char[] s) {
        Set<String> winners = howWins(s);

        if (winners.size() > 1 || Math.abs(count(s, 'X') - count(s, 'O')) > 1) {
            return "Impossible";
        }

        if (winners.size() < 1) {
            if (count(s, ' ') == 0 && count(s, '_') == 0) {
                return "Draw";
            } else {
                return "Game not finished";
            }
        }
        return winners.toArray()[0].toString();
    }

    public static void printField(char[] s) {
        System.out.println("---------\n" +
                "| " + s[0] + " " + s[1] + " " + s[2] + " | \n" +
                "| " + s[3] + " " + s[4] + " " + s[5] + " | \n" +
                "| " + s[6] + " " + s[7] + " " + s[8] + " | \n" +
                "---------");
    }

    public static int count(char[] s, char c) {
        int count = 0;
        for (char x : s) {
            if (x == c)
                count++;
        }
        return count;
    }

    public static Set<String> howWins(char[] s) {
        Set<String> winners = new HashSet();
        for (int i = 0; i < 9; i += 3) {
            if (s[i] == s[i + 1] && s[i] == s[i + 2] && s[i] != ' ' && s[i] != '_' && s[i] != '\u0000')
                winners.add(s[i] + " wins");
        }
        for (int i = 0; i < 3; i++) {
            if (s[i] == s[i + 3] && s[i] == s[i + 6] && s[i] != ' ' && s[i] != '_' &&  s[i] != '\u0000')
                winners.add(s[i] + " wins");
        }
        if (s[0] == s[4] && s[0] == s[8] && s[0] != ' ' && s[0] != '_' &&  s[0] != '\u0000')
            winners.add(s[0] + " wins");
        if (s[2] == s[4] && s[2] == s[6] && s[2] != ' ' && s[2] != '_' &&  s[2] != '\u0000')
            winners.add(s[2] + " wins");
        return winners;
    }
}