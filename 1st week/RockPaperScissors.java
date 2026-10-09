import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

    private static final String[] MOVES = {"Rock", "Paper", "Scissors"};
    private static final Random random = new Random();

    // Determines the winner of a single round
    public static String playRound(String playerMove, String computerMove) {
        if (playerMove.equalsIgnoreCase(computerMove)) {
            return "Draw";
        }

        if ((playerMove.equalsIgnoreCase("Rock") && computerMove.equalsIgnoreCase("Scissors")) ||
            (playerMove.equalsIgnoreCase("Paper") && computerMove.equalsIgnoreCase("Rock")) ||
            (playerMove.equalsIgnoreCase("Scissors") && computerMove.equalsIgnoreCase("Paper"))) {
            return "Player Wins";
        } else {
            return "Computer Wins";
        }
    }

    public static void main(String[] args) {
        int totalRounds = 5;
        String[] playerMoves = new String[totalRounds];
        String[] computerMoves = new String[totalRounds];
        String[] results = new String[totalRounds];

        int wins = 0, losses = 0, draws = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== College Coding Arcade: Rock-Paper-Scissors ===");
        System.out.println("Enter your move (Rock, Paper, or Scissors) for 5 rounds:\n");

        for (int i = 0; i < totalRounds; i++) {
            System.out.print("Round " + (i + 1) + " - Enter move: ");
            String playerMove = scanner.nextLine().trim();

            // Fallback for empty or invalid input
            if (!playerMove.equalsIgnoreCase("Rock") && 
                !playerMove.equalsIgnoreCase("Paper") && 
                !playerMove.equalsIgnoreCase("Scissors")) {
                playerMove = MOVES[random.nextInt(MOVES.length)];
                System.out.println("Invalid input! Auto-selected move: " + playerMove);
            }

            String computerMove = MOVES[random.nextInt(MOVES.length)];
            String result = playRound(playerMove, computerMove);

            playerMoves[i] = playerMove;
            computerMoves[i] = computerMove;
            results[i] = result;

            if (result.equals("Player Wins")) wins++;
            else if (result.equals("Computer Wins")) losses++;
            else draws++;

            System.out.println("Result: " + result + "\n");
        }

        // Print Final Summary Table
        System.out.println("==================== SCOREBOARD ====================");
        System.out.printf("%-8s | %-12s | %-13s | %-13s%n", "Round", "Player Move", "Computer Move", "Result");
        System.out.println("----------------------------------------------------");
        for (int i = 0; i < totalRounds; i++) {
            System.out.printf("%-8d | %-12s | %-13s | %-13s%n", (i + 1), playerMoves[i], computerMoves[i], results[i]);
        }
        System.out.println("----------------------------------------------------");

        double winPercentage = ((double) wins / totalRounds) * 100;
        System.out.printf("Final Summary: Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%%n", 
                wins, losses, draws, winPercentage);

        scanner.close();
    }
}