package Device.Driver;

import Device.AbstractSmartHomeDevice;
import Device.ElectricDevice;
import Device.SmartFanDevice;
import Device.SmartLightDevice;
import InterfaceDevice.InterfaceDevice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DriverAdapter {

    private static Set<InterfaceDevice> interfaces = new HashSet<>();
    private static Map<String, Set<InterfaceDevice>> locationInterfaceDeviceMap = new HashMap<>();
    private static Map<String, InterfaceDevice> nameInterfaceDeviceMap = new HashMap<>();

    public static final String SMART_DEVICE_LIGHT = "Light";
    public static final String SMART_DEVICE_FAN = "Fan";

    public static void executeCommand(String interfaceDeviceName, String smartHomeDeviceName, String location, String command) {
        CommandProcessor.executeCommand(nameInterfaceDeviceMap, interfaceDeviceName, smartHomeDeviceName, location, command);
        try {
            Thread.sleep(1000);
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void addInterfaceDeviceToSetAndLocationMap(InterfaceDevice interfaceDevice) {
        if(interfaceDevice == null) {
            System.out.println("Cannot add an invalid interface device");
            return;
        }
        Set<InterfaceDevice> set = locationInterfaceDeviceMap.get(interfaceDevice.getLocation());

        if(set == null) {
            set = new HashSet<InterfaceDevice>();
            locationInterfaceDeviceMap.put(interfaceDevice.getLocation(), set);
        }

        set.add(interfaceDevice);
        interfaces.add(interfaceDevice);
        nameInterfaceDeviceMap.put(interfaceDevice.getName(), interfaceDevice);
    }

    public static InterfaceDevice addInterfaceDevice(String name, String location, String command) {
        if(name == null || name.isEmpty() || location == null || location.isEmpty() || command == null || command.isEmpty()) {
            return null;
        }
        InterfaceDevice interfaceDevice = new InterfaceDevice(name, location, command);
        System.out.println("Added interface device: " + name + " At location: " + location + " for command: " + command);
        return interfaceDevice;
    }

    public static void addSmartHomeDevice(String name, String location, String interfaceDeviceName) {
        if(name == null || name.isEmpty() || interfaceDeviceName == null || interfaceDeviceName.isEmpty()) {
            System.out.println("Invalid device");
            return;
        }

        InterfaceDevice interfaceDevice = nameInterfaceDeviceMap.get(interfaceDeviceName);

        if(interfaceDevice == null) {
            System.out.println("No interface is configured with the given name: " + interfaceDeviceName);
            return;
        }

        AbstractSmartHomeDevice smartHomeDevice = null;

        if(location == null || location.isEmpty()) {
            switch(name){
                default :
                    smartHomeDevice = new ElectricDevice(name);
            }
        } else {
            switch(name) {
                case SMART_DEVICE_LIGHT:
                    smartHomeDevice = new SmartLightDevice(name, location);
                    break;

                case SMART_DEVICE_FAN:
                    smartHomeDevice = new SmartFanDevice(name, location);
                    break;

                default:
                    System.out.println("Invalid entry");
            }
        }

        if(smartHomeDevice != null) {
            interfaceDevice.addSmartHomeDevice(name, location, smartHomeDevice);
            smartHomeDevice.connectTo(interfaceDevice);
            System.out.println("Added " + smartHomeDevice.getName() + " to " + interfaceDevice.getName());
        } else {
            System.out.println("Unable to create smart home device");
        }
    }
}
