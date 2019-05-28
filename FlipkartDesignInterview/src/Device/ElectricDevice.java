package Device;

public class ElectricDevice extends AbstractSmartHomeDevice {
    public ElectricDevice(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name + " " + this.isDeviceOn;
    }
}