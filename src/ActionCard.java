public class ActionCard extends Card {
    public ActionCard(String color, String type, int number) {
        super(color, type, number);
    }
    @Override
    public boolean playCard() {
        return false;
    }
    // IF USING DRAW 2 THE NEXT PLAYER TURN WILL BE FORFIET
}
