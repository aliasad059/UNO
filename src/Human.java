import java.util.ArrayList;

public class Human extends Player {

    public Human(String playerName, int turnNumber) {
        super(playerName, turnNumber);
    }

    @Override
    public void chooseCard() {
        if (Board.getLastPlayedCard().getTypeDetail().equals(Constants.typesDetail[0])) {
            System.out.println(this.PlayerName + " is skipped!");
            return;
        } else if (addCardNumber != 0) {
            ArrayList<Card> drawCards = new ArrayList<Card>();
            for (Card card : Board.getCards().get(this.turnID)) {
                if (card.getTypeDetail().equals(Constants.typesDetail[4]) || card.getTypeDetail().equals(Constants.typesDetail[2])) {
                    if (canUseThisCard(card))
                        drawCards.add(card);
                }
            }
            if (drawCards.size() != 0) {
                System.out.println("Will you play your draw cards of take " + addCardNumber + " cards?");
                while (true) {
                    System.out.println("Enter Y (yes) to choose your draw and N (no) to take cards from storage");
                    char choice = scanner.nextLine().toUpperCase().charAt(0);
                    if (choice == 'Y') {
                        while (true) {
                            System.out.println("Choose a card");
                            printDeck(drawCards);
                            int cardToChooseNumber = scanner.nextInt();
                            if (cardToChooseNumber < drawCards.size()) {
                                playCard(drawCards.get(cardToChooseNumber));
                                return;
                            } else System.out.println("Wrong input enter try again");
                        }
                    } else if (choice == 'N') {
                        Board.addCardToDeck(this.turnID, addCardNumber);
                        addCardNumber = 0;
                        return;
                    } else {
                        System.out.println("Wrong input enter try again");
                        continue;
                    }
                }
            } else {
                Board.addCardToDeck(this.turnID, addCardNumber);
                addCardNumber = 0;
                return;
            }

        }
        boolean canPlayAnyCard = false;
        for (Card card : Board.getCards().get(turnID)) {
            if (canUseThisCard(card)) {
                canPlayAnyCard = true;
                break;
            }
        }
        if (canPlayAnyCard) {
            // normal situation
            Card cardToPlay;
            while (true) {

                System.out.println(this.PlayerName + " enter a card");
                int cardToPlayNumber = scanner.nextInt();
                if (canUseThisCard(playerCard.get(cardToPlayNumber))) {
                    cardToPlay = playerCard.get(cardToPlayNumber);
                    Board.setLastPlayedCard(cardToPlay);
                    Board.setCurrentColor(cardToPlay.getColor());
                    playCard(cardToPlay);
                    break;
                } else System.out.println("Can not play this card now , choose another.");
            }
        } else {
            System.out.println(this.PlayerName + " can not play any card, 1 card is added to the deck");
            Board.addCardToDeck(this.turnID, 1);
            //checking maybe can play new added card
            for (Card card : Board.getCards().get(turnID)) {
                if (canUseThisCard(card)) {
                    playCard(card);
                    return;
                }
            }
        }
    }

