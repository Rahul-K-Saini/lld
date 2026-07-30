public class RoomLight extends AbstractLight implements Light {

  private final String color;

  public RoomLight() {
    this("WHITE", new WifiStrategy());
  }

  public RoomLight(String color, BrightnessStrategy brightnessStrategy) {
    super(brightnessStrategy);
    this.color = color;
  }

  @Override
  public String getColor() {
    return this.color;
  }

  @Override
  public void setBrightness(int brightness) {
    var brightnessChangeMethod = new BrightnessAcctoDeviceContext(new WifiStrategy());
    brightnessChangeMethod.setBrightness(brightness);
  }

}
