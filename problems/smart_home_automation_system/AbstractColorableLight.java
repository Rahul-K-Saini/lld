public abstract class AbstractColorableLight extends AbstractLight implements ColorableLight {
  protected String color;

  protected AbstractColorableLight(String color) {
    super();
    setColor(color);
  }

  @Override
  public void setColor(String hex) {
    if (hex == null)
      throw new NullPointerException("hex is null");
    String normalized = hex.trim();
    if (!normalized.startsWith("#"))
      normalized = "#" + normalized;
    if (!normalized.matches("^#([A-Fa-f0-9]{6})$"))
      throw new IllegalArgumentException("Invalid hex string");
    this.color = normalized.toUpperCase();
  }

  @Override
  public String getColor() {
    return this.color;
  }
}
