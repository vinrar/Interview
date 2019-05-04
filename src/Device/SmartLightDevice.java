package Device;

import Device.Interfaces.Color;
import Device.Interfaces.LightColourSettings;
import Device.Interfaces.AdjustableSettings;

public class SmartLightDevice  extends LocationEnabledDevice implements LightColourSettings, AdjustableSettings {

    SettingsBean settings;
    Color color = Color.WHITE;

    public SmartLightDevice(String name, String location) {
        settings = new SettingsBean();

        setDefaultValues(1, 10);


        if(location == null || location.isEmpty()) {
            throw new RuntimeException("Location cannot be null for setting up a smart light.");
        }

        this.name = name;
        this.location = location;
    }

    @Override
    public int getSettingValue() {
        return this.settings.getSettingValue();
    }

    @Override
    public void setSettingValue(int value) throws Exception {
        this.settings.setSettingValue(value);
    }

    @Override
    public void setDefaultValues(int min, int max) {
        settings.setDefaultValues(min, max);
    }

    private static boolean contains(String requestedColor) {

        for (Color color : Color.values()) {
            if (color.name().equalsIgnoreCase(requestedColor)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getCurrentColor() {
        return this.color.getColor();
    }

    @Override
    public void setColor(String color) {
        if(contains(color)) {
            this.color = Color.valueOf(color.toUpperCase());
        } else
            System.out.println("Requested color is not available for: " + this.name);
    }
}