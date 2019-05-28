package Device.Driver;

import Device.AbstractSmartHomeDevice;
import Device.ElectricDevice;
import Device.SmartFanDevice;
import Device.SmartLightDevice;
import InterfaceDevice.InterfaceDevice;

import java.util.HashMap;
import java.util.Map;

public class ConnectionDriver {

    private static Map<String, Map<String, InterfaceDevice>> locationInterfaceDeviceMap = new HashMap<>(16);
    private static Map<String, InterfaceDevice> nameInterfaceDeviceMap = new HashMap<>(16);

    public static final String SMART_DEVICE_LIGHT = "Light";
    public static final String SMART_DEVICE_FAN = "Fan";

    public static void main(String[] args) {
        addInterfaceDeviceToSetAndLocationMap(addInterfaceDevice("Google Home", "LivingRoom", "Ok Google"));

        addInterfaceDeviceToSetAndLocationMap(addInterfaceDevice("Alexa", "Drawing Room", "Alexa"));

        addSmartHomeDevice("Light", "Drawing Room", "Alexa");

        addSmartHomeDevice("Fan", "Living Room", "Alexa");

        addSmartHomeDevice("Smart Charger", null, "Google Home");

        executeCommand("Alexa", "Light", "Drawing Room", "ON");

        executeCommand("Google Home", "Smart Charger", null, "ON");

        executeCommand("Alexa", "Light", "Drawing Room", "5");

        executeCommand("Alexa", "Light", "Drawing Room", "11");

        executeCommand("Alexa", "Light", "Drawing Room", "OFF");

        executeCommand("Alexa", "Light", "Drawing Room", "11");

        executeCommand("Alexa", "Light", "Drawing Room", "Red Color");

        printConnectedDevices("Alexa", "Drawing Room");

        return;
    }

    private static void printConnectedDevices(String interfaceDevice, String location) {
        InterfaceDevice device = nameInterfaceDeviceMap.get(interfaceDevice);
        System.out.println(device.getConnectedHomeDevices());
    }

    private static void executeCommand(String interfaceDeviceName, String smartHomeDeviceName, String location, String command) {
        CommandProcessor.executeCommand(nameInterfaceDeviceMap, interfaceDeviceName, smartHomeDeviceName, location, command);
    }

    private static void addInterfaceDeviceToSetAndLocationMap(InterfaceDevice interfaceDevice) {
        if(interfaceDevice == null) {
            System.out.println("Cannot add an invalid interface device");
            return;
        }
        Map<String, InterfaceDevice> map = locationInterfaceDeviceMap.get(interfaceDevice.getLocation());

        if(map == null || map.isEmpty()) {
            map = new HashMap<String, InterfaceDevice>();
            locationInterfaceDeviceMap.put(interfaceDevice.getLocation(), map);
        }

        map.put(interfaceDevice.getName(), interfaceDevice);
        nameInterfaceDeviceMap.put(interfaceDevice.getName(), interfaceDevice);
    }

    private static InterfaceDevice addInterfaceDevice(String name, String location, String command) {
        if(name == null || name.isEmpty() || location == null || location.isEmpty() || command == null || command.isEmpty()) {
            return null;
        }
        InterfaceDevice interfaceDevice = new InterfaceDevice(name, location, command);
        System.out.println("Added interface device: " + name + " At location: " + location + " for command: " + command);
        return interfaceDevice;
    }

    private static void addSmartHomeDevice(String name, String location, String interfaceDeviceName) {
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