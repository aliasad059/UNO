public class Computer extends Player{

    public Computer(String playerName, int turnNumber) {
        super(playerName, turnNumber);
    }

    @Override
    boolean playCard(int cardToPlay) {
        return false;
    }
}
