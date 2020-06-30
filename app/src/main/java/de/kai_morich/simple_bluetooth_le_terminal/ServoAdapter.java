package de.kai_morich.simple_bluetooth_le_terminal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;


import java.util.ArrayList;
import java.util.List;

public class ServoAdapter extends RecyclerView.Adapter<ServoAdapter.ServoHolder> {

    private List<Servo> dataSet;
    private static ServoListener tf;

    public ServoAdapter(List<Servo> data, ServoListener terminalFragment) {
        this.dataSet = data;
        this.tf = terminalFragment;
    }



    @NonNull
    @Override
    public ServoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.servo_item, parent, false);
        ServoHolder sh = new ServoHolder(v);
        return sh;
    }

    @Override
    public void onBindViewHolder(@NonNull ServoHolder servoHolder, int position) {
        Servo servo = dataSet.get(position);
        servoHolder.assignServo(servo);
        servoHolder.servo_name.setText(servo.getName());
        servoHolder.servo_min.setText(String.valueOf(servo.getMin()));
        servoHolder.servo_mid.setText(String.valueOf(servo.getMid()));
        servoHolder.servo_max.setText(String.valueOf(servo.getMax()));

        servoHolder.servo_slider.setEnabled(servo.isEnabled());
        servoHolder.servo_slider.setMinValue(servo.getMin());
        servoHolder.servo_slider.setMaxValue(servo.getMax());
        servoHolder.servo_slider.setMinStartValue(servo.getValue()).apply();
        if(servo.isEnabled()) servoHolder.servo_value.setText(String.valueOf(servo.getValue()));
        else servoHolder.servo_value.setText("Disable");
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ServoHolder extends RecyclerView.ViewHolder {

        private TextView servo_name;
        private TextView servo_value;
        private TextView servo_min;
        private TextView servo_mid;
        private TextView servo_max;
        private CrystalSeekbar servo_slider;
        private ImageButton config_btn;
        private Servo sv;

        public ServoHolder(@NonNull View itemView) {
            super(itemView);

            servo_name = itemView.findViewById(R.id.servo_name);
            servo_value = itemView.findViewById(R.id.servo_value);
            servo_min = itemView.findViewById(R.id.servo_min);
            servo_mid = itemView.findViewById(R.id.servo_mid);
            servo_max = itemView.findViewById(R.id.servo_max);
            servo_slider = itemView.findViewById(R.id.servo_slider);
            config_btn = itemView.findViewById(R.id.config_btn);

            // set listener
            servo_slider.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
                @Override
                public void valueChanged(Number value) {
                    servo_value.setText(String.valueOf(value));
                }
            });

            // set final value listener
            servo_slider.setOnSeekbarFinalValueListener(new OnSeekbarFinalValueListener() {
                @Override
                public void finalValue(Number value) {
                    // Callback for send value
                    if(sv != null) sv.setValue(value.intValue());
                    if(sv != null) tf.sendServoValue(sv.getPosition(), value.intValue());
                }
            });
        }

        public void assignServo(Servo s) {
            this.sv = s;
            config_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =  new Intent(view.getContext(), ServoConfigActivity.class);
                    intent.putExtra("id", s.getPosition());
                    intent.putExtra("enable", s.isEnabled());
                    intent.putExtra("name", s.getName());
                    intent.putExtra("min", s.getMin());
                    intent.putExtra("mid", s.getMid());
                    intent.putExtra("max", s.getMax());
                    if (view.getContext() instanceof AppCompatActivity) {
                        ((AppCompatActivity) view.getContext()).startActivityForResult(intent, 0);
                    }
                }
            });
        }
    }
}
