package problems.smart_home_automation_system;

public class MusicFactory implements DeviceFactory {
    @Override
    public Device createDevice() {
        return new Music();
    }
}

