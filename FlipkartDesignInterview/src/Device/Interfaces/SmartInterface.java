package Device.Interfaces;

import Device.AbstractSmartHomeDevice;

import java.util.List;

public interface SmartInterface {

    boolean addSmartHomeDevice(String type, String location, AbstractSmartHomeDevice smartHomeDevice);

    SmartHomeDeviceInterface getSmartHomeDevice(String type, String location);

    boolean giveCommand(SmartInterface smartInterface, String type, String location, String command);

    List<SmartHomeDeviceInterface> getConnectedHomeDevices();
}