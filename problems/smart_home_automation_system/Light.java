public interface Light extends Device {
  String getColor();

  void setBrightness(int brightness);

  int getBrightness();
}
