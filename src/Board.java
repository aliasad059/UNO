import java.util.ArrayList;
import java.util.Random;

public class Board {
    private ArrayList<ArrayList<Card>> cards = new ArrayList<>();

    static private Card lastPlayedCard;
    static private String currentColor;
    static private String wise = "clockwise";

    public Board(int playersNumber) {
        //all players deck + the remaining cards
        //remaining cards
        for (int i = 0; i < playersNumber + 1; i++)
            cards.add(new ArrayList<>());
        makeCards();
        distributeCards();
    }

    private void makeCards() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                cards.get(0).add(new NumberCard(Constants.colors[i], "NUMBER", j));
                if (j + 1 < 10) {
                    cards.get(0).add(new NumberCard(Constants.colors[i], "NUMBER", j + 1));
                }
                cards.get(0).add(new ActionCard(Constants.colors[i], "SKIP", -1));
                cards.get(0).add(new ActionCard(Constants.colors[i], "REVERSE", -2));
                cards.get(0).add(new ActionCard(Constants.colors[i], "DRAW", -3));
                cards.get(0).add(new ActionCard(Constants.colors[i], "SKIP", -1));
                cards.get(0).add(new ActionCard(Constants.colors[i], "REVERSE", -2));
                cards.get(0).add(new ActionCard(Constants.colors[i], "DRAW", -3));

                cards.get(0).add(new WildCard("BLACK", "CHOOSECOLOR", -1));
                cards.get(0).add(new WildCard("BLACK", "WILDDRAW", -2));
            }
        }
    }

    private void distributeCards() {
        Random randomNumber = new  Random();
        for (int i = 1 ; i < cards.size() ; i ++) {
            for (int j = 0; j < 7; j++) {
                int randomCardNumber =randomNumber.nextInt(cards.get(0).size());
                cards.get(i).add(cards.get(0).get(randomCardNumber));
                cards.get(0).remove(randomCardNumber);
            }
        }
        while (true) {
            int randomCardNumber = randomNumber.nextInt(cards.get(0).size());
            if (cards.get(0).get(randomCardNumber).getType().equals("NUMBER")){
                setLastPlayedCard(cards.get(0).get(randomCardNumber));
                setCurrentColor(lastPlayedCard.getColor());
            }
        }
    }

    public static Card getLastPlayedCard() {
        return lastPlayedCard;
    }

    public static void setLastPlayedCard(Card lastPlayedCard) {
        Board.lastPlayedCard = lastPlayedCard;
    }

    public static String getCurrentColor() {
        return currentColor;
    }

    public static void setCurrentColor(String currentColor) {
        Board.currentColor = currentColor;
    }
}
