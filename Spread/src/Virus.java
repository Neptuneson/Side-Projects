import java.awt.*;

public class Virus {
  private final int id;
  private final Color color;
  private final double threshold;
  private final double MutThreshold;
  public Virus(int id, Color color, double threshold, double MutThreshold) {
    this.id = id;
    this.color = color;
    this.threshold = threshold;
    this.MutThreshold = MutThreshold;
  }
  public int getID() {
    return id;
  }
  public Color getColor() {
    return color;
  }
  public double getThreshold() {
    return threshold;
  }

  public double getMutThreshold() {
    return MutThreshold;
  }
}
