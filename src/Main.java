import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;

class Card {
    private String title;
    private Integer value;

    public Card(String title, Integer value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Card{" +
                "title='" + title + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

public class Main {
    private static ArrayList<Card> hearts = new ArrayList<>();
    private static ArrayList<Card> diamonds = new ArrayList<>();
    private static ArrayList<Card> clovers = new ArrayList<>();
    private static ArrayList<Card> deck = new ArrayList<>();
    private static ArrayList<Card> spades = new ArrayList<>();
    private static ArrayList<Card> playerHand = new ArrayList<>();
    private static ArrayList<Card> dealerHand = new ArrayList<>();
    private static boolean dealing = true;
    private static int turnOver = 4;

    static {
        for (Integer value : new Integer[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1}) {
            hearts.add(new Card("Heart", value));
            spades.add(new Card("Spade", value));
            clovers.add(new Card("Clovers", value));
            diamonds.add(new Card("Diamonds", value));
        }
    }

    public static void main(String[] args) {
        deck.addAll(spades);
        deck.addAll(hearts);
        deck.addAll(clovers);
        deck.addAll(diamonds);
        Collections.shuffle(deck);


        while (dealing) {
            System.out.println("Deck: " + deck);
            System.out.println("Deck Size: " + deck.size());
            for (int i = 0; i < turnOver; i++) {
                if (i == 0 || i == 2) {
                    Card playerCard = deck.remove(i);
                    playerHand.add(playerCard);
                }
                if (i == 1 || i == 3) {
                    Card dealerCard = deck.remove(i);
                    dealerHand.add(dealerCard);
                }
            }

            for(int hand  = 0; hand < playerHand.size(); hand++) {
                getAceValue(hand);
            }

            System.out.println("Your hand is: " + playerHand);
            System.out.println("Dealer hand is: " + dealerHand.getFirst());
            System.out.println("Press 1 to Stay: 2 to hit me");
            Console input = System.console();
            String answer = input.readLine();
            try {
                if(!answer.equals("1") || !answer.equals("2")) {
                    throw new Exception("bad bad");
                }
            } catch(Exception e) {
                System.out.println("You didn't enter 1 or 2. Please try again");
                System.out.println("Press 1 to Stay: 2 to hit me");
                input = System.console();
                answer = input.readLine();
            }

            if (answer.equals("1")) {
                dealing = false;
                int playerTotal = playerHand.getFirst().getValue() + playerHand.getLast().getValue();
                int dealerTotal = dealerHand.getFirst().getValue() + dealerHand.getLast().getValue();
                if(playerTotal > 21) {
                    restartGame();
                    System.out.println("You lose!!!");
                }
                if (playerTotal == dealerTotal) {
                    System.out.println("Its a draw!!!");
                } else if(playerTotal > dealerTotal) {
                    System.out.println("You win!!!");
                } else {
                    System.out.println("You lose!!!");
                }
                restartGame();
                // You may need additional logic here to calculate the total
                // value of the player's and dealer's hands.
            } else if (answer.equals("2")) {
                turnOver++;
                Card playerCard = deck.remove(4);
                playerHand.add(playerCard);
                System.out.println(playerHand);
                System.out.println("Press 1 to Stay: 2 to hit me");
                String answer2 = input.readLine();
                System.out.println("answer 2: " + answer2 + "answer: " +  answer);

                if(answer2.equals("1")) {
                    dealing = false;
                    int playerTotal = 0;
                    int dealerTotal = 0;
                    for(Card card: playerHand) {
                        playerTotal += card.getValue();
                    }
                    if(playerTotal > 21) {
                        System.out.println("You Lose!!");
                        restartGame();
                        return;
                    }
                    for(Card card: dealerHand) {
                        dealerTotal += card.getValue();
                    }
                    if (playerTotal == dealerTotal) {
                        System.out.println("Its a draw!!!");
                    } else if(playerTotal > dealerTotal) {
                        System.out.println("You win!!!");
                    } else {
                        System.out.println("You lose!!!");
                    }
                    restartGame();

                } else if (answer2.equals("2")) {
                    playerHand.add(playerCard);
                    System.out.println(playerHand);
                    System.out.println("Press 1 to Stay: 2 to hit me");
                }
            }
            System.out.println(dealerHand);
        }
    }

    private static void getAceValue(int hand) {
        System.out.println(playerHand.get(hand));
        if(playerHand.get(hand).getValue() == 1) {
            Console aceInput = System.console();
            System.out.println("You have an ace. press 1 for value 1 or 2 for value 11");
            String aceAnswer = aceInput.readLine();
            try {
                if(aceAnswer.equals("1")) {
                    System.out.println("inside getacevalue first");
                        playerHand.set(hand, new Card("Heart", 1));
                        return;
                }
                if(aceAnswer.equals("2")) {
                    System.out.println("inside getacevalueu second");
                    playerHand.set(hand, new Card("Heart", 11));
                    return;
                }
            } catch(Exception e) {
                System.out.println("Please enter 1 or 2");
            }
        }
    }

    private static void restartGame() {
        playerHand.removeAll(playerHand);
        dealerHand.removeAll(dealerHand);
        turnOver = 4;
        dealing = true;
    }

}
