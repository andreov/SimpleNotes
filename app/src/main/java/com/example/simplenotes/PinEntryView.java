package com.example.simplenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class PinEntryView extends AppCompatActivity {

    private String userEntered;
    //public String userPin = "";
    public SaveSP saveSP;
    String psw;


    private final int PIN_LENGTH = 4;
    private boolean keyPadLockedFlag = false;
    private Context appContext;

    private TextView statusView;

    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private ImageButton buttonDelete;
    private EditText passwordInput;
    //ImageView backSpace;
    private View.OnClickListener pinButtonHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pswSave=saveSP.init(this);
        psw=pswSave.getString("SAVE","");

        if(psw.equals("")){
            Intent intent = new Intent(PinEntryView.this, LoginSetting.class);
            startActivity(intent);
            finish();
        }else {
            appContext = this;
            userEntered = "";
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            setContentView(R.layout.activity_pin_entry_view);
            initView();
            setButtonDelete();
            clickDigButton();
            initButton();
        }

    }

    public void initView(){
        statusView = (TextView) findViewById(R.id.haha_text );
        passwordInput = findViewById(R.id.dotText);
        passwordInput.setFocusable(false);
        passwordInput.setClickable(false);
        buttonDelete = findViewById(R.id.lbtnDelete);
        //buttonExit = findViewById(R.id.buttonExit);

    }

    public void setButtonDelete(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                if (keyPadLockedFlag == true) {
//                    return;
//                }
                if (userEntered.length() > 0) {
                    //userEntered = userEntered.substring(0, userEntered.length() - 1);
                    userEntered="";
                    passwordInput.setText("");
                    statusView.setTextColor(Color.WHITE);
                    statusView.setText("Enter Your Password");
                    keyPadLockedFlag = false;

                }
            }
        });
    }

    public void initButton(){
        button0 = (Button) findViewById(R.id.lbtn0);
        button0.setOnClickListener(pinButtonHandler);
        button1 = (Button) findViewById(R.id.lbtn1);
        button1.setOnClickListener(pinButtonHandler);
        button2 = (Button) findViewById(R.id.lbtn2);
        button2.setOnClickListener(pinButtonHandler);
        button3 = (Button) findViewById(R.id.lbtn3);
        button3.setOnClickListener(pinButtonHandler);
        button4 = (Button) findViewById(R.id.lbtn4);
        button4.setOnClickListener(pinButtonHandler);
        button5 = (Button) findViewById(R.id.lbtn5);
        button5.setOnClickListener(pinButtonHandler);
        button6 = (Button) findViewById(R.id.lbtn6);
        button6.setOnClickListener(pinButtonHandler);
        button7 = (Button) findViewById(R.id.lbtn7);
        button7.setOnClickListener(pinButtonHandler);
        button8 = (Button) findViewById(R.id.lbtn8);
        button8.setOnClickListener(pinButtonHandler);
        button9 = (Button) findViewById(R.id.lbtn9);
        button9.setOnClickListener(pinButtonHandler);
    }

    public void clickDigButton(){
        pinButtonHandler = new View.OnClickListener() {
            public void onClick(View v) {


                if (keyPadLockedFlag == true) {
                    return;
                }
                Button pressedButton = (Button) v;

                if (userEntered.length() < PIN_LENGTH) {
                    userEntered = userEntered + pressedButton.getText();
                    Log.v("PinView", "User entered=" + userEntered);

                    //Update pin boxes
                    passwordInput.setText(passwordInput.getText().toString() + "*");
                    passwordInput.setSelection(passwordInput.getText().toString().length());

                    if (userEntered.length() == PIN_LENGTH) {
                        //Check if entered PIN is correct
                        if (userEntered.equals(psw)) {
                            statusView.setTextColor(Color.GREEN);
                            statusView.setText("Correct");
                            Log.v("PinView", "Correct PIN");
                            Intent intent = new Intent(PinEntryView.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            statusView.setTextColor(Color.RED);
                            statusView.setText("Incorrect PIN. Press 'Del'");
                            keyPadLockedFlag = true;
                            Log.v("PinView", "Wrong PIN");

                            //new LockKeyPadOperation().execute("");
                        }
                    }
                } //else {
//                    //Roll over
//                    passwordInput.setText("");
//                    userEntered = "";
//                    statusView.setText("");
//                    userEntered = userEntered + pressedButton.getText();
//                    Log.v("PinView", "User entered=" + userEntered);
//                    //Update pin boxes
//                    passwordInput.setText("8");
//
//                }
            }
        };


    }
}