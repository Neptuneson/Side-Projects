import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
/**
 * The control logic and main display panel for game.
 */
public class Infection extends JPanel {
  private Random rand;
  private static final int UPDATE_RATE = 30;  // Frames per second (fps)
  private final static int SIZE = 800;
  private final static int SPEED = 3;
  private static final int numOfPatients = 50;
  private static final int numOfInfected = 5;
  private static int virusID = 0;
  private static Patient[] patients;
  private final static int pRadius = 3;
  private final static int infectRadius = 5;
  private final CollisionBox box;  // The container rectangular box

  private final DrawCanvas canvas; // Custom canvas for drawing the box/ball
  private int canvasWidth = SIZE;
  private int canvasHeight = SIZE;

  /**
   * Constructor to create the UI components and init the game objects.
   * Set the drawing canvas to fill the screen (given its width and height).
   */
  public Infection() {
    rand = new Random();
    rand.setSeed(7);

    patients = new Patient[numOfPatients];

    Virus virus = new Virus(virusID, new Color(255, 0, 255), 0.001, 0.0001);
    virusID++;

    for(int i = 0; i < numOfPatients; i++) {
      int iRadius = infectRadius;
      int radius = pRadius;
      int x = rand.nextInt(canvasWidth - radius * 2 - 20) + radius + 10;
      int y = rand.nextInt(canvasHeight - radius * 2 - 20) + radius + 10;
      int speed = SPEED;
      int angleInDegree = rand.nextInt(360);

      Patient patient;
      if (i < numOfInfected) {
        patient = new Patient(x, y, radius, speed, angleInDegree, virus.getColor(), iRadius, true, virus);
      } else {
        patient = new Patient(x, y, radius, speed, angleInDegree, Color.GREEN, iRadius, false, null);
      }
      patients[i] = patient;
    }

    // Init the Container Box to fill the screen
    box = new CollisionBox(0, 0, canvasWidth, canvasHeight, Color.BLACK, Color.WHITE);

    // Init the custom drawing panel for drawing the game
    canvas = new DrawCanvas();
    this.setLayout(new BorderLayout());
    this.add(canvas, BorderLayout.CENTER);

    // Handling window resize.
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        Component c = (Component)e.getSource();
        Dimension dim = c.getSize();
        canvasWidth = dim.width;
        canvasHeight = dim.height;
        // Adjust the bounds of the container to fill the window
        box.set(0, 0, canvasWidth, canvasHeight);
      }
    });

    // Start the ball bouncing
    gameStart();
  }

  /** Start the ball bouncing. */
  public void gameStart() {
    // Run the game logic in its own thread.
    Thread gameThread = new Thread() {
      public void run() {
        while (true) {
          // Execute one time-step for the game
          gameUpdate();
          // Refresh the display
          repaint();
          // Delay and give other thread a chance
          try {
            Thread.sleep(1000 / UPDATE_RATE);
          } catch (InterruptedException ex) {}
        }
      }
    };
    gameThread.start();  // Invoke GaemThread.run()
  }

  /**
   * One game time-step.
   * Update the game objects, with proper collision detection and response.
   */
  public void gameUpdate() {
    for(Patient patient: patients) {
      mutate(patient);
      infect(patient);
      patient.checkUp();
      patient.moveOneStepWithCollisionDetection(box);
    }
  }

  public void mutate(Patient patient) {
    if (patient.infected) {
      if (Math.random() < patient.getVirus().getMutThreshold()) {

        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColor = new Color(r, g, b);
        Virus virus = new Virus(virusID, randomColor.brighter(), patient.getVirus().getThreshold() / 10, patient.getVirus().getMutThreshold() / 10);
        virusID++;
        patient.mutate(virus);
      }
    }
  }

  public void infect(Patient patient) {
    if (patient.infected) {
      for (Patient adjPatient : patients) {
        double dist = Math.sqrt(Math.pow((patient.x - adjPatient.x), 2) + Math.pow((patient.y - adjPatient.y), 2));
        if(dist <= 2 * patient.iRadius) {
          adjPatient.infect(patient.getVirus());
        }
      }
    }
  }

  /** The custom drawing panel for the bouncing ball (inner class). */
  class DrawCanvas extends JPanel {
    /** Custom drawing codes */
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);    // Paint background
      // Draw the box and the ball
      box.draw(g);
      for(Patient patient: patients) {
        patient.draw(g);
      }
    }

    /** Called back to get the preferred size of the component. */
    @Override
    public Dimension getPreferredSize() {
      return (new Dimension(canvasWidth, canvasHeight));
    }
  }
}