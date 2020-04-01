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

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDetail() {
        return typeDetail;
    }

    public void setTypeDetail(String typeDetail) {
        this.typeDetail = typeDetail;
    }
}
