package com.apsu.joshua.blackjack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    Card[] deck = new Card[] { new Card(1, 2, "Two", "Hearts"),
            new Card(2, 3, "Three", "Hearts"), new Card(3, 4, "Four", "Hearts"),
            new Card(4, 5, "Five", "Hearts"), new Card(5, 6, "Six", "Hearts"),
            new Card(6, 7, "Seven", "Hearts"), new Card(7, 8, "Eight", "Hearts"),
            new Card(8, 9, "Nine", "Hearts"), new Card(9, 10, "Ten", "Hearts"),
            new Card(10, 10, "Jack", "Hearts"), new Card(11, 10, "Queen", "Hearts"),
            new Card(12, 10, "King", "Hearts"), new Card(13, 11, "Ace", "Hearts"),
            new Card(14, 2, "Two", "Diamonds"),
            new Card(15, 3, "Three", "Diamonds"), new Card(16, 4, "Four", "Diamonds"),
            new Card(17, 5, "Five", "Diamonds"), new Card(18, 6, "Six", "Diamonds"),
            new Card(19, 7, "Seven", "Diamonds"), new Card(20, 8, "Eight", "Diamonds"),
            new Card(21, 9, "Nine", "Diamonds"), new Card(22, 10, "Ten", "Diamonds"),
            new Card(23, 10, "Jack", "Diamonds"), new Card(24, 10, "Queen", "Diamonds"),
            new Card(25, 10, "King", "Diamonds"), new Card(26, 11, "Ace", "Diamonds"),
            new Card(27, 2, "Two", "Spades"),
            new Card(28, 3, "Three", "Spades"), new Card(29, 4, "Four", "Spades"),
            new Card(30, 5, "Five", "Spades"), new Card(31, 6, "Six", "Spades"),
            new Card(32, 7, "Seven", "Spades"), new Card(33, 8, "Eight", "Spades"),
            new Card(34, 9, "Nine", "Spades"), new Card(35, 10, "Ten", "Spades"),
            new Card(36, 10, "Jack", "Spades"), new Card(37, 10, "Queen", "Spades"),
            new Card(38, 10, "King", "Spades"), new Card(39, 11, "Ace", "Spades"),
            new Card(40, 2, "Two", "Clubs"),
            new Card(41, 3, "Three", "Clubs"), new Card(42, 4, "Four", "Clubs"),
            new Card(43, 5, "Five", "Clubs"), new Card(44, 6, "Six", "Clubs"),
            new Card(45, 7, "Seven", "Clubs"), new Card(46, 8, "Eight", "Clubs"),
            new Card(47, 9, "Nine", "Clubs"), new Card(48, 10, "Ten", "Clubs"),
            new Card(49, 10, "Jack", "Clubs"), new Card(50, 10, "Queen", "Clubs"),
            new Card(51, 10, "King", "Clubs"), new Card(52, 11, "Ace", "Clubs")
            };
    Player user = new Player(), dealer = new Player();
    int money = 200;
    int bet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText betAmount = (EditText) findViewById(R.id.etBet);
        final TextView total = (TextView) findViewById(R.id.tvPlayer);
        final TextView finish = (TextView) findViewById(R.id.tvDealer);
        final TextView moneyTv = (TextView) findViewById(R.id.tvTotal);
        Button hit = (Button) findViewById(R.id.btnHit);
        Button stay = (Button) findViewById(R.id.btnStay);
        final Button placeBet = (Button) findViewById(R.id.btnPlaceBet);
        final Button resetBtn = (Button) findViewById(R.id.btnReset);
        resetBtn.setEnabled(false);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
        placeBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Integer.parseInt(betAmount.getText().toString()) > money) {
                    finish.setText("You don't have that much money!");
                }
                else {
                    bet = Integer.parseInt(betAmount.getText().toString());
                    money = money - bet;
                    moneyTv.setText("Total: " + money);
                    betAmount.setEnabled(false);
                    placeBet.setEnabled(false);
                }
            }
        });
        stay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setHold(true);
                while (user.getHandValue() > dealer.getHandValue()) {
                    dealer.newCard();
                    total.setText(Integer.toString(user.getHandValue()) + " " + Integer.toString(dealer.getHandValue()));
                }
                endGame();
            }
        });
        hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.newCard();
                total.setText(Integer.toString(user.getHandValue()) + " " + Integer.toString(dealer.getHandValue()));
                if (user.getHandValue() >= 21) {
                    endGame();
                } else {
                    dealer.newCard();
                    total.setText(Integer.toString(user.getHandValue()) + " " + Integer.toString(dealer.getHandValue()));
                    if (dealer.getHandValue() >= 21) {
                        endGame();
                    }
                }
            }
        });
    }


    public Card drawCard () {
        Card drawn = deck[0 + (int)(Math.random() * ((51) + 1))];
        boolean usedCard;
        do {
            usedCard = false;
            for (int i = 0; i < 5; i++)
            {
                if (user.getCard(i).getId() == drawn.getId()) {
                    usedCard = true;
                    drawn = deck[0 + (int)(Math.random() * ((51) + 1))];
                }
                else if (dealer.getCard(i).getId() == drawn.getId()) {
                    usedCard = true;
                    drawn = deck[0 + (int) (Math.random() * ((51) + 1))];
                }
            }
        } while (usedCard);
        return drawn;
    }

    public void reset() {
        TextView total = (TextView) findViewById(R.id.tvPlayer);
        TextView finish = (TextView) findViewById(R.id.tvDealer);
        Button placeBet = (Button) findViewById(R.id.btnPlaceBet);
        EditText betAmount = (EditText) findViewById(R.id.etBet);
        finish.setText("Dealer");
        placeBet.setEnabled(true);
        betAmount.setEnabled(true);
        user = new Player();
        dealer = new Player();
        total.setText(Integer.toString(user.getHandValue()) + " " + Integer.toString(dealer.getHandValue()));
        Button resetBtn = (Button) findViewById(R.id.btnReset);
        Button hit = (Button) findViewById(R.id.btnHit);
        Button stay = (Button) findViewById(R.id.btnStay);
        placeBet.setEnabled(true);
        resetBtn.setEnabled(false);
        hit.setEnabled(true);
        stay.setEnabled(true);
    }

    public void endGame () {
        boolean won;
        boolean tie = false;
        if (user.getHandValue() == dealer.getHandValue()) {
            won = false;
            tie = true;
        }else if (user.getHandValue() > 21) {
            won = false;
        } else if (dealer.getHandValue() > 21) {
            won = true;
        } else if (user.getHandValue() == 21) {
            won = true;
        } else if (dealer.getHandValue() == 21) {
            won = false;
        } else if (user.getHandValue() > dealer.getHandValue()) {
            won = true;
        } else {
            won = false;
        }
        TextView finish = (TextView) findViewById(R.id.tvDealer);
        TextView moneyTv = (TextView) findViewById(R.id.tvTotal);
        if (won) {
            finish.setText("Congratulations you won!");
            money += bet*2;
            moneyTv.setText("Total: " + money);
        } else if (tie) {
            finish.setText("It's a tie!");
            money += bet;
            moneyTv.setText("Total: " + money);
        } else {
            finish.setText("Dealer won this round...");
        }
        Button placeBet = (Button) findViewById(R.id.btnPlaceBet);
        Button resetBtn = (Button) findViewById(R.id.btnReset);
        Button hit = (Button) findViewById(R.id.btnHit);
        Button stay = (Button) findViewById(R.id.btnStay);
        placeBet.setEnabled(false);
        resetBtn.setEnabled(true);
        hit.setEnabled(false);
        stay.setEnabled(false);

    }

    class Player {
        private Card[] hand = new Card[5];
        private int cardCount;
        private int handValue;
        private boolean hold;

        public int getCardCount() {
            return cardCount;
        }

        public void setCardCount(int cardCount) {
            this.cardCount = cardCount;
        }

        public boolean isHold() {
            return hold;
        }

        public void setHold(boolean hold) {
            this.hold = hold;
        }

        public Card getCard(int i) {
            return hand[i];
        }

        public void newCard() {
            hand[cardCount] = drawCard();
            handValue += hand[cardCount].getValue();
            cardCount++;
            if (handValue > 21) {
                for (int i = 0; i < cardCount; i++) {
                    if (hand[i].getType().equals("Ace")) {
                        hand[i].setValue(1);
                    }
                }
            }
        }

        public int getHandValue() {
            return handValue;
        }

        public void setHandValue(int handValue) {
            this.handValue = handValue;
        }

        public Player() {
            for (int i = 0; i < 5; i++) {
                hand[i] = new Card(0, 0, "Blank", "Blank");
            }
            cardCount = 0;
            handValue = 0;
            hold = false;
        }

    }
}

class Card {

    private int value;
    private int id;
    private String type;
    private String suit;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getSuit() {
        return suit;
    }

    public String getType() {
        return type;
    }

    public Card(int id, int value, String type, String suit){
        this.id = id;
        this.value = value;
        this.type = type;
        this.suit = suit;
    }


}
