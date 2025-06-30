import java.awt.image.BufferedImage;

public record Card(int rank, int suit, BufferedImage image) implements Comparable<Card> {

   public String toString() {
      String suitName = "";
      if (this.suit == 1) {
         suitName = "spades";
      } else if (this.suit == 2) {
         suitName = "clubs";
      } else if (this.suit == 3) {
         suitName = "hearts";
      } else if (this.suit == 4) {
         suitName = "diamonds";
      }

      String rankName;
      if (this.rank == 13) {
         rankName = "King";
      } else if (this.rank == 12) {
         rankName = "Queen";
      } else if (this.rank == 11) {
         rankName = "Jack";
      } else if (this.rank == 1) {
         rankName = "Ace";
      } else {
         rankName = String.valueOf(this.rank);
      }

      return String.format("%s of %s", rankName, suitName);
   }

   public int compareTo(Card o) {
      return Integer.compare(this.rank(), o.rank());
   }
}
