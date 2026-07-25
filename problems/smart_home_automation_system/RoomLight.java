package problems.smart_home_automation_system;

public class RoomLight extends AbstractLight implements Light {

  private final String color;

  public RoomLight() {
    this("WHITE");
  }

  public RoomLight(String color) {
    super();
    this.color = color;
  }

  @Override
  public String getColor() {
    return this.color;
  }

}
