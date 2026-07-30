public abstract class AbstractLight extends AbstractDevice implements Light {
  protected int brightness;
  protected static final int DEFAULT_BRIGHTNESS = 100;

  protected AbstractLight() {
    super();
    this.brightness = DEFAULT_BRIGHTNESS;
  }

  @Override
  public void setBrightness(int brightness) {
    if (this.status == DeviceStatus.OFF)
      throw new IllegalStateException("Cannot change brightness device is OFF");
    if (brightness < 0 || brightness > 100)
      throw new IllegalStateException("Cannot change brightness device is INVALID");
    this.brightness = brightness;
  }

  @Override
  public int getBrightness() {
    return this.brightness;
  }
}
