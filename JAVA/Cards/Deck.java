import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final Card[] deck = new Card[52];
   private int index = 0;

   public Deck(BufferedImage[][] images) {
      this.build(images);
   }

   public void reset() {
      this.index = 0;
   }

   private void build(BufferedImage[][] images) {
      int i = 0;
      for(int s = 1; s < 5; ++s) {
         for(int r = 1; r < 14; ++r) {
            this.deck[i] = new Card(r, s, images[s - 1][r - 1]);
            ++i;
         }
      }
   }

   public void shuffle() {
      List<Card> cardList = Arrays.asList(this.deck);
      Collections.shuffle(cardList);
      cardList.toArray(this.deck);
   }

   public Card draw() {
      Card card = this.deck[this.index];
      ++this.index;
      return card;
   }

   public void printDeck() {
       int DECK_SIZE = 52;
       for(int i = 0; i < DECK_SIZE; ++i) {
         System.out.println(this.deck[i]);
      }

   }
}
