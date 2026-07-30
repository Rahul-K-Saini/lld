public class BrightnessAcctoDeviceContext {
  private BrightnessStrategy strategy;

  public BrightnessAcctoDeviceContext(BrightnessStrategy strategy) {
    this.strategy = strategy;
  }

  public void setBrightness(int value) {
    strategy.adjustBrightness(value);
  }
}
