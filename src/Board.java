import java.util.ArrayList;
import java.util.Random;

public class Board {
    static private ArrayList<ArrayList<Card>> cards = new ArrayList<ArrayList<Card>>();
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

        //all players deck + the remaining cards on the ground
        for (int i = 0; i < playersNumber + 1; i++)
            cards.add(new ArrayList<Card>());
        makeCards();
        distributeCards();
    }

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
            if (cards.get(0).get(randomCardNumber).getType().equals(Constants.types[0])) {
                setLastPlayedCard(cards.get(0).get(randomCardNumber));
                setCurrentColor(lastPlayedCard.getColor());
                break;
            } else if (cards.get(0).get(randomCardNumber).getType().equals(Constants.types[1])) {
                if (cards.get(0).get(randomCardNumber).getTypeDetail().equals(Constants.typesDetail[0])) {
                    Player.isSkipped = true;
                } else if (cards.get(0).get(randomCardNumber).getTypeDetail().equals(Constants.typesDetail[1])) {
                    reverse(turns[0]);
                } else if (cards.get(0).get(randomCardNumber).getTypeDetail().equals(Constants.typesDetail[2])) {
                    Player.setAddCardNumber(2);
                    Player.isSkipped = true;
                }
                setLastPlayedCard(cards.get(0).get(randomCardNumber));
                setCurrentColor(lastPlayedCard.getColor());
                break;
            } else continue;
        }
    }

    public static void setLastPlayedCard(Card lastPlayedCard) {
        Board.lastPlayedCard = lastPlayedCard;
    }

    public static Card getLastPlayedCard() {
        return lastPlayedCard;
    }

    public static void setCurrentColor(String currentColor) {
        Board.currentColor = currentColor;
    }

    public static String getCurrentColor() {
        return currentColor;
    }

    public static ArrayList<ArrayList<Card>> getCards() {
        return cards;
    }

    public static int getPlayersNumber() {
        return playersNumber;
    }

    public static int[] getTurns() {
        return turns;
    }

    //give an example in the comment
    public static void reverse(int turnIDOfCurrentPlayer) {
        if (turns.length == 2) {
            int temp = turns[0];
            turns[0] = turns[1];
            turns[1] = temp;
        } else {
            int playerIndex = 0;
            for (int i = 0; i < turns.length; i++) {
                if (turns[i] == turnIDOfCurrentPlayer) {
                    playerIndex = i;
                    System.out.println("playerIndex = " + i);
                    System.out.println("turnIDOfCurrentPlayer = " + turnIDOfCurrentPlayer);
                    break;
                }
            }
            int[] temp = new int[turns.length];
            for (int i = 0; i < turns.length; i++) {
                temp[i] = turns[(playerIndex - i + turns.length) % turns.length];
                System.out.println(turns[i]);
            }
            for (int i = 0; i < turns.length; i++) {
                //turns[i] = temp[(playerIndex + i + turns.length -1) % turns.length];
                turns[(i + playerIndex) % turns.length] = temp[i];
                System.out.println(turns[i]);
            }
        }
        System.out.println("The board is reversed!");
        wise *= -1;
    }

    public static void addCardToDeck(int turnIDOfCurrentPlayer, int cardsNumber) {
        for (int i = 0; i < cardsNumber; i++) {
            int cardToAddIndex = random.nextInt(cards.get(0).size());
            Card cardToAdd = cards.get(0).get(cardToAddIndex);
            cards.get(turnIDOfCurrentPlayer).add(cardToAdd);
            cards.get(0).remove(cardToAdd);
        }
    }

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

    public static String getWise() {
        if (wise > 0)
            return "ClockWise";
        else return "Anti ClockWise";
    }
}
