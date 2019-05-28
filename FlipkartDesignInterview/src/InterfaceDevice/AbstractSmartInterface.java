package InterfaceDevice;

import Device.Interfaces.SmartInterface;

public abstract class AbstractSmartInterface implements SmartInterface {
    protected String location;
    protected String name;
    protected String command;

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }
}