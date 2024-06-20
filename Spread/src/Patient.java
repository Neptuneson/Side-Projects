import java.awt.*;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Set;

/**
 * The bouncing ball.
 */
public class Patient {
  float x, y;
  float speedX, speedY;
  float radius;
  private Color color;
  private Virus curVirus;
  boolean infected;
  float iRadius;
  private static final Color DEFAULT_COLOR = Color.BLUE;
  private Set<Integer> viruses;

  /**
   * Constructor: For user friendliness, user specifies velocity in speed and
   * moveAngle in usual Cartesian coordinates. Need to convert to speedX and
   * speedY in Java graphics coordinates for ease of operation.
   */
  public Patient(float x, float y, float radius, float speed, float angleInDegree, Color color, float iRadius, boolean infected, Virus curVirus) {
    this.x = x;
    this.y = y;
    // Convert (speed, angle) to (x, y), with y-axis inverted
    this.speedX = (float)(speed * Math.cos(Math.toRadians(angleInDegree)));
    this.speedY = (float)(-speed * Math.sin(Math.toRadians(angleInDegree)));
    this.radius = radius;
    this.color = color;
    this.iRadius = radius + iRadius;
    this.infected = infected;
    this.curVirus = curVirus;
    viruses = new HashSet<>();
  }

  public void mutate(Virus virus) {
    curVirus = virus;
    color = virus.getColor();
    viruses.add(virus.getID());
  }

  public void infect(Virus virus) {
    if (!infected && !viruses.contains(virus.getID())) {
      infected = true;
      curVirus = virus;
      color = virus.getColor();
      viruses.add(virus.getID());
    }
  }

  public void checkUp() {
    if (infected) {
      if (Math.random() < curVirus.getThreshold()) {
        heal();
      }
    }
  }

  public void heal() {
    infected = false;
    curVirus = null;
    color = Color.BLUE;
  }

  public Virus getVirus() {
    return curVirus;
  }

  /** Draw itself using the given graphics context. */
  public void draw(Graphics g) {
    g.setColor(color);
    g.fillOval((int)(x - radius), (int)(y - radius), (int)(2 * radius), (int)(2 * radius));
    g.drawOval((int)(x - iRadius), (int)(y - iRadius), (int)(2 * iRadius), (int)(2 * iRadius));
  }

  /**
   * Make one move, check for collision and react accordingly if collision occurs.
   *
   * @param box: the container (obstacle) for this ball.
   */
  public void moveOneStepWithCollisionDetection(CollisionBox box) {
    // Get the ball's bounds, offset by the radius of the ball
    float ballMinX = box.minX + iRadius;
    float ballMinY = box.minY + iRadius;
    float ballMaxX = box.maxX - iRadius;
    float ballMaxY = box.maxY - iRadius;

    // Calculate the ball's new position
    x += speedX;
    y += speedY;
    // Check if the ball moves over the bounds. If so, adjust the position and speed.
    if (x < ballMinX) {
      speedX = -speedX; // Reflect along normal
      x = ballMinX;     // Re-position the ball at the edge
    } else if (x > ballMaxX) {
      speedX = -speedX;
      x = ballMaxX;
    }
    // May cross both x and y bounds
    if (y < ballMinY) {
      speedY = -speedY;
      y = ballMinY;
    } else if (y > ballMaxY) {
      speedY = -speedY;
      y = ballMaxY;
    }
  }

  /** Return the magnitude of speed. */
  public float getSpeed() {
    return (float)Math.sqrt(speedX * speedX + speedY * speedY);
  }

  /** Return the direction of movement in degrees (counter-clockwise). */
  public float getMoveAngle() {
    return (float)Math.toDegrees(Math.atan2(-speedY, speedX));
  }

  /** Return mass */
  public float getMass() {
    return radius * radius * radius / 1000f;  // Normalize by a factor
  }

  /** Return the kinetic energy (0.5mv^2) */
  public float getKineticEnergy() {
    return 0.5f * getMass() * (speedX * speedX + speedY * speedY);
  }

  /** Describe itself. */
  public String toString() {
    sb.delete(0, sb.length());
    formatter.format("@(%3.0f,%3.0f) r=%3.0f V=(%2.0f,%2.0f) " +
        "S=%4.1f \u0398=%4.0f KE=%3.0f",
      x, y, radius, speedX, speedY, getSpeed(), getMoveAngle(),
      getKineticEnergy());  // \u0398 is theta
    return sb.toString();
  }
  // Re-use to build the formatted string for toString()
  private StringBuilder sb = new StringBuilder();
  private Formatter formatter = new Formatter(sb);
}