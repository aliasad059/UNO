import java.util.ArrayList;
import java.util.Scanner;

/**
 * the game will start from here in two mode
 */
public class Game {
    static Scanner scanner = new Scanner(System.in);

    /**
     * the turns array tells us which player's turn is , we give the turn to the players by this array.
     * in this array the value of each index is the player turn ID which is unique to each player and explained in it's place
     * this method will get the turns array , an index of it , and returns the player with the right turnID
     * @param turns turns array
     * @param turnIndex this index contains an turnID that is for a player
     * @param players list of players
     * @return the player that should play a card
     */
    private static Player whosTurn(int[] turns, int turnIndex, ArrayList<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            if (turns[turnIndex] == players.get(i).turnID)
                return players.get(i);
        }
        return null;
    }

    /**
     * print the ranking of the players when the game is finished according to their points using a Comparator
     * @param players players list
     */
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

    /**
     * start the game in solo mose
     */

    public static void startSoloMode() {
        ArrayList<Player> players = new ArrayList<Player>();
        System.out.println("Welcome to UNO solo mode!");
        System.out.println("Enter your name");
        String humanName = scanner.nextLine();
        int playersNumber;
        while (true) {
            System.out.println("Enter number of players");
            playersNumber = scanner.nextInt();
            scanner.nextLine();
            if (playersNumber < 2) {
                System.out.println("The game must have at least two players, enter another number");
                continue;
            }
            break;
        }
        Board board = new Board(playersNumber);
        // if player turnID changes , no problem will happen , but it had to be a number between 1 to playersNumber
        // in other word when a player's turn Id is 1 it does not mean he is the first player to start as said before

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
                    System.out.println((j+1)+". "+playerToPrint.PlayerName + " has " + playerToPrint.playerCard.size() + " cards");
                }
                board.printLastPlayedCard();
                //sleeping for 5 seconds to see what the bots are doing

                try {
                    Thread.sleep(5000);
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

    /**
     * start the game in multi mode
     */
    public static void startMultiMode() {
        ArrayList<Player> players = new ArrayList<Player>();
        System.out.println("Welcome to UNO multi mode!");
        int playersNumber;
        while (true) {
            System.out.println("Enter number of players");
            playersNumber = scanner.nextInt();
            scanner.nextLine();
            if (playersNumber < 2) {
                System.out.println("The game must have at least two players, enter another number");
                continue;
            }
            break;
        }
        Board board = new Board(playersNumber);
        // if player turnID changes , no problem will happen , but it had to be a number between 1 to playersNumber
        // in other word when a player's turn Id is 1 it does not mean he is the first player to start as said before
        for (int i = 1; i <= playersNumber; i++) {
            System.out.println("Enter player " + i + " name");
            String humanName = scanner.nextLine();
            players.add(new Human(humanName, i));
        }
        while (true) {
            int[] turns = Board.getTurns();
            for (int i = 0; i < turns.length; i++) {
                System.out.println("The game wise is : " + Board.getWise());
                for (int j = 0; j < turns.length; j++) {
                    Player playerToPrint = whosTurn(turns, j, players);
                    System.out.println((j+1)+". "+playerToPrint.PlayerName + " has " + playerToPrint.playerCard.size() + " cards");
                }
                board.printLastPlayedCard();
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

    /**
     * the main method :)
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the UNO game!");
        System.out.println("This game is played in two modes , solo mode with bots and multi mode");
        while (true) {
            System.out.println("Which mode do you want to play? enter 1 to play SOLO and 2 to play MULTI");
            int gameMode = scanner.nextInt();
            scanner.nextLine();
            if (gameMode == 1)
                startSoloMode();
            else if (gameMode == 2) startMultiMode();
            else {
                System.out.println("Wrong input, choose another");
                continue;
            }
            break;
        }
    }
}
