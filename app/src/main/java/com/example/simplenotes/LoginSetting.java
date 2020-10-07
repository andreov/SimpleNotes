package com.example.simplenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginSetting extends AppCompatActivity {

    public SaveSP saveSP;
    private EditText newPsw;
    private Button buttonSetting;
    private String userPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_setting);

        newPsw = findViewById(R.id.textPassword);
        buttonSetting=findViewById(R.id.buttonOK);

        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPin = newPsw.getText().toString();
                if(userPin.equals("")){
                    Toast.makeText(LoginSetting.this, "Введите пароль", Toast.LENGTH_LONG).show();
                    return;

                }else {
                    saveSP.put("SAVE",userPin);
                    Intent intent = new Intent(LoginSetting.this, PinEntryView.class);
                    startActivity(intent);
                    finish();

                }

            }
        });

    }
}