import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Infection");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new Infection());
      frame.pack();
      frame.setVisible(true);
    });
  }
}