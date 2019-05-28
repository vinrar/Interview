package Device;

import Device.Interfaces.AdjustableSettings;

public class SmartFanDevice extends LocationEnabledDevice implements AdjustableSettings {

    SettingsBean settings;

    public SmartFanDevice(String name, String location) {
        settings = new SettingsBean();
        setDefaultValues(1, 5);
        if(location == null || location.isEmpty()) {
            throw new RuntimeException("Location cannot be null for setting up a smart fan.");
        }

        this.name = name;
        this.location = location;
    }

    @Override
    public int getSettingValue() {
        return settings.getSettingValue();
    }

    @Override
    public void setSettingValue(int value) throws Exception {
        settings.setSettingValue(value);
    }

    @Override
    public void setDefaultValues(int min, int max) {
        settings.setDefaultValues(min, max);
    }
}