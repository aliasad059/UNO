import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Human extends Player {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    public Human(String playerName, int turnNumber) {
        super(playerName, turnNumber);
    }

    public void changeColor(String color) {
        Board.setCurrentColor(color);
    }

    @Override
    public boolean playCard() {
        Card cardToPlay;
        while (true) {
            printPlayerCard();
            System.out.println("Enter a card");
            int cardToPlayNumber = scanner.nextInt();
            if (canUseThisCard(playerCard.get(cardToPlayNumber))) {
                cardToPlay = playerCard.get(cardToPlayNumber);
                break;
            } else System.out.println("Can not play this card now , choose another.");
        }

        if (cardToPlay.getType().equals(Constants.types[2])) {
            if (cardToPlay.getTypeDetail().equals(Constants.typesDetail[3])) {
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
                    else continue;
                    break;
                }
                Board.setLastPlayedCard(cardToPlay);
            } else if (cardToPlay.getTypeDetail().equals(Constants.typesDetail[4])) {
                random.nextInt()
                ArrayList<ArrayList<Card>> cards = Board.getCards();
                for (int i = 0; i < 4; i++) {
                    int cardRandomNumber = random.nextInt(cards.get(0).size());
                    cards.get()
                }

            }
            return false;
        }
    }

    public void printPlayerCard() {

    }
}
