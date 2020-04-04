import java.util.ArrayList;
import java.util.Random;

/**
 * a computer can choose a card and play it manually
 */
public class Computer extends Player {
    Random random = new Random();

    public Computer(String playerName, int turnNumber) {
        super(playerName, turnNumber);
    }

    @Override
    public void chooseCard() {

        // if the recent player played a skip card
        if (isSkipped && Board.getLastPlayedCard().getTypeDetail().equals(Constants.typesDetail[0])) {
            isSkipped = false;
            System.out.println(this.PlayerName + " is skipped!");
            return;
        }
        // if the add card number is not equal to zero it means that a player plays a draw card
        else if (addCardNumber != 0) {
            ArrayList<Card> drawCards = new ArrayList<Card>();
            for (Card card : Board.getCards().get(this.turnID)) {
                if (card.getTypeDetail().equals(Constants.typesDetail[4]) || card.getTypeDetail().equals(Constants.typesDetail[2])) {
                    if (canUseThisCard(card))
                        drawCards.add(card);
                }
            }
            // if the player has at least one draw card and can play it , it will decide to drop its draw card
            if (drawCards.size() != 0) {
                int cardToChooseNumber = random.nextInt(drawCards.size());
                playCard(drawCards.get(cardToChooseNumber));
            } else {
                Board.addCardToDeck(this.turnID, addCardNumber);
                System.out.println(addCardNumber + " cards are added to " + this.PlayerName + "'s deck");
                addCardNumber = 0;
            }
            return;

        }
        // normal situation

        //check if the player can play any card or not if no one card will be added to he/she 's deck
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
                int cardToPlayNumber = random.nextInt(playerCard.size());
                if (canUseThisCard(playerCard.get(cardToPlayNumber))) {
                    cardToPlay = playerCard.get(cardToPlayNumber);
                    Board.setLastPlayedCard(cardToPlay);
                    Board.setCurrentColor(cardToPlay.getColor());
                    playCard(cardToPlay);
                    break;
                }
            }
        } else {
            System.out.println(this.PlayerName + " can not play any card, 1 card is added to " + this.getPlayerName() + "'s deck");
            Board.addCardToDeck(this.turnID, 1);
            //checking maybe can play new added card
            for (Card card : Board.getCards().get(turnID)) {
                if (canUseThisCard(card)) {
                    System.out.println(this.PlayerName + " found a card to play from the ground!");
                    this.chooseCard();
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
                isSkipped = true;
                //if it is a choose color wild card
            }
            //after using any wild card we must set a new color to the board
            String bestColor = bestColorToChoose();
            System.out.println(this.PlayerName + " changed board color to " + bestColor);
            Board.setCurrentColor(bestColor);
            isSkipped = true;
        }
        // if the card is an action card
        else if (cardToPlay.getType().equals(Constants.types[1])) {
            //playing skip card
            if (cardToPlay.getTypeDetail().equals(Constants.typesDetail[0])) {
                Board.setCurrentColor(cardToPlay.getColor());
                isSkipped = true;
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
                isSkipped = true;
            }
        }
        //if the card is an number card
        else if (cardToPlay.getType().equals(Constants.types[0])) {
            Board.setCurrentColor(cardToPlay.getColor());
        }

        Board.setLastPlayedCard(cardToPlay);
        dropCard(cardToPlay);

    }

    // when using wild card the board color must be changed , the computer choose the best color according to its deck
    private String bestColorToChoose() {
        int[] colorsNumber = {0, 0, 0, 0};
        for (int i = 0; i < playerCard.size(); i++) {
            if (playerCard.get(i).getColor().equals(Constants.colors[0])) {
                colorsNumber[0]++;
            } else if (playerCard.get(i).getColor().equals(Constants.colors[1])) {
                colorsNumber[1]++;
            } else if (playerCard.get(i).getColor().equals(Constants.colors[2])) {
                colorsNumber[2]++;
            } else if (playerCard.get(i).getColor().equals(Constants.colors[3])) {
                colorsNumber[3]++;
            }
        }
        int maxNumber = 0;
        int maxColorIndex = 0;
        for (int i = 0; i < 4; i++) {
            if (maxNumber < colorsNumber[i]) {
                maxNumber = colorsNumber[i];
                maxColorIndex = i;
            }
        }
        return Constants.colors[maxColorIndex];
    }
}
