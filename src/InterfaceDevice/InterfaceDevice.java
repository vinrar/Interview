package InterfaceDevice;

import Device.AbstractSmartHomeDevice;
import Device.Interfaces.SmartHomeDeviceInterface;
import Device.Interfaces.SmartInterface;
import Device.LocationEnabledDevice;

import java.util.*;

public class InterfaceDevice extends AbstractSmartInterface{

    public InterfaceDevice(String name, String location, String command) {
        this.name = name;
        this.location = location;
        this.command = command;
    }

    Map<String, Map<String, AbstractSmartHomeDevice>> locationDeviceMap = new HashMap<>();
    Map<String, AbstractSmartHomeDevice> smartDevicesSet = new HashMap<>();

    @Override
    public boolean addSmartHomeDevice(String type, String location, AbstractSmartHomeDevice smartHomeDevice) {

        if(smartHomeDevice instanceof LocationEnabledDevice) {
            if(location == null) {
                System.out.println("Location should be enabled for adding smart device: " + smartHomeDevice.getName());
                return false;
            }

            Map<String, AbstractSmartHomeDevice> locationEnabledDevices = locationDeviceMap.get(smartHomeDevice);
            if(locationEnabledDevices != null && locationEnabledDevices.get(smartHomeDevice.getName()) != null) {
                System.out.println("Device: " + smartHomeDevice.getName() + " already connected to the Interface");
            }

            if(locationEnabledDevices == null) {
                locationEnabledDevices = new HashMap<>();
                locationEnabledDevices.put(smartHomeDevice.getName(), smartHomeDevice);
                locationDeviceMap.put(location, locationEnabledDevices);
            } else {
                locationEnabledDevices.put(smartHomeDevice.getName(), smartHomeDevice);
            }
        } else {
            smartDevicesSet.put(smartHomeDevice.getName(), smartHomeDevice);
        }

        return true;
    }

    @Override
    public SmartHomeDeviceInterface getSmartHomeDevice(String name, String location) {
        if(location == null || location.isEmpty())
            getSmartHomeDevice(name);

        Map<String, AbstractSmartHomeDevice> map = locationDeviceMap.get(location);

        if(map == null || map.isEmpty()) {
            System.out.println("No device with the given name: " + name + " is configured to this interface: " + this.name);
        } else {
            return map.get(name);
        }

        return null;
    }


    private SmartHomeDeviceInterface getSmartHomeDevice(String name) {
        SmartHomeDeviceInterface device = smartDevicesSet.get(name);
        if(device == null) {
            System.out.println("No such device connected");
        }

        return device;
    }

    @Override
    public boolean giveCommand(SmartInterface smartInterface, String type, String location, String command) {
        return true;
    }

    @Override
    public List<SmartHomeDeviceInterface> getConnectedHomeDevices() {
        List<SmartHomeDeviceInterface> list = new ArrayList<>();
        //list.addAll(smartDevicesSet.values());
        for(Map<String, AbstractSmartHomeDevice> map : locationDeviceMap.values()){
            if(map != null)
                list.addAll(map.values());
        }

        return list;
    }
}