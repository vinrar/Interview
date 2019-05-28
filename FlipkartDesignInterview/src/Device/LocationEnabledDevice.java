package Device;

public class LocationEnabledDevice extends AbstractSmartHomeDevice {
    protected String location;

    @Override
    public String toString(){
        return this.name + " " + this.location + " " + this.isDeviceOn;
    }
}
