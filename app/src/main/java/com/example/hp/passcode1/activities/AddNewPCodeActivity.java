package com.example.hp.passcode1.activities;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hp.passcode1.R;

import java.util.Calendar;

import static android.widget.Toast.LENGTH_SHORT;

public class AddNewPCodeActivity extends AppCompatActivity {
    CardView cardView;
    Button btnTimeSlot;
    private TextView tvStartTime, tvEndTime;

    private int sHour;
    private int sMinute;
    private int eHour;
    private int eMinute;
    static final int TIME_DIALOG_ID1 = 100011;
    static final int TIME_DIALOG_ID2 = 100012;
    private boolean flag;
    String AM_PM;
    int clickcount = 0;

    EditText edText_Name, edText_Passcode, edText_Re_Pass;
    TextView txt_StartTime, txt_EndTime;
    private int[] days = {R.id.buttonSun, R.id.buttonMon, R.id.buttonTue, R.id.buttonWed, R.id.buttonThu, R.id.buttonFri, R.id.buttonSat};
    CheckBox checkBox_EveryDay;
    Spinner spinner;
    Button btnSave;
    DbHandler db;
    TextView txtDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pcode);

        Intent intent = getIntent();

        onCreate();
        db = new DbHandler(this);

        btnTimeSlot = (Button) findViewById(R.id.buttonTimeSlot);
        btnTimeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickcount = clickcount + 1;
                if (clickcount == 1) {
                    showDialog(TIME_DIALOG_ID1);
                } else if (clickcount == 2)
                    showDialog(TIME_DIALOG_ID2);
            }
        });


        checkBox_EveryDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    buttonDisable();
                    txtDay.setText(new StringBuffer().append("SUN,MON,TUE,WED,THE,FRI,SAT"));
                } else {
                    buttonEnabled();
                }


            }
        });

        final Calendar cal = Calendar.getInstance();
        sHour = cal.get(Calendar.HOUR_OF_DAY);
        sMinute = cal.get(Calendar.MINUTE);
        spinner_changed();
        dataAdd();
    }

    private void buttonDisable() {
        for (int id : days) {

            findViewById(id).setEnabled(false);

        }
    }

    private void buttonEnabled() {
        for (int id : days) {

            findViewById(id).setEnabled(true);

        }
    }

    private void onCreate() {
        btnSave = (Button) findViewById(R.id.buttonSave);
        edText_Name = (EditText) findViewById(R.id.editText_NAME);
        edText_Passcode = (EditText) findViewById(R.id.editText_Pass);
        edText_Re_Pass = (EditText) findViewById(R.id.editTextRePass);
        txt_StartTime = (TextView) findViewById(R.id.txtViewRStartTime);
        txt_EndTime = (TextView) findViewById(R.id.txtViewREndTime);
        txtDay = (TextView) findViewById(R.id.textDays);
        spinner = (Spinner) findViewById(R.id.spinner);
        checkBox_EveryDay = (CheckBox) findViewById(R.id.checkBoxEveryDay);
        cardView = (CardView) findViewById(R.id.cardView2);
        tvStartTime = (TextView) findViewById(R.id.txtViewRStartTime);
        tvEndTime = (TextView) findViewById(R.id.txtViewREndTime);

    }

    private void spinner_changed() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getSelectedItem().equals("Permanent")) {
                    tvStartTime.setText("");
                    tvEndTime.setText("");
                    txtDay.setText("");
                    cardView.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void dataAdd() {
        setDaysOnClickListener();
        //  checkPass();


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validation();
                    PassCode objPassCode = new PassCode();
                    objPassCode.setpName(edText_Name.getText().toString());
                    objPassCode.setpType(spinner.getSelectedItem().toString());
                    objPassCode.setStartTime(txt_StartTime.getText().toString());
                    objPassCode.setEndTime(txt_EndTime.getText().toString());
                    objPassCode.setpCode(edText_Passcode.getText().toString());
                    objPassCode.setDays(txtDay.getText().toString());
                    boolean result = db.insert_passcode(objPassCode);

                    Log.i("result :", String.valueOf(result));

                    if (result == true) {
                        Toast.makeText(AddNewPCodeActivity.this, "data is inserted", LENGTH_SHORT).show();
                        edText_Name.setText("");
                        checkBox_EveryDay.setChecked(false);
                        edText_Passcode.setText("");
                        edText_Re_Pass.setText("");
                        Intent intent1 = new Intent(AddNewPCodeActivity.this, MainActivity.class);
                        startActivity(intent1);
                    } else {
                        Toast.makeText(AddNewPCodeActivity.this, "data is not inserted", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }


    private void setDaysOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;

                if (flag == false) {
                    txtDay.setText(button.getText());
                    Toast.makeText(AddNewPCodeActivity.this, "" + txtDay.getText().toString(), Toast.LENGTH_SHORT).show();
                    flag = true;
                } else if (flag == true) {
                    txtDay.append(new StringBuffer().append(",").append(button.getText()));
                    Toast.makeText(AddNewPCodeActivity.this, "" + txtDay.getText().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        };

        for (int id : days) {

            findViewById(id).setOnClickListener(listener);

        }


    }


    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            sHour = hourOfDay;
            sMinute = minute;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            updateStartTime();
        }
    };

    private TimePickerDialog.OnTimeSetListener mkTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            eHour = hourOfDay;
            eMinute = minute;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            updateEndTime();
        }
    };


    private void updateStartTime() {

        tvStartTime.setText(
                new StringBuilder().append(pad(sHour)).append(":").append(pad(sMinute)).append(AM_PM));
        Toast.makeText(getApplicationContext(), "Plz Click Again to Set End Time ", LENGTH_SHORT).show();

    }

    private void updateEndTime() {

        tvEndTime.setText(
                new StringBuilder().append(pad(eHour)).append(":").append(pad(eMinute)).append(AM_PM));
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case TIME_DIALOG_ID1:
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, 0, mTimeSetListener, sHour, sMinute, false);
                return timePickerDialog;
            case TIME_DIALOG_ID2:
                TimePickerDialog timePickerDialog2 = new TimePickerDialog(this, 0, mkTimeSetListener, eHour, eMinute, false);
                return timePickerDialog2;
        }
        return null;
    }

private void validation(){
    if (TextUtils.isEmpty(edText_Name.getText().toString())) {
        edText_Name.requestFocus();
        edText_Re_Pass.setError("PLZ Enter Name");
        return;
    }

    if (TextUtils.isEmpty(edText_Passcode.getText().toString())) {
        edText_Re_Pass.setError("This Field is required");
        edText_Passcode.requestFocus();

        return;
    }
    if (TextUtils.isEmpty(edText_Re_Pass.getText().toString())) {
        edText_Re_Pass.setError("This Field is required");
        edText_Passcode.requestFocus();
        return;
    }
    if (!edText_Passcode.getText().toString().equals(edText_Re_Pass.getText().toString())) {
        edText_Re_Pass.setError("This Field is required");
        edText_Re_Pass.requestFocus();
        return;
    }

}
}





