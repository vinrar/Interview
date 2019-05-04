package Device;

public class SettingsBean {
    private static int minValue = 1;
    private static int maxValue = 1;
    private static int settingValue = 0;

    protected void setDefaultValues(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int getSettingValue() {
        return settingValue;
    }

    protected void setSettingValue(int settingValue) throws Exception {
        if(settingValue > maxValue) {
            throw new Exception("Setting cannot be greater than: " + maxValue);
        } else if (settingValue < minValue) {
            throw new Exception("Setting cannot be less than: " + minValue);
        }

        this.settingValue = settingValue;
    }

    public int getMinValue(){
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

}
