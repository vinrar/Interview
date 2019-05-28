package Device.Interfaces;

public interface SmartHomeDeviceInterface {
    boolean turnOn();

    boolean turnOff();

    boolean connectTo(SmartInterface smartInterface);

    boolean disconnectFrom(SmartInterface smartInterface);

    boolean isDeviceOn();
}