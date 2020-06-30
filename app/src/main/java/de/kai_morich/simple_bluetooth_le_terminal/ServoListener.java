package de.kai_morich.simple_bluetooth_le_terminal;

public interface ServoListener {
    public void sendServoValue(int id, int value);
    public void sendServoConfig(int id, boolean enable, int min, int mid, int max);
}
