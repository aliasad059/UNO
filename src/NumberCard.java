public class NumberCard extends Card {
    public NumberCard(String color, String type, int number) {
        super(color, type, number);
    }
    @Override
    public boolean playCard() {
        return false;
    }
}
