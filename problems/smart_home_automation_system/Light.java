package problems.smart_home_automation_system;

public interface Light extends Device {
    String getColor();
    void setBrightness(int brightness);
    int getBrightness();
}
