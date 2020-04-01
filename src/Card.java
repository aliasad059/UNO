abstract public class Card {
    private String color;
    private String type;
    private int number;

    public Card(String color, String type, int number) {
        this.color = color;
        this.type = type;
        this.number = number;
    }

    abstract public boolean playCard();

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
