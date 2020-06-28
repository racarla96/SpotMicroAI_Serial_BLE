package de.kai_morich.simple_bluetooth_le_terminal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class ServoConfigActivity extends AppCompatActivity {

    private int position;

    private Switch enable;
    private EditText name;
    private EditText min;
    private EditText mid;
    private EditText max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        enable = findViewById(R.id.enable);
        name = findViewById(R.id.servo_name);
        min = findViewById(R.id.min);
        mid = findViewById(R.id.mid);
        max = findViewById(R.id.max);

        Intent intent = getIntent();
        enable.setChecked(intent.getBooleanExtra("enable", Servo.def_enable));
        name.setText(intent.getStringExtra("name"));
        min.setText(String.valueOf(intent.getIntExtra("min", Servo.def_min_value)));
        mid.setText(String.valueOf(intent.getIntExtra("mid", Servo.def_mid_value)));
        max.setText(String.valueOf(intent.getIntExtra("max", Servo.def_max_value)));
        position = intent.getIntExtra("id", Servo.def_position);
    }

    @Override
    public void onBackPressed() {

        boolean _enable = Servo.def_enable;
        String _name = "";
        int _min = Servo.def_min_value;
        int _mid = Servo.def_mid_value;
        int _max = Servo.def_max_value;

        try {
            _enable = enable.isChecked();
            _name = String.valueOf(name.getText());
            _min = Integer.parseInt(String.valueOf(min.getText()));
            _mid = Integer.parseInt(String.valueOf(mid.getText()));
            _max = Integer.parseInt(String.valueOf(max.getText()));
        } catch (Exception e){}

        Intent intent = new Intent();
        intent.putExtra("id", position);
        intent.putExtra("enable", _enable);
        intent.putExtra("name", _name);
        intent.putExtra("min", _min);
        intent.putExtra("mid", _mid);
        intent.putExtra("max", _max);
        setResult(0, intent); // You can also send result without any data using setResult(int resultCode)
        finish();
    }

}
