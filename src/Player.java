import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

abstract public class Player {
    String PlayerName;
    int turnID;
    static int addCardNumber = 0;
    ArrayList<Card> playerCard;
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    abstract public void chooseCard();

    abstract public void playCard(Card cardToPlay);

    public Player(String playerName, int turnID) {
        PlayerName = playerName;
        this.turnID = turnID;
        playerCard = Board.getCards().get(turnID);
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public boolean canUseThisCard(Card cardToUse) {
        //if it is a number card
        if (cardToUse.getType().equals(Constants.types[0])) {
            if (cardToUse.getTypeDetail().equals(Board.getLastPlayedCard().getTypeDetail())) {
                return true;
            } else if (cardToUse.getColor().equals(Board.getCurrentColor())) {
                return true;
            } else return false;
        }
        //if it is an action card
        else if (cardToUse.getType().equals(Constants.types[1])) {
            if (cardToUse.getColor().equals(Board.getCurrentColor()))
                return true;
            else if (cardToUse.getTypeDetail().equals(Board.getLastPlayedCard().getTypeDetail()))
                return true;
            else return false;
        }
        //if it is a wild card
        else if (cardToUse.getType().equals(Constants.types[2])) {
            // if it is a choose color card
            if (cardToUse.getTypeDetail().equals(Constants.typesDetail[3])) {
                return true;
            }
            //if it is a wild draw card
            else if ((cardToUse.getTypeDetail().equals(Constants.typesDetail[4]))) {
                boolean canUseAnyOtherCard = false;
                for (Card otherCard : Board.getCards().get(this.turnID)) {
                    if (otherCard.getType().equals(Constants.types[0]) || otherCard.getType().equals(Constants.types[0])) {
                        if (canUseThisCard(otherCard))
                            return false;
                    }
                }
                return true;
            } else return false;
        } else return false;

    }

    public void dropCard(Card cardToDrop) {
        Board.getCards().get(0).add(cardToDrop);
        Board.getCards().get(this.turnID).remove(cardToDrop);

    }

    public static void setAddCardNumber(int addCardNumber) {
        Player.addCardNumber = addCardNumber;
    }
}
