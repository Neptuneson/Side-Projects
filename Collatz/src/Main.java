public class Main {
  private static final int RUNS = 1000;
  public static void main(String[] args) {
    for (int i = 2; i <= RUNS; i++) {
      System.out.printf("Starting: %d\n", i);
      collatz(i);
    }
  }
  private static int collatz(int value) {
    System.out.println(value);
    if (value == 1) {
      return 1;
    } else if (value % 2 == 0) {
      return collatz(value / 2);
    } else {
      return collatz((3 * value + 1) / 2);
    }
  }
}