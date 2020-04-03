abstract public class Card {
    private String color;
    private String type;
    private String typeDetail;
    final int point;
    public Card(String color, String type, String typeDetail) {
        this.color = color;
        this.type = type;
        this.typeDetail = typeDetail;
        if (type.equals(Constants.types[0]))
            point = (int)typeDetail.charAt(0)-48;
        else if(type.equals(Constants.types[1]))
            point = 20;
        else point = 50;
    }

    public String getColor() {
        return color;
    }


    public String getType() {
        return type;
    }

    public int getPoint() {
        return point;
    }

    public String getTypeDetail() {
        return typeDetail;
    }

}
