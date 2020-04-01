public class WildCard extends Card {

    public WildCard(String color, String type, int number) {
        super(color, type, number);
    }
    public void changeColor(String color){
        Board.setCurrentColor(color);
    }

    @Override
    public boolean playCard() {
        return false;
    }
}
