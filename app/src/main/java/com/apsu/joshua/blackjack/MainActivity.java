package com.apsu.joshua.blackjack;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    Card[] deck = new Card[] { new Card(1, 2, "Two", "Hearts", R.drawable.hearts2),
            new Card(2, 3, "Three", "Hearts", R.drawable.hearts3), new Card(3, 4, "Four", "Hearts", R.drawable.hearts4),
            new Card(4, 5, "Five", "Hearts", R.drawable.hearts5), new Card(5, 6, "Six", "Hearts", R.drawable.hearts6),
            new Card(6, 7, "Seven", "Hearts", R.drawable.hearts7), new Card(7, 8, "Eight", "Hearts", R.drawable.hearts8),
            new Card(8, 9, "Nine", "Hearts", R.drawable.hearts9), new Card(9, 10, "Ten", "Hearts", R.drawable.hearts10),
            new Card(10, 10, "Jack", "Hearts", R.drawable.hearts11), new Card(11, 10, "Queen", "Hearts", R.drawable.hearts12),
            new Card(12, 10, "King", "Hearts", R.drawable.hearts13), new Card(13, 11, "Ace", "Hearts", R.drawable.hearts14),
            new Card(14, 2, "Two", "Diamonds", R.drawable.diamonds2),
            new Card(15, 3, "Three", "Diamonds", R.drawable.diamonds3), new Card(16, 4, "Four", "Diamonds", R.drawable.diamonds4),
            new Card(17, 5, "Five", "Diamonds", R.drawable.diamonds5), new Card(18, 6, "Six", "Diamonds", R.drawable.diamonds6),
            new Card(19, 7, "Seven", "Diamonds", R.drawable.diamonds7), new Card(20, 8, "Eight", "Diamonds", R.drawable.diamonds8),
            new Card(21, 9, "Nine", "Diamonds", R.drawable.diamonds9), new Card(22, 10, "Ten", "Diamonds", R.drawable.diamonds10),
            new Card(23, 10, "Jack", "Diamonds", R.drawable.diamonds11), new Card(24, 10, "Queen", "Diamonds", R.drawable.diamonds12),
            new Card(25, 10, "King", "Diamonds", R.drawable.diamonds13), new Card(26, 11, "Ace", "Diamonds", R.drawable.diamonds14),
            new Card(27, 2, "Two", "Spades", R.drawable.spades2),
            new Card(28, 3, "Three", "Spades", R.drawable.spades3), new Card(29, 4, "Four", "Spades", R.drawable.spades4),
            new Card(30, 5, "Five", "Spades", R.drawable.spades5), new Card(31, 6, "Six", "Spades", R.drawable.spades6),
            new Card(32, 7, "Seven", "Spades", R.drawable.spades7), new Card(33, 8, "Eight", "Spades", R.drawable.spades8),
            new Card(34, 9, "Nine", "Spades", R.drawable.spades9), new Card(35, 10, "Ten", "Spades", R.drawable.spades10),
            new Card(36, 10, "Jack", "Spades", R.drawable.spades11), new Card(37, 10, "Queen", "Spades", R.drawable.spades12),
            new Card(38, 10, "King", "Spades", R.drawable.spades13), new Card(39, 11, "Ace", "Spades", R.drawable.spades14),
            new Card(40, 2, "Two", "Clubs", R.drawable.clubs2),
            new Card(41, 3, "Three", "Clubs", R.drawable.clubs3), new Card(42, 4, "Four", "Clubs", R.drawable.clubs4),
            new Card(43, 5, "Five", "Clubs", R.drawable.clubs5), new Card(44, 6, "Six", "Clubs", R.drawable.clubs6),
            new Card(45, 7, "Seven", "Clubs", R.drawable.clubs7), new Card(46, 8, "Eight", "Clubs", R.drawable.clubs8),
            new Card(47, 9, "Nine", "Clubs", R.drawable.clubs9), new Card(48, 10, "Ten", "Clubs", R.drawable.clubs10),
            new Card(49, 10, "Jack", "Clubs", R.drawable.clubs11), new Card(50, 10, "Queen", "Clubs", R.drawable.clubs12),
            new Card(51, 10, "King", "Clubs", R.drawable.clubs13), new Card(52, 11, "Ace", "Clubs", R.drawable.clubs14)
            };
    Player user, dealer;
    int money;
    int bet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new Player(1);
        dealer = new Player(0);
        money = 200;
        bet = 0;


        final EditText betAmount = (EditText) findViewById(R.id.etBet);
        final TextView playertv = (TextView) findViewById(R.id.tvPlayer);
        final TextView dealertv = (TextView) findViewById(R.id.tvDealer);
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
                    Toast.makeText(getApplicationContext(),"You don't have that much money!",Toast.LENGTH_LONG);
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
                    dealertv.setText("Dealer: " + Integer.toString(dealer.getHandValue()));
                }
                endGame();
            }
        });
        hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.newCard();
                playertv.setText("Player: " + Integer.toString(user.getHandValue()));
                if (user.getHandValue() >= 21) {
                    endGame();
                } else if (dealer.isHold() == false){
                    Button placeBet = (Button) findViewById(R.id.btnPlaceBet);
                    Button hit = (Button) findViewById(R.id.btnHit);
                    Button stay = (Button) findViewById(R.id.btnStay);
                    placeBet.setEnabled(false);
                    hit.setEnabled(false);
                    stay.setEnabled(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dealer.newCard();
                            dealertv.setText("Dealer: " + Integer.toString(dealer.getHandValue()));
                            if (dealer.getHandValue() >= 21) {
                                endGame();
                            }else if (dealer.getHandValue() >= 18 && dealer.getHandValue() > user.getHandValue()) {
                                dealer.setHold(true);
                                dealertv.setText("Dealer: " + Integer.toString(dealer.getHandValue()) + "(Hold)");
                            }
                            Button placeBet = (Button) findViewById(R.id.btnPlaceBet);
                            Button hit = (Button) findViewById(R.id.btnHit);
                            Button stay = (Button) findViewById(R.id.btnStay);
                            placeBet.setEnabled(true);
                            hit.setEnabled(true);
                            stay.setEnabled(true);
                        }
                        }, 1000);
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
        TextView playerTv = (TextView) findViewById(R.id.tvPlayer);
        TextView dealerTv = (TextView) findViewById(R.id.tvDealer);
        Button placeBet = (Button) findViewById(R.id.btnPlaceBet);
        EditText betAmount = (EditText) findViewById(R.id.etBet);
        placeBet.setEnabled(true);
        betAmount.setEnabled(true);
        user = new Player(1);
        dealer = new Player(0);
        dealerTv.setText("Dealer: " + Integer.toString(dealer.getHandValue()));
        playerTv.setText("Player: " + Integer.toString(user.getHandValue()));
        Button resetBtn = (Button) findViewById(R.id.btnReset);
        Button hit = (Button) findViewById(R.id.btnHit);
        Button stay = (Button) findViewById(R.id.btnStay);
        placeBet.setEnabled(true);
        resetBtn.setEnabled(false);
        hit.setEnabled(true);
        stay.setEnabled(true);
        ImageView iv = (ImageView) findViewById(R.id.dealerCard1);
        iv.setVisibility(View.INVISIBLE);
        iv = (ImageView) findViewById(R.id.dealerCard2);
        iv.setVisibility(View.INVISIBLE);
        iv = (ImageView) findViewById(R.id.dealerCard3);
        iv.setVisibility(View.INVISIBLE);
        iv = (ImageView) findViewById(R.id.dealerCard4);
        iv.setVisibility(View.INVISIBLE);
        iv = (ImageView) findViewById(R.id.dealerCard5);
        iv.setVisibility(View.INVISIBLE);
        iv = (ImageView) findViewById(R.id.playerCard1);
        iv.setVisibility(View.INVISIBLE);
        iv = (ImageView) findViewById(R.id.playerCard2);
        iv.setVisibility(View.INVISIBLE);
        iv = (ImageView) findViewById(R.id.playerCard3);
        iv.setVisibility(View.INVISIBLE);
        iv = (ImageView) findViewById(R.id.playerCard4);
        iv.setVisibility(View.INVISIBLE);
        iv = (ImageView) findViewById(R.id.playerCard5);
        iv.setVisibility(View.INVISIBLE);
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
            bet = 0;
        } else if (tie) {
            finish.setText("It's a tie!");
            money += bet;
            moneyTv.setText("Total: " + money);
            bet = 0;
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
        private int playerID;
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
            handValue = 0;
            if (playerID == 1) {
                ImageView iv = (ImageView) findViewById(R.id.playerCard1);
                switch (cardCount) {
                    case 0:
                        iv.setImageDrawable(getResources().getDrawable(hand[cardCount].getImageId()));
                        iv.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        iv = (ImageView) findViewById(R.id.playerCard2);
                        iv.setImageDrawable(getResources().getDrawable(hand[cardCount].getImageId()));
                        iv.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        iv = (ImageView) findViewById(R.id.playerCard3);
                        iv.setImageDrawable(getResources().getDrawable(hand[cardCount].getImageId()));
                        iv.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        iv = (ImageView) findViewById(R.id.playerCard4);
                        iv.setImageDrawable(getResources().getDrawable(hand[cardCount].getImageId()));
                        iv.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        iv = (ImageView) findViewById(R.id.playerCard5);
                        iv.setImageDrawable(getResources().getDrawable(hand[cardCount].getImageId()));
                        iv.setVisibility(View.VISIBLE);
                        break;
                }
            } else if (playerID == 0) {
                ImageView iv = (ImageView) findViewById(R.id.dealerCard1);
                switch (cardCount) {
                    case 0:
                        iv.setImageDrawable(getResources().getDrawable(hand[cardCount].getImageId()));
                        iv.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        iv = (ImageView) findViewById(R.id.dealerCard2);
                        iv.setImageDrawable(getResources().getDrawable(hand[cardCount].getImageId()));
                        iv.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        iv = (ImageView) findViewById(R.id.dealerCard3);
                        iv.setImageDrawable(getResources().getDrawable(hand[cardCount].getImageId()));
                        iv.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        iv = (ImageView) findViewById(R.id.dealerCard4);
                        iv.setImageDrawable(getResources().getDrawable(hand[cardCount].getImageId()));
                        iv.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        iv = (ImageView) findViewById(R.id.dealerCard5);
                        iv.setImageDrawable(getResources().getDrawable(hand[cardCount].getImageId()));
                        iv.setVisibility(View.VISIBLE);
                        break;
                }
            }
            cardCount++;
            int checkValue = 0;
            for (int i = 0; i < cardCount; i++) {
                checkValue += hand[i].getValue();
            }
            for (int i = 0; i < cardCount; i++) {
                if (hand[i].getType().equals("Ace") && checkValue > 21) {
                       hand[i].setValue(1);
                   }
                handValue += hand[i].getValue();
            }
        }

        public int getHandValue() {
            return handValue;
        }

        public void setHandValue(int handValue) {
            this.handValue = handValue;
        }

        public Player(int player) {
            for (int i = 0; i < 5; i++) {
                hand[i] = new Card(0, 0, "Blank", "Blank", null);
            }
            cardCount = 0;
            handValue = 0;
            hold = false;
            playerID = player;
        }

    }
}

class Card {

    private int value;
    private int id;
    private String type;
    private String suit;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    private Integer imageId;

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

    public Card(int id, int value, String type, String suit, Integer imageId){
        this.id = id;
        this.value = value;
        this.type = type;
        this.suit = suit;
        this.imageId = imageId;
    }


}
