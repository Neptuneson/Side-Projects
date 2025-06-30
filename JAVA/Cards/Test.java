import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {
   private static Card[] flop;

   public static void main(String[] args) {
       flop = new Card[]{new Card(4, 1, null),
               new Card(4, 2, null),
               new Card(3, 1, null),
               new Card(2, 2, null),
               new Card(2, 3, null)};
       System.out.println(handEval());
   }

   private static int handEval() {
      int result = 0;
      List<Card> flopList = Arrays.asList(flop);
      Collections.sort(flopList);
      flopList.toArray(flop);
      if (isRoyalFlush()) {
         return 250;
      } else if (isStraightFlush()) {
         return 50;
      } else if (isFourOfAKind()) {
         return 75;
      } else if (isFullHouse()) {
         return 10;
      } else if (isFlush()) {
         return 8;
      } else if (isStraight()) {
         return 5;
      } else if (isThreeOfAKind()) {
         return 3;
      } else if (isTwoPair()) {
         System.out.println("isTwoPair");
         return 1;
      } else if (isPair()) {
         System.out.println("isPair");
         return 1;
      } else if (isHigh()) {
         System.out.println("High");
         return 1;
      } else {
         return result;
      }
   }

   private static boolean isRoyalFlush() {
      if (isFlush() && flop[0].rank() == 1) {
         for(int i = 1; i < flop.length - 1; ++i) {
            if (flop[i].rank() + 1 != flop[i + 1].rank()) {
               return false;
            }
         }
         return true;
      } else {
         return false;
      }
   }

   private static boolean isStraightFlush() {
      return isStraight() && isFlush();
   }

   private static boolean isFourOfAKind() {
      return flop[0].rank() == flop[1].rank() && flop[1].rank() == flop[2].rank() && flop[2].rank() == flop[3].rank() && flop[3].rank() != flop[4].rank() || flop[0].rank() != flop[1].rank() && flop[1].rank() == flop[2].rank() && flop[2].rank() == flop[3].rank() && flop[3].rank() == flop[4].rank();
   }

   private static boolean isFullHouse() {
      return flop[0].rank() == flop[1].rank() && flop[1].rank() == flop[2].rank() && flop[2].rank() != flop[3].rank() && flop[3].rank() == flop[4].rank() || flop[0].rank() == flop[1].rank() && flop[1].rank() != flop[2].rank() && flop[2].rank() == flop[3].rank() && flop[3].rank() == flop[4].rank();
   }

   private static boolean isFlush() {
      for(int i = 0; i < flop.length - 1; ++i) {
         if (flop[i].suit() != flop[i + 1].suit()) {
            return false;
         }
      }
      return true;
   }

   private static boolean isStraight() {
      for(int i = 1; i < flop.length - 1; ++i) {
         if (flop[i].rank() + 1 != flop[i + 1].rank()) {
            return false;
         }
      }
      return true;
   }

   private static boolean isThreeOfAKind() {
      return flop[0].rank() == flop[1].rank() && flop[1].rank() == flop[2].rank() || flop[1].rank() == flop[2].rank() && flop[2].rank() == flop[3].rank() || flop[2].rank() == flop[3].rank() && flop[3].rank() == flop[4].rank();
   }

   private static boolean isTwoPair() {
      return flop[0].rank() == flop[1].rank() && flop[2].rank() == flop[3].rank() || flop[0].rank() == flop[1].rank() && flop[3].rank() == flop[4].rank() || flop[1].rank() == flop[2].rank() && flop[3].rank() == flop[4].rank();
   }

   private static boolean isPair() {
      for(int i = 0; i < flop.length - 1; ++i) {
         if (flop[i].rank() == flop[i + 1].rank()) {
            return true;
         }
      }
      return false;
   }

   private static boolean isHigh() {
      Card[] var0 = flop;
       for (Card card : var0) {
           if (card.rank() >= 11) {
               return true;
           }
       }
      return false;
   }
}
