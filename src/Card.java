abstract public class Card {
    private String color;
    private String type;
    private String typeDetail;

    public Card(String color, String type, String typeDetail) {
        this.color = color;
        this.type = type;
        this.typeDetail = typeDetail;
    }

    public String getColor() {
        return color;
    }


    public String getType() {
        return type;
    }


    public String getTypeDetail() {
        return typeDetail;
    }

}
