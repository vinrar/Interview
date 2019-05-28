package Device.Driver;

import Device.AbstractSmartHomeDevice;
import Device.Interfaces.AdjustableSettings;
import Device.Interfaces.LightColourSettings;
import InterfaceDevice.InterfaceDevice;

import java.util.Map;

public class CommandProcessor {
    public static final String COMMAND_ON = "ON";
    public static final String COMMAND_OFF = "OFF";

    public static void executeCommand(Map<String, InterfaceDevice> nameInterfaceDeviceMap, String interfaceDeviceName, String smartHomeDeviceName, String location, String command) {
        InterfaceDevice interfaceDevice = nameInterfaceDeviceMap.get(interfaceDeviceName);
        AbstractSmartHomeDevice smartHomeDevice = (AbstractSmartHomeDevice) interfaceDevice.getSmartHomeDevice(smartHomeDeviceName, location);

        if(smartHomeDevice == null)
            return;

        processCommand(command, smartHomeDevice);

        try {
            Thread.sleep(1000);
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void processCommand(String command, AbstractSmartHomeDevice smartHomeDevice) {
        if(command == null || command.isEmpty()) {
            System.out.println("Command cannot be empty or null");
            return;
        }

        switch (command) {
            case COMMAND_ON:
                smartHomeDevice.turnOn();
                break;
            case COMMAND_OFF:
                smartHomeDevice.turnOff();
                break;
            default:
                int setting = tryParseInt(command);
                if(setting == -1) {
                    if(command.contains("Color")) {
                        System.out.println("Requested for color change: " + command);
                        LightColourSettings colourDevice = (LightColourSettings) smartHomeDevice;
                        if(colourDevice == null) {
                            System.out.println("Device does not have Color settings");
                        } else {
                            command = command.substring(0, command.length() - 6);
                            colourDevice.setColor(command);
                        }
                    } else {
                        System.out.println("Invalid setting requested.");
                    }
                } else {
                    AdjustableSettings device = (AdjustableSettings) smartHomeDevice;

                    if(!smartHomeDevice.isDeviceOn()) {
                        System.out.println("This device is not turned on");
                        return;
                    }

                    if(device == null) {
                        System.out.println("This device does not have setting to change.");
                        return;
                    }

                    try {
                        device.setSettingValue(setting);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
        }
    }

    private static int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return  -1;
        }
    }
}
