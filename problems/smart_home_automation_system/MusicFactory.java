
public class MusicFactory implements DeviceFactory {
  @Override
  public Device createDevice() {
    return new Music();
  }
}
