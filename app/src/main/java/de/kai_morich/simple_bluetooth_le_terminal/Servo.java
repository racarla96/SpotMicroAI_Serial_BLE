package de.kai_morich.simple_bluetooth_le_terminal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Servo {

    static final String shpref = "userdetails"; // SharedPreferences name
    static final int def_min_value = 102;
    static final int def_mid_value = 307;
    static final int def_max_value = 512;
    static final boolean def_enable = true;
    static final int def_position = -1;

    private boolean enabled = def_enable;
    private final int position;
    private int value;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editorSharedPreferences;

    public Servo(Context context, int position) {
        sharedPreferences = context.getSharedPreferences(shpref, Context.MODE_PRIVATE);
        editorSharedPreferences = sharedPreferences.edit();
        this.position = position;
        this.value = getMid();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnable(boolean state) { enabled = state; }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return sharedPreferences.getString("s" + position + "name", "Servo " + position);
    }

    public void setName(String name) {
        editorSharedPreferences.putString("s" + position + "name", name);
        editorSharedPreferences.commit();
    }

    public int getMin() {
        return sharedPreferences.getInt("s" + position + "min", def_min_value);
    }

    public void setMin(int min) {
        editorSharedPreferences.putInt("s" + position + "min", min);
        editorSharedPreferences.commit();
    }

    public int getMid() {
        return sharedPreferences.getInt("s" + position + "mid", def_mid_value);
    }

    public void setMid(int mid) {
        editorSharedPreferences.putInt("s" + position + "mid", mid);
        editorSharedPreferences.commit();
    }

    public int getMax() {
        return sharedPreferences.getInt("s" + position + "max", def_max_value);
    }

    public void setMax(int max) {
        editorSharedPreferences.putInt("s" + position + "max", max);
        editorSharedPreferences.commit();
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() { return value; }
}
