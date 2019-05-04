package Device.Interfaces;

public interface AdjustableSettings {

    int getSettingValue();

    void setSettingValue(int value) throws Exception;

    void setDefaultValues(int min, int max);
}
