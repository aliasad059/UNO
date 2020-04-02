import java.util.ArrayList;
import java.util.Random;

public class Computer extends Player {
    Random random = new Random();

    public Computer(String playerName, int turnNumber) {
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
                int cardToChooseNumber = random.nextInt(drawCards.size());
                playCard(drawCards.get(cardToChooseNumber));
                return;
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
                //if it is a choose color wild card
            }
            //after using any wild card we must set a new color to the board
            Board.setCurrentColor(bestColorToChoose());
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
        }

        Board.setLastPlayedCard(cardToPlay);
        dropCard(cardToPlay);

    }

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
