public class WifiStrategy implements BrightnessStrategy {
  @Override
  public void adjustBrightness(int value) {
    System.out.println("Adjusting brightness using WiFi protocol to " + value);
  }
}
