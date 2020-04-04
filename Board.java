import java.util.ArrayList;
import java.util.Random;

/**
 * this class makes us a board to play in it ,in the game.
 */
public class Board {
    //contains the list of the cards
    //the index 0 is the contains remaining cards that is not for the players now
    //the other index is for the player with the same turnID . means the index 2 is for the player with the turnID of 2
    static private ArrayList<ArrayList<Card>> cards = new ArrayList<ArrayList<Card>>();

    //the turns array tells us which player's turn is , we give the turn to the players by this array.
    // in this array the value of each index is the player turn ID which is unique to each player and explained in it's place
    static private int[] turns;

    static private Random random = new Random();

    static private Card lastPlayedCard;
    static private String currentColor;
    static private int playersNumber;

    //1 for clockwise and -1 for anti clockWise
    static private int wise = 1;

    public Board(int playersNumber) {
        this.playersNumber = playersNumber;
        turns = new int[playersNumber];

        // initializing turns array randomly with different amounts
        int size = playersNumber;
        ArrayList<Integer> list = new ArrayList<Integer>(size);
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }
        for (int i = 0; i < turns.length; i++) {
            int index = random.nextInt(list.size());
            turns[i] = list.get(index);
            list.remove(index);
        }

        //playerNumber + '1' is because of all players deck + the remaining cards on the ground
        for (int i = 0; i < playersNumber + 1; i++)
            cards.add(new ArrayList<Card>());
        makeCards();
        distributeCards();
    }

    /**
     * makes 108 card for the game
     */
    private void makeCards() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                cards.get(0).add(new NumberCard(Constants.colors[i], Constants.types[0], "    " + j + "    "));
                if (j + 1 < 10) {
                    cards.get(0).add(new NumberCard(Constants.colors[i], Constants.types[0], "    " + (j + 1) + "    "));
                }
            }
            cards.get(0).add(new ActionCard(Constants.colors[i], Constants.types[1], Constants.typesDetail[0]));
            cards.get(0).add(new ActionCard(Constants.colors[i], Constants.types[1], Constants.typesDetail[1]));
            cards.get(0).add(new ActionCard(Constants.colors[i], Constants.types[1], Constants.typesDetail[2]));
            cards.get(0).add(new ActionCard(Constants.colors[i], Constants.types[1], Constants.typesDetail[0]));
            cards.get(0).add(new ActionCard(Constants.colors[i], Constants.types[1], Constants.typesDetail[1]));
            cards.get(0).add(new ActionCard(Constants.colors[i], Constants.types[1], Constants.typesDetail[2]));

            cards.get(0).add(new WildCard(Constants.colors[4], Constants.types[2], Constants.typesDetail[3]));
            cards.get(0).add(new WildCard(Constants.colors[4], Constants.types[2], Constants.typesDetail[4]));
        }
    }

    /**
     * after making cards its time to give each player 7 cards from the remaining cards and also a card to be the ground card that the first player
     * will play its cards according to it
     */
    private void distributeCards() {
        Random randomNumber = new Random();
        for (int i = 1; i < cards.size(); i++) {
            for (int j = 0; j < 7; j++) {
                int randomCardNumber = randomNumber.nextInt(cards.get(0).size());
                cards.get(i).add(cards.get(0).get(randomCardNumber));
                cards.get(0).remove(randomCardNumber);
            }
        }

        while (true) {
            int randomCardNumber = randomNumber.nextInt(cards.get(0).size());
            //if it is a NUMBER card
            if (cards.get(0).get(randomCardNumber).getType().equals(Constants.types[0])) {
                setLastPlayedCard(cards.get(0).get(randomCardNumber));
                setCurrentColor(lastPlayedCard.getColor());
                break;
            }
            // if it is an ACTION card
            else if (cards.get(0).get(randomCardNumber).getType().equals(Constants.types[1])) {
                //skip card
                if (cards.get(0).get(randomCardNumber).getTypeDetail().equals(Constants.typesDetail[0])) {
                    Player.isSkipped = true;
                }
                //reverse card
                else if (cards.get(0).get(randomCardNumber).getTypeDetail().equals(Constants.typesDetail[1])) {
                    reverse(turns[0]);
                }
                // draw two card
                else if (cards.get(0).get(randomCardNumber).getTypeDetail().equals(Constants.typesDetail[2])) {
                    Player.setAddCardNumber(2);
                    Player.isSkipped = true;
                }
                setLastPlayedCard(cards.get(0).get(randomCardNumber));
                setCurrentColor(lastPlayedCard.getColor());
                break;
            } else continue;
        }
    }

    /**
     * set the last played card
     * @param lastPlayedCard the last played card
     */
    public static void setLastPlayedCard(Card lastPlayedCard) {
        Board.lastPlayedCard = lastPlayedCard;
    }

    /**
     * get the last player card
     * @return the last player card
     */
    public static Card getLastPlayedCard() {
        return lastPlayedCard;
    }
    /**
     * set the color of the board
     * @param currentColor new color to be set
     */
    public static void setCurrentColor(String currentColor) {
        Board.currentColor = currentColor;
    }
    /**
     * get the color of the board
     * @return  currentColor new color to be set
     */
    public static String getCurrentColor() {
        return currentColor;
    }

    /**
     * get the cards list
     * @return cards list
     */
    public static ArrayList<ArrayList<Card>> getCards() {
        return cards;
    }

    /**
     * get the turns
     * @return turns array
     */
    public static int[] getTurns() {
        return turns;
    }

    /**
     * reverse the board according to the player
     * @param turnIDOfCurrentPlayer turnID of player
     */
    public static void reverse(int turnIDOfCurrentPlayer) {
        // if there are only two players in the game just change their turn
        //    turns[0] = 1              turns[0] = 0
        //                      =>
        //    turns[1] = 0              turns[1] = 1
        if (turns.length == 2) {
            int temp = turns[0];
            turns[0] = turns[1];
            turns[1] = temp;
        }
        //else it has another algorithm
        //suppose the following turns array and the player that used reverse card or the first player to start the game when the card on the ground is reverse
        // is the one with the turnID of 2
        // this algorithm will change the turns array as this way
        //
        //  turns[0] = 1                temp[0] = turns[3] = 2                      turns[0] = temp[2] = 4
        //  turns[1] = 4                temp[1] = turns[2] = 3                      turns[1] = temp[3] = 1
        //  turns[2] = 3        =>      temp[2] = turns[1] = 4          =>          turns[2] = temp[4] = 5
        // *turns[3] = 2                temp[3] = turns[0] = 1                      turns[3] = temp[0] = 2
        //  turns[4] = 5                temp[4] = turns[4] = 5                      turns[4] = temp[1] = 3

        else {
            int playerIndex = 0;
            for (int i = 0; i < turns.length; i++) {
                if (turns[i] == turnIDOfCurrentPlayer) {
                    playerIndex = i;;
                    break;
                }
            }
            int[] temp = new int[turns.length];
            for (int i = 0; i < turns.length; i++) {
                temp[i] = turns[(playerIndex - i + turns.length) % turns.length];
            }
            for (int i = 0; i < turns.length; i++) {
                //turns[i] = temp[(playerIndex + i + turns.length -1) % turns.length];
                turns[(i + playerIndex) % turns.length] = temp[i];
            }
        }
        System.out.println("The board is reversed!");
        wise *= -1;
    }

    /**
     * add some cards to player deck
     * @param turnIDOfCurrentPlayer the turnID of the player
     * @param cardsNumber how many cards should be add
     */
    public static void addCardToDeck(int turnIDOfCurrentPlayer, int cardsNumber) {
        if(cardsNumber > cards.get(0).size()){
            cardsNumber = cards.get(0).size();
            System.out.println("Not enough cards to add");
            System.out.println("The player will take "+cardsNumber+" cards");
        }
        for (int i = 0; i < cardsNumber; i++) {
            int cardToAddIndex = random.nextInt(cards.get(0).size());
            Card cardToAdd = cards.get(0).get(cardToAddIndex);
            cards.get(turnIDOfCurrentPlayer).add(cardToAdd);
            cards.get(0).remove(cardToAdd);
        }
    }

    /**
     * print the las card that played
     */
    public void printLastPlayedCard() {
        String ANSI_COLOR;
        if (currentColor.equals(Constants.colors[0]))
            ANSI_COLOR = Constants.ANSI_COLORS[0];
        else if (currentColor.equals(Constants.colors[1]))
            ANSI_COLOR = Constants.ANSI_COLORS[1];
        else if (currentColor.equals(Constants.colors[2]))
            ANSI_COLOR = Constants.ANSI_COLORS[2];
        else if (currentColor.equals(Constants.colors[3]))
            ANSI_COLOR = Constants.ANSI_COLORS[3];
        else ANSI_COLOR = "0";

        System.out.println(ANSI_COLOR + "------------------  ");
        System.out.println("+                +  ");
        System.out.println("|LAST PLAYED CARD|  ");
        System.out.println("+                +  ");
        System.out.println("------------------  ");
        System.out.println("+                +  ");
        System.out.println("|                |  ");
        System.out.println("+                +  ");
        System.out.println("|    " + lastPlayedCard.getTypeDetail() + "   |  ");
        System.out.println("+                +  ");
        System.out.println("|                |  ");
        System.out.println("+                +  ");
        System.out.println("------------------  " + ANSI_COLOR);
        System.out.println("\n" + "\u001B[0m");
    }

    /**
     * get the wise of the game
     * @return wise of the game
     */
    public static String getWise() {
        if (wise > 0)
            return "ClockWise";
        else return "Anti ClockWise";
    }
}
