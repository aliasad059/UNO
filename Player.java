import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * this class simulate a player of the game
 * there are two kind of player human and computer
 * class player is the parent of HUMAN and COMPUTER classes
 */
abstract public class Player {
    protected String PlayerName;
    //this is a unique integer that will tell us witch object of subclassed are we using
    // in other word each object of such a this type has a unique turnID that specify the object to us
    protected int turnID;
    //how many card will be added if the player don't play a draw card
    //it will increase more than one time when players play draw card more than one time continuously
    protected static int addCardNumber = 0;
    protected ArrayList<Card> playerCard;
    protected int point;
    static boolean isSkipped = false;
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    /**
     * choose a card to play
     */
    abstract public void chooseCard();

    /**
     * play a card
     * @param cardToPlay card to play
     */
    abstract public void playCard(Card cardToPlay);

    public Player(String playerName, int turnID) {
        PlayerName = playerName;
        this.turnID = turnID;
        playerCard = Board.getCards().get(turnID);
    }

    /**
     * get player name
     * @return name of player
     */
    public String getPlayerName() {
        return PlayerName;
    }

    /**
     * check if can use this card or not
     * @param cardToUse card to check
     * @return true if the player is able to play it
     */
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
                    if (!otherCard.getTypeDetail().equals(Constants.typesDetail[4])) {
                        if (canUseThisCard(otherCard))
                            return false;
                    }
                }
                return true;
            } else return false;
        } else return false;

    }

    /**
     * after playing the card the player should drop the card on the ground
     * @param cardToDrop card to drop
     */
    public void dropCard(Card cardToDrop) {
        Board.getCards().get(0).add(cardToDrop);
        Board.getCards().get(this.turnID).remove(cardToDrop);
        System.out.println(this.PlayerName + " drop the " + cardToDrop.getType() + " card with kind of " + cardToDrop.getTypeDetail());

    }

    /**
     * set how many cards should be added
     * @param addCardNumber
     */
    public static void setAddCardNumber(int addCardNumber) {
        Player.addCardNumber = addCardNumber;
    }

}