    @Override
    public void playCard(Card cardToPlay) {
        //if it is a wild card
        if (cardToPlay.getType().equals(Constants.types[2])) {

            //if the card is a wild draw card
            if (cardToPlay.getTypeDetail().equals(Constants.typesDetail[4])) {
                addCardNumber += 4;

            }
            //after using any wild card we must set a new color to the board
            while (true) {
                System.out.println("Enter the board color :(R stands for RED ,Y stands for YELLOW ,G stands for GREEN ,B stands for BLUE )");
                char color = Game.scanner.nextLine().toUpperCase().charAt(0);
                if (color == 'R')
                    Board.setCurrentColor(Constants.colors[0]);
                else if (color == 'Y')
                    Board.setCurrentColor(Constants.colors[1]);
                else if (color == 'G')
                    Board.setCurrentColor(Constants.colors[2]);
                else if (color == 'B')
                    Board.setCurrentColor(Constants.colors[3]);
                else {
                    System.out.println("Wrong input, choose another");
                    continue;
                }
                break;
            }
        }
        // if the card is an action card
        else if (cardToPlay.getType().equals(Constants.types[1])) {
            //playing skip card
            if (cardToPlay.getTypeDetail().equals(Constants.typesDetail[0])) {
                Board.setCurrentColor(cardToPlay.getColor());
            }
            //playing reverse card
            else if (cardToPlay.getTypeDetail().equals(Constants.typesDetail[1])) {
                Board.setCurrentColor(cardToPlay.getColor());
                Board.reverse(this.turnID);
            }
            //playing draw 2 card
            else if (cardToPlay.getTypeDetail().equals(Constants.typesDetail[2])) {

                Board.setCurrentColor(cardToPlay.getColor());
                addCardNumber += 2;
            }
        }
        //if the card is an number card
        else if (cardToPlay.getType().equals(Constants.types[0])) {
            Board.setCurrentColor(cardToPlay.getColor());
            // no need to do anything
        }

        Board.setLastPlayedCard(cardToPlay);
        dropCard(cardToPlay);

    }

    public void printDeck(ArrayList<Card> deckToPrint) {
        System.out.println("");
        for (int j = 0; j < deckToPrint.size(); j++) {

            System.out.print(getANSICOLOR(deckToPrint.get(j)) + "------------------  " + getANSICOLOR(deckToPrint.get(j)));
        }
        System.out.print("\u001B[30m" + "+                +  " + "\u001B[30m");
        for (int j = 0; j < deckToPrint.size(); j++) {
            System.out.print(getANSICOLOR(deckToPrint.get(j)) + "|     Card  " + (j) + "    |  " + getANSICOLOR(deckToPrint.get(j)));
        }
        System.out.print("\u001B[30m" + "+                +  " + "\u001B[30m");
        for (int j = 0; j < deckToPrint.size(); j++) {
            System.out.print(getANSICOLOR(deckToPrint.get(j)) + "------------------  " + getANSICOLOR(deckToPrint.get(j)));
        }
        System.out.print("\u001B[30m" + "+                +  " + "\u001B[30m");
        for (int j = 0; j < deckToPrint.size(); j++) {
            System.out.print(getANSICOLOR(deckToPrint.get(j)) + "|                |  " + getANSICOLOR(deckToPrint.get(j)));
        }
        System.out.print("\u001B[30m" + "+                +  " + "\u001B[30m");
        for (int j = 0; j < deckToPrint.size(); j++) {
            System.out.print(getANSICOLOR(deckToPrint.get(j)) + "|   " + deckToPrint.get(j).getTypeDetail() + "    |  ");
        }
        System.out.print("\u001B[30m" + "+                +  " + "\u001B[30m");
        for (int j = 0; j < deckToPrint.size(); j++) {
            System.out.print(getANSICOLOR(deckToPrint.get(j)) + "|                |  " + getANSICOLOR(deckToPrint.get(j)));
        }
        System.out.print("\u001B[30m" + "+                +  " + "\u001B[30m");
        for (int j = 0; j < deckToPrint.size(); j++) {
            System.out.print(getANSICOLOR(deckToPrint.get(j)) + "------------------  " + getANSICOLOR(deckToPrint.get(j)));
        }
        System.out.println("\n");
    }

    public static String getANSICOLOR(Card card) {
        if (card.getColor().equals(Constants.colors[0]))
            return Constants.ANSI_COLORS[0];
        else if (card.getColor().equals(Constants.colors[1]))
            return Constants.ANSI_COLORS[1];
        else if (card.getColor().equals(Constants.colors[2]))
            return Constants.ANSI_COLORS[2];
        else if (card.getColor().equals(Constants.colors[3]))
            return Constants.ANSI_COLORS[3];
        else
            return Constants.ANSI_COLORS[4];
    }
}
