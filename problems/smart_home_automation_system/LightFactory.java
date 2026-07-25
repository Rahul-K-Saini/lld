package problems.smart_home_automation_system;

// this right here is the simple factory not the full factory pattern, 
// the full factory pattern is the one that has a factory for each type of device, 
// this is just a simple factory that creates devices based on the type passed in the constructor
public class LightFactory implements DeviceFactory {

  public enum LightType {
    ROOM_LIGHT,
    SMART_RGB_LIGHT
  }

  private final LightType lightType;

  public LightFactory(LightType lightType) {
    this.lightType = lightType;
  }

  @Override
  public Device createDevice() {
    return createLight();
  }

  public Light createLight() {
    return switch (lightType) {
      case ROOM_LIGHT -> new RoomLight();
      case SMART_RGB_LIGHT -> new SmartRGBLight();
      default -> throw new IllegalArgumentException("Unknown light type: " + lightType);
    };
  }

  public Light createLight(String parameter) {
    return switch (lightType) {
      case ROOM_LIGHT -> new RoomLight(parameter);
      case SMART_RGB_LIGHT -> new SmartRGBLight(parameter);
      default -> throw new IllegalArgumentException("Unknown light type: " + lightType);
    };
  }
}
