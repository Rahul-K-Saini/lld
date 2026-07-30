public class FanFactory implements DeviceFactory {
  @Override
  public Device createDevice() {
    return new Fan();
  }
}
