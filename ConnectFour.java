package Inventory;

import java.util.Scanner;
import java.util.Random;

public class ConnectFour {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int column = 7;
        int row = 6;
        String[][] grid = new String[row][column];
        for (int a = 0; a < row; a++) {
            for (int b = 0; b < column; b++) {
                grid[a][b] = "○";
            }
        }

        String winner = " ";
        boolean win = false;
        boolean inputCheck;
        int tieCheck = 0;

        int choiceCheck;
        do {
            choiceCheck = 0;
            System.out.println("Welcome to Connect Four!");
            System.out.println("Choose a game mode:");
            System.out.println("A. Play against the computer");
            System.out.println("B. Play against another player");

            String game = input.next();

            if (game.equalsIgnoreCase("A")) {
                System.out.println("Enter your name:");
                String playerName = input.next();
                Random random = new Random();

                do {
                    inputCheck = true;

                    if (!win) {
                        printGameBoard(grid, column);

                        while (inputCheck) {
                            System.out.println(playerName + ", enter the column to place your token:");
                            int temp1 = input.nextInt() - 1;

                            if (temp1 < 0 || temp1 >= column) {
                                System.out.println("Error. Invalid input. Column out of range.");
                            } else {
                                int rowIndex = findAvailableRow(grid, temp1);
                                if (rowIndex == -1) {
                                    System.out.println("Error. Column is filled.");
                                } else {
                                    grid[rowIndex][temp1] = "•";
                                    inputCheck = false;
                                    win = checkForWin(grid, "•", rowIndex, temp1);
                                    if (win) {
                                        winner = playerName;
                                        announceWinner(winner);
                                    }
                                    tieCheck++;
                                    if (tieCheck == row * column) {
                                        System.out.println("It's a tie! Game over.");
                                        break;
                                    }
                                }
                            }
                        }

                        // Computer's move (randomly select an available column)
                        if (!win) {
                            int computerMove;
                            do {
                                computerMove = random.nextInt(column);
                            } while (grid[0][computerMove] != "○");

                            int rowIndex = findAvailableRow(grid, computerMove);
                            grid[rowIndex][computerMove] = "♦";
                            win = checkForWin(grid, "♦", rowIndex, computerMove);
                            if (win) {
                                winner = "Computer";
                                announceWinner(winner);
                            }
                            tieCheck++;
                            if (tieCheck == row * column) {
                                System.out.println("It's a tie! Game over.");
                                break;
                            }
                        }
                    }
                } while (!win);
            } else if (game.equalsIgnoreCase("B")) {
                // Code for playing against another player
                String player1, player2;
                System.out.println("Enter the name of player 1:");
                player1 = input.next();
                System.out.println("Enter the name of player 2:");
                player2 = input.next();

                do {
                    inputCheck = true;

                    if (!win) {
                        printGameBoard(grid, column);

                        while (inputCheck) {
                            String currentPlayer = (tieCheck % 2 == 0) ? player1 : player2;
                            System.out.println(currentPlayer + ", enter the column to place your token:");
                            int temp1 = input.nextInt() - 1;

                            if (temp1 < 0 || temp1 >= column) {
                                System.out.println("Error. Invalid input. Column out of range.");
                            } else {
                                int rowIndex = findAvailableRow(grid, temp1);
                                if (rowIndex == -1) {
                                    System.out.println("Error. Column is filled.");
                                } else {
                                    String token = (tieCheck % 2 == 0) ? "•" : "♦";
                                    grid[rowIndex][temp1] = token;
                                    inputCheck = false;
                                    win = checkForWin(grid, token, rowIndex, temp1);
                                    if (win) {
                                        winner = currentPlayer;
                                        announceWinner(winner);
                                    }
                                    tieCheck++;
                                    if (tieCheck == row * column) {
                                        System.out.println("It's a tie! Game over.");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } while (!win);
            } else {
                System.out.println("Error. Invalid input. Please choose a valid game mode.");
                choiceCheck = 1;
            }
        } while (choiceCheck == 1);

        input.close();
    }

    // Function to print the game board
    public static void printGameBoard(String[][] grid, int column) {
        System.out.println("   COLUMN");
        System.out.println("~~~~~~~~~~~~~");
        System.out.println("1 2 3 4 5 6 7");
        System.out.println("~~~~~~~~~~~~~");

        for (int a = 0; a < grid.length; a++) {
            for (int b = 0; b < column; b++) {
                System.out.print(grid[a][b] + " ");
            }
            System.out.println();
        }

        System.out.println("~~~~~~~~~~~~~");
    }

    // Function to find the first available row in a column
    public static int findAvailableRow(String[][] grid, int column) {
        for (int a = grid.length - 1; a >= 0; a--) {
            if (grid[a][column].equals("○")) {
                return a;
            }
        }
        return -1; // Column is filled
    }

    // Function to announce the winner
    public static void announceWinner(String winner) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\t\t " + winner + " WINS!");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    // Function to check for a win condition
    public static boolean checkForWin(String[][] grid, String token, int row, int column) {
        // Check horizontally
        int count = 0;
        for (int c = Math.max(0, column - 3); c <= Math.min(column + 3, grid[0].length - 1); c++) {
            if (grid[row][c].equals(token)) {
                count++;
                if (count == 4) {
                    return true; // Horizontal win
                }
            } else {
                count = 0;
            }
        }

        // Check vertically
        count = 0;
        for (int r = Math.max(0, row - 3); r <= Math.min(row + 3, grid.length - 1); r++) {
            if (grid[r][column].equals(token)) {
                count++;
                if (count == 4) {
                    return true; // Vertical win
                }
            } else {
                count = 0;
            }
        }

        // Check diagonally (from bottom-left to top-right)
        count = 0;
        int r = row + 3;
        int c = column - 3;
        while (r >= Math.max(0, row - 3) && c <= Math.min(column + 3, grid[0].length - 1)) {
            if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && grid[r][c].equals(token)) {
                count++;
                if (count == 4) {
                    return true; // Diagonal win (bottom-left to top-right)
                }
            } else {
                count = 0;
            }
            r--;
            c++;
        }

        // Check diagonally (from bottom-right to top-left)
        count = 0;
        r = row + 3;
        c = column + 3;
        while (r >= Math.max(0, row - 3) && c >= Math.max(0, column - 3)) {
            if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && grid[r][c].equals(token)) {
                count++;
                if (count == 4) {
                    return true; // Diagonal win (bottom-right to top-left)
                }
            } else {
                count = 0;
            }
            r--;
            c--;
        }

        return false; // No win condition met
    }
}
