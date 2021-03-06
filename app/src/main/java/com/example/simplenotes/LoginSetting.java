package com.example.simplenotes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.simplenotes.NoteListAdapter.checkSaveUpdate;

public class LoginSetting extends AppCompatActivity {

    //public SaveSP saveSP = new SaveSP();
    private EditText newPsw;
    private Button buttonSetting;
    private String userPin;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_setting);
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setTitle(R.string.newPassword);

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
                    App.getKeyStore().savePin(userPin);
                    Intent intent = new Intent(LoginSetting.this, PinEntryView.class);
                    startActivity(intent);
                    finish();

                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginSetting.this, PinEntryView.class);
        startActivity(intent);
        finish();
    }
}