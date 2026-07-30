public class ZigbeeStrategy implements BrightnessStrategy {
  @Override
  public void adjustBrightness(int value) {
    System.out.println("Adjusting brightness using Zigbee protocol to " + value);
  }
}
