package problems.smart_home_automation_system;

public abstract class AbstractDevice implements Device {
    protected DeviceStatus status;

    protected AbstractDevice() {
        this.status = DeviceStatus.OFF;
    }

    @Override
    public void off() {
        this.status = DeviceStatus.OFF;
    }

    @Override
    public void on() {
        this.status = DeviceStatus.ON;
    }

    @Override
    public String getStatus() {
        return this.status.name();
    }
}

