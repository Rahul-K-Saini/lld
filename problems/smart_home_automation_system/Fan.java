public class Fan extends AbstractDevice {

  private int speed;
  private static final int MAX_SPEED = 3;

  public Fan() {
    super();
    this.speed = 0;
  }

  public void setSpeed(int speed) {
    if (this.status == DeviceStatus.OFF && speed > 0) {
      throw new IllegalStateException("Cannot set speed when device is OFF");
    }
    if (speed < 0 || speed > MAX_SPEED) {
      throw new IllegalStateException("Speed must be between 0 and " + MAX_SPEED);
    }
    this.speed = speed;
  }

  public int getSpeed() {
    return this.speed;
  }

  public String getSpeedName() {
    return switch (this.speed) {
      case 0 -> "OFF";
      case 1 -> "LOW";
      case 2 -> "MEDIUM";
      case 3 -> "HIGH";
      default -> "UNKNOWN";
    };
  }
}
