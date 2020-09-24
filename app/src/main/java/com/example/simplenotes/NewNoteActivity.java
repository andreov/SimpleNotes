package com.example.simplenotes;




import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.Calendar;
import static com.example.simplenotes.MainActivity.mNoteViewModel;
import static com.example.simplenotes.NoteListAdapter.EXTRA_UPDATE_CHECKBOX;
import static com.example.simplenotes.NoteListAdapter.EXTRA_UPDATE_DEDLINE;
import static com.example.simplenotes.NoteListAdapter.EXTRA_UPDATE_DESC;
import static com.example.simplenotes.NoteListAdapter.EXTRA_UPDATE_ID;
import static com.example.simplenotes.NoteListAdapter.EXTRA_UPDATE_TITLE;
import static com.example.simplenotes.NoteListAdapter.checkSaveUpdate;



public class NewNoteActivity extends AppCompatActivity {

    public static int idUpd;
    private CheckBox mCheckDeadLine;
    private EditText mEditTitleView;
    private EditText mEditDescView;
    private EditText mTextDeadline;
    private ImageButton mButtonDeadline;
    private Calendar mDateDeadline;
    private long milliseconds;
    private Toolbar myToolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        backButtonToolbar();
        initViews();
        setCheckBox();
        noteUpdate(checkSaveUpdate);
        checkDeadLine();


    }
    public void backButtonToolbar(){
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        checkSaveUpdate =false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuSave:
                clickSave();
                Toast.makeText(NewNoteActivity.this, "Save Note", Toast.LENGTH_LONG).show();

                return true;
            default:
                return true;
        }
    }


    public void initViews(){
        mEditTitleView = findViewById(R.id.edit_title);
        mEditDescView = findViewById(R.id.edit_desc);
        mCheckDeadLine=findViewById((R.id.checkBox));
        mButtonDeadline =findViewById(R.id.calendarDeadLine);
        mTextDeadline=findViewById(R.id.TextDeadline);
        mTextDeadline.setFocusable(false);
        //mTextDeadline.setLongClickable(false);
        //mTextDeadline.setCursorVisible(false);
        ///mTextDeadline.setClickable(false);
        mDateDeadline=Calendar.getInstance();
    }

    private void clickSave(){
        //Intent intent = new Intent(NewNoteActivity.this, MainActivity.class);
        if(checkSaveUpdate){ //(TextUtils.isEmpty(mEditWordView.getText())) {
            String title = mEditTitleView.getText().toString();
            String desc = mEditDescView.getText().toString();
            long dedline= milliseconds;
            boolean checkDedline=mCheckDeadLine.isChecked();
            Note note =new Note(title,desc,dedline,checkDedline);
            note.setId(idUpd);
            mNoteViewModel.update(note);
            checkSaveUpdate =false;
            //startActivity(intent);
            finish();
        } else {
            String title = mEditTitleView.getText().toString();
            String desc = mEditDescView.getText().toString();
            long dedline= milliseconds;
            boolean checkDedline=mCheckDeadLine.isChecked();
            Note note =new Note(title,desc,dedline,checkDedline);
            mNoteViewModel.insert(note);
            //startActivity(intent);
            finish();
        }
    }

    private void checkDeadLine(){
        mCheckDeadLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setCheckBox();
            }
        });
    }


    public void setCheckBox(){
        if (mCheckDeadLine.isChecked()) {
            mButtonDeadline.setClickable(true);
            mTextDeadline.setClickable(true);
            //mTextDeadline.setLongClickable(true);
            //mTextDeadline.setCursorVisible(true);
            mDateDeadline=Calendar.getInstance();
            setInitialDateTime();
            Toast.makeText(getApplicationContext(), "Включено", Toast.LENGTH_SHORT).show();
        } else {
            mButtonDeadline.setClickable(false);
            mTextDeadline.setLongClickable(false);
            mTextDeadline.setCursorVisible(false);
            mTextDeadline.setClickable(false);
            //mTextDeadline.setFocusable(false);
            mTextDeadline.setText(null);
            milliseconds=0;
            //Toast.makeText(getApplicationContext(), "Выключено", Toast.LENGTH_SHORT).show();
        }
    }

    public void noteUpdate(boolean checkUpdate){
        if(checkUpdate){
            String titleUpd=getIntent().getSerializableExtra(EXTRA_UPDATE_TITLE).toString();
            String descUpd=getIntent().getSerializableExtra(EXTRA_UPDATE_DESC).toString();
            idUpd=(int)getIntent().getSerializableExtra(EXTRA_UPDATE_ID);
            long dedline=(long)getIntent().getSerializableExtra(EXTRA_UPDATE_DEDLINE);
            boolean checkdedline=(boolean)getIntent().getSerializableExtra(EXTRA_UPDATE_CHECKBOX);
            mCheckDeadLine.setChecked(checkdedline);
            mEditTitleView.setText(titleUpd);
            mEditDescView.setText(descUpd);

            if(checkdedline){
                //mDateDeadline=Calendar.getInstance();
                mDateDeadline.setTimeInMillis(dedline);
                mButtonDeadline.setClickable(true);
                mTextDeadline.setClickable(true);
                mTextDeadline.setText(DateUtils.formatDateTime(this, dedline,
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
                milliseconds=dedline;
            }
            else {
                milliseconds=0;
                return;
            }

        }else return;
    }

    // отображаем диалоговое окно для выбора времени
    public void setDate(View v) {
        new DatePickerDialog(NewNoteActivity.this, d,
                mDateDeadline.get(Calendar.YEAR),
                mDateDeadline.get(Calendar.MONTH),
                mDateDeadline.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    final DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mDateDeadline.set(Calendar.YEAR, year);
            mDateDeadline.set(Calendar.MONTH, monthOfYear);
            mDateDeadline.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    // установка начальных даты и времени
    private void setInitialDateTime() {
        mTextDeadline.setText(DateUtils.formatDateTime(this, mDateDeadline.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        milliseconds = mDateDeadline.getTimeInMillis();
    }

//    private long textTolong(){
//        SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy",Locale.getDefault());
//        Date date=null;
//        try {
//            date = fmt.parse(mTextDeadline.getText().toString());
//        } catch (ParseException e) {
//            e.printStackTrace();
//            if (!mCheckDeadLine.isChecked()) return 0;
//            else {
//                Toast.makeText(getApplicationContext(), "Неверный формат даты", Toast.LENGTH_SHORT).show();
//                //finish();
//                //date=null;
//            }
//        }
//        return date.getTime();
//    }


//    String input = "Sat Feb 17 2012";
//    Date date = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH).parse(input);
//    long milliseconds = date.getTime();
//    long millisecondsFromNow = milliseconds - (new Date()).getTime();
//    Toast.makeText(this, "Milliseconds to future date="+millisecondsFromNow, Toast.LENGTH_SHORT).show();
}

