import java.util.ArrayList;

abstract public class Player {
    String PlayerName;
    int turnID;
    ArrayList<Card> playerCard;
    abstract public boolean playCard();
    public Player(String playerName, int turnID) {
        PlayerName = playerName;
        this.turnID = turnID;
        playerCard = Board.getCards().get(turnID);
    }

    public String getPlayerName() {
        return PlayerName;
    }
    public boolean canUseThisCard(Card cardToUse){
        // wild draw and other situations
        return false;
    }
}
