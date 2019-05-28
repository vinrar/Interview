package Device;

import Device.Interfaces.SmartHomeDeviceInterface;
import Device.Interfaces.SmartInterface;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractSmartHomeDevice implements SmartHomeDeviceInterface {
    protected String name;
    private Set<SmartInterface> connectedInterfaces = new HashSet<>(8);

    boolean isDeviceOn = false;

    @Override
    public boolean turnOn() {
        System.out.println("The device: " + name + " is turned on.");
        if(isDeviceOn == false) {
            this.isDeviceOn = true;
            return true;
        }
        this.isDeviceOn = true;
        return true;
    }

    @Override
    public boolean turnOff() {
        System.out.println("The device: " + name + " is turned off.");
        if(isDeviceOn == true) {
            this.isDeviceOn = false;
            return true;
        }
        this.isDeviceOn = false;
        return true;
    }

    @Override
    public boolean isDeviceOn(){
        return this.isDeviceOn;
    }

    @Override
    public boolean connectTo(SmartInterface smartInterface) {
        boolean alreadyConnected = connectedInterfaces.contains(smartInterface);

        if(!alreadyConnected)
            connectedInterfaces.add(smartInterface);

        return !alreadyConnected;
    }

    @Override
    public boolean disconnectFrom(SmartInterface smartInterface) {
        boolean alreadyConnected = connectedInterfaces.contains(smartInterface);

        if(alreadyConnected)
            connectedInterfaces.remove(smartInterface);

        return alreadyConnected;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o){
        String string = (String) o;
        if(this.name.equalsIgnoreCase(string)){
            return true;
        }

        return false;
    }
}