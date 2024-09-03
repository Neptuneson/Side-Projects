import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
   private static JFrame frame;
   private static JLabel card1Image;
   private static JLabel card2Image;
   private static JLabel card3Image;
   private static JLabel card4Image;
   private static JLabel card5Image;
   private static JLabel betLabel;
   private static JLabel currentBet;
   private static JTextField betInput;
   private static JButton draw;
   private static JButton deal;
   private static JToggleButton hold1;
   private static JToggleButton hold2;
   private static JToggleButton hold3;
   private static JToggleButton hold4;
   private static JToggleButton hold5;
   private static final BufferedImage[][] images = new BufferedImage[4][13];
   private static ImageIcon cardBack;
   private static Deck deck;
   private static final Card[] flop = new Card[5];
   private static boolean isPressed1;
   private static boolean isPressed2;
   private static boolean isPressed3;
   private static boolean isPressed4;
   private static boolean isPressed5;
   private static int bank = 100;
   private static int bet = 0;

   public static void main(String[] args) throws IOException {
      populateImages();
      deck = new Deck(images);
      frame = new JFrame("Cards");
      frame.setPreferredSize(new Dimension(1500, 500));
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      buildGui();
      frame.pack();
      frame.setExtendedState(6);
      frame.setVisible(true);
   }

   private static void buildGui() {
      Container window = frame.getContentPane();
      window.setBackground(Color.decode("#3F3432"));
      window.setLayout(new BorderLayout());
      JPanel betPanel = new JPanel();
      betPanel.setOpaque(true);
      betPanel.setBackground(Color.decode("#3F3432"));
      betLabel = new JLabel("Bank: $" + bank);
      betLabel.setForeground(Color.white);
      betPanel.add(betLabel);
      betInput = new JTextField("0");
      betInput.setPreferredSize(new Dimension(100, 20));
      betPanel.add(betInput);
      JButton betButton = new JButton("Enter Bet");
      betButton.addActionListener((e) -> {
         bet = Integer.parseInt(betInput.getText());
         bank -= bet;
         currentBet.setText("Current Bet: $" + bet);
         betLabel.setText("Bank: $" + bank);
         betInput.setText("0");
         deal.setEnabled(true);
      });
      betPanel.add(betButton);
      currentBet = new JLabel("Current Bet: $" + bet);
      currentBet.setForeground(Color.white);
      betPanel.add(currentBet);
      window.add(betPanel, "South");
      draw = new JButton("Draw");
      draw.addActionListener((e) -> {
         BufferedImage image;
         if (!isPressed1) {
            flop[0] = deck.draw();
            image = flop[0].image();
            card1Image.setIcon(new ImageIcon(image.getScaledInstance(image.getWidth() / 2, image.getHeight() / 2, 4)));
         }

         if (!isPressed2) {
            flop[1] = deck.draw();
            image = flop[1].image();
            card2Image.setIcon(new ImageIcon(image.getScaledInstance(image.getWidth() / 2, image.getHeight() / 2, 4)));
         }

         if (!isPressed3) {
            flop[2] = deck.draw();
            image = flop[2].image();
            card3Image.setIcon(new ImageIcon(image.getScaledInstance(image.getWidth() / 2, image.getHeight() / 2, 4)));
         }

         if (!isPressed4) {
            flop[3] = deck.draw();
            image = flop[3].image();
            card4Image.setIcon(new ImageIcon(image.getScaledInstance(image.getWidth() / 2, image.getHeight() / 2, 4)));
         }

         if (!isPressed5) {
            flop[4] = deck.draw();
            image = flop[4].image();
            card5Image.setIcon(new ImageIcon(image.getScaledInstance(image.getWidth() / 2, image.getHeight() / 2, 4)));
         }

         draw.setEnabled(false);
         int score = handEval();
         if (score > 0) {
            bank += score * bet;
         } else {
            bank -= bet;
         }

         betLabel.setText("Bank: $" + bank);
      });
      betPanel.add(draw);
      deal = new JButton("Deal");
      deal.addActionListener((e) -> {
         if (isPressed1) {
            hold1.doClick();
            isPressed1 = false;
         }

         if (isPressed2) {
            hold2.doClick();
            isPressed2 = false;
         }

         if (isPressed3) {
            hold3.doClick();
            isPressed3 = false;
         }

         if (isPressed4) {
            hold4.doClick();
            isPressed4 = false;
         }

         if (isPressed5) {
            hold5.doClick();
            isPressed5 = false;
         }

         draw.setEnabled(true);
         deck.reset();
         deck.shuffle();

         for(int i = 0; i < 5; ++i) {
            flop[i] = deck.draw();
         }

         BufferedImage image1 = flop[0].image();
         card1Image.setIcon(new ImageIcon(image1.getScaledInstance(image1.getWidth() / 2, image1.getHeight() / 2, 4)));
         BufferedImage image2 = flop[1].image();
         card2Image.setIcon(new ImageIcon(image2.getScaledInstance(image2.getWidth() / 2, image2.getHeight() / 2, 4)));
         BufferedImage image3 = flop[2].image();
         card3Image.setIcon(new ImageIcon(image3.getScaledInstance(image3.getWidth() / 2, image3.getHeight() / 2, 4)));
         BufferedImage image4 = flop[3].image();
         card4Image.setIcon(new ImageIcon(image4.getScaledInstance(image4.getWidth() / 2, image4.getHeight() / 2, 4)));
         BufferedImage image5 = flop[4].image();
         card5Image.setIcon(new ImageIcon(image5.getScaledInstance(image5.getWidth() / 2, image5.getHeight() / 2, 4)));
         deal.setEnabled(false);
      });
      betPanel.add(deal);
      JPanel cardHold = new JPanel();
      cardHold.setLayout(new BorderLayout());
      JPanel cards = new JPanel();
      cards.setBackground(Color.decode("#079447"));
      cards.setLayout(new GridLayout(1, 5));
      card1Image = new JLabel(cardBack);
      cards.add(card1Image);
      card2Image = new JLabel(cardBack);
      cards.add(card2Image);
      card3Image = new JLabel(cardBack);
      cards.add(card3Image);
      card4Image = new JLabel(cardBack);
      cards.add(card4Image);
      card5Image = new JLabel(cardBack);
      cards.add(card5Image);
      cardHold.add(cards, "Center");
      JPanel holds = new JPanel();
      holds.setBackground(Color.decode("#3F3432"));
      holds.setLayout(new GridLayout(1, 5));
      hold1 = new JToggleButton("Hold");
      hold1.addActionListener((e) -> {
         isPressed1 = !isPressed1;
      });
      holds.add(hold1);
      hold2 = new JToggleButton("Hold");
      hold2.addActionListener((e) -> {
         isPressed2 = !isPressed2;
      });
      holds.add(hold2);
      hold3 = new JToggleButton("Hold");
      hold3.addActionListener((e) -> {
         isPressed3 = !isPressed3;
      });
      holds.add(hold3);
      hold4 = new JToggleButton("Hold");
      hold4.addActionListener((e) -> {
         isPressed4 = !isPressed4;
      });
      holds.add(hold4);
      hold5 = new JToggleButton("Hold");
      hold5.addActionListener((e) -> {
         isPressed5 = !isPressed5;
      });
      holds.add(hold5);
      draw.setEnabled(false);
      deal.setEnabled(false);
      cardHold.add(holds, "South");
      window.add(cardHold, "Center");
      JPanel north = new JPanel();
      north.setBackground(Color.decode("#3F3432"));
      north.setPreferredSize(new Dimension(640, 40));
      window.add(north, "North");
      JPanel west = new JPanel();
      west.setBackground(Color.decode("#3F3432"));
      west.setPreferredSize(new Dimension(40, 480));
      window.add(west, "West");
      JPanel east = new JPanel();
      east.setBackground(Color.decode("#3F3432"));
      east.setPreferredSize(new Dimension(40, 480));
      window.add(east, "East");
   }

   private static void populateImages() throws IOException {
      File cardBackFile = new File("src/images/back.png");
      BufferedImage cardBackImage = ImageIO.read(cardBackFile);
      cardBack = new ImageIcon(cardBackImage.getScaledInstance((int)((double)cardBackImage.getWidth() / 1.2D), (int)((double)cardBackImage.getHeight() / 1.2D), 4));
      File directory = new File("src/images/PNG-cards-1.3");
      File[] files = directory.listFiles();

      assert files != null;

       for (File file : files) {
           String name = file.getName();
           int rank;
           if (name.contains("ace")) {
               rank = 0;
           } else if (name.contains("jack")) {
               rank = 10;
           } else if (name.contains("queen")) {
               rank = 11;
           } else if (name.contains("king")) {
               rank = 12;
           } else if (name.contains("10")) {
               rank = Integer.parseInt(name.substring(0, 2)) - 1;
           } else {
               rank = Integer.parseInt(name.substring(0, 1)) - 1;
           }

           byte suit;
           if (name.contains("spades")) {
               suit = 0;
           } else if (name.contains("clubs")) {
               suit = 1;
           } else if (name.contains("hearts")) {
               suit = 2;
           } else {
               suit = 3;
           }

           images[suit][rank] = ImageIO.read(file);
       }

   }

   private static int handEval() {
      List<Card> flopList = Arrays.asList(flop);
      Collections.sort(flopList);
      flopList.toArray(flop);
      if (isRoyalFlush()) {
         return 250;
      } else if (isStraightFlush()) {
         return 50;
      } else if (isFourOfAKind()) {
         if (hasFourOfWhat() == 1) {
            return 80;
         } else {
            return hasFourOfWhat() <= 4 ? 40 : 25;
         }
      } else if (isFullHouse()) {
         return 10;
      } else if (isFlush()) {
         return 8;
      } else if (isStraight()) {
         return 5;
      } else if (isThreeOfAKind()) {
         return 3;
      } else if (isTwoPair()) {
         return 1;
      } else if (isPair()) {
         return 1;
      } else {
         return 0;
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

   private static int hasFourOfWhat() {
      return flop[0].rank() == flop[1].rank() ? flop[0].rank() : flop[4].rank();
   }
}
