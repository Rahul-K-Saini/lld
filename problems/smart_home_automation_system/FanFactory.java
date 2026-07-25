package problems.smart_home_automation_system;

public class FanFactory implements DeviceFactory {
    @Override
    public Device createDevice() {
        return new Fan();
    }
}

