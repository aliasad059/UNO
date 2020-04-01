abstract public class Player {
    String PlayerName;
    abstract boolean playCard(int cardToPlay);

    public String getPlayerName() {
        return PlayerName;
    }
}
