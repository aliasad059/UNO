/**
 * simulate a card of the UNO game
 */
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
            point = (int)typeDetail.charAt(4)-48;
        else if(type.equals(Constants.types[1]))
            point = 20;
        else point = 50;
    }

    /**
     * get the color of the card
     * @return
     */
    public String getColor() {
        return color;
    }

    /**
     * get the type of the card
     * @return type of the card
     */

    public String getType() {
        return type;
    }

    /**
     * get the point of the card
     * @return point of the card
     */

    public int getPoint() {
        return point;
    }

    /**
     * get type detail of the card
     * @return type detail of the card
     */

    public String getTypeDetail() {
        return typeDetail;
    }

}
