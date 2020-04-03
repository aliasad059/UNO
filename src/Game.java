import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    static Scanner scanner = new Scanner(System.in);

    private static Player whosTurn(int[] turns, int turnIndex, ArrayList<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            if (turns[turnIndex] == players.get(i).turnID)
                return players.get(i);
        }
        return null;
    }

    private static void printRanking(ArrayList<Player> players) {

        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.get(i).playerCard.size(); j++) {
                players.get(i).point += players.get(i).playerCard.get(j).getPoint();
            }
        }
        players.sort(new PointComparator());
        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". NAME: " + players.get(i).getPlayerName() + " || POINT: " + players.get(i).point);
        }
    }

    public static void startSoloMode() {
        ArrayList<Player> players = new ArrayList<Player>();
        System.out.println("Welcome to UNO solo mode!");
        System.out.println("Enter your name");
        String humanName = scanner.nextLine();
        System.out.println("Enter number of players");
        int playersNumber = scanner.nextLine().charAt(0)-48;
        Board board = new Board(playersNumber);
        // if player turnID changes , no problem will happen , but it had to be a number between 1 to playersNumber
        // in other word when a player's turn Id is 1 it does not mean he is the first player to start

        players.add(new Human(humanName, 1));
        for (int i = 2; i <= playersNumber; i++) {
            players.add(new Computer("BOT " + (i - 1), i));
        }
        while (true) {
            int[] turns = Board.getTurns();
            for (int i = 0; i < turns.length; i++) {
                System.out.println("The game wise is : " + Board.getWise());

                for (int j = 0; j < turns.length; j++) {
                    Player playerToPrint = whosTurn(turns, j, players);
                    System.out.println(playerToPrint.PlayerName + " has " + playerToPrint.playerCard.size() + " cards");
                }
                //sleeping for 5 seconds to see what the bots are doing
                board.printLastPlayedCard();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println();
                }
                Player playerToPlay = whosTurn(turns, i, players);
                playerToPlay.chooseCard();
                if (playerToPlay.playerCard.size() == 0) {
                    System.out.println("This game is finished!");
                    System.out.println("Player " + playerToPlay.getPlayerName() + " is the winner!");
                    printRanking(players);
                    return;
                }
            }
        }
    }

    public static void startMultiMode() {
        ArrayList<Player> players = new ArrayList<Player>();
        System.out.println("Welcome to UNO multi mode!");
        System.out.println("Enter number of players");
        int playersNumber = scanner.nextInt();
        Board board = new Board(playersNumber);
        // if player turnID changes , no problem will happen , but it had to be a number between 1 to playersNumber
        // in other word when a player's turn Id is 1 it does not mean he is the first player to start
        for (int i = 1; i <= playersNumber; i++) {
            System.out.println("Enter player " + i + " name");
            String humanName = scanner.nextLine();
            players.add(new Human(humanName, i));
        }
        int[] turns = Board.getTurns();
        while (true) {
            for (int i = 0; i < turns.length; i++) {
                System.out.println("The game wise is : " + Board.getWise());
                for (int j = 0; j < turns.length; j++) {
                    Player playerToPrint = whosTurn(turns, j, players);
                    System.out.println(playerToPrint.PlayerName + " has " + playerToPrint.playerCard.size() + " cards");
                }
                Player playerToPlay = whosTurn(turns, i, players);
                playerToPlay.chooseCard();
                if (playerToPlay.playerCard.size() == 0) {
                    System.out.println("This game is finished!");
                    System.out.println("Player " + playerToPlay.getPlayerName() + " is the winner!");
                    printRanking(players);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        startSoloMode();
    }
}
