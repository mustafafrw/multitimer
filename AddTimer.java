package com.example.mustafa.multitimer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddTimer extends AppCompatActivity {

Typeface custom_font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timer);

        custom_font = Typeface.createFromAsset(getAssets(),"fonts/ubuntuc.ttf");
        ConstraintLayout cos = findViewById(R.id.ca);

        for(int i=0;i<cos.getChildCount();i++) {
            View vq = cos.getChildAt(i);

            if (vq instanceof TextView) {
                TextView tx = ((TextView) vq);
                tx.setTypeface(custom_font);
            }
            if (vq instanceof EditText) {
                EditText tx = ((EditText) vq);
                tx.setTypeface(custom_font);
            }
            if (vq instanceof Button) {
                Button tx = ((Button) vq);
                tx.setTypeface(custom_font);
                tx.setBackgroundColor(Color.argb(80, 244, 167, 66));
            }

        }
    }
    public void ekle(View view)
    {
        EditText h = findViewById(R.id.editText4);
        EditText m = findViewById(R.id.editText3);
        EditText s = findViewById(R.id.editText2);

        EditText tName = findViewById(R.id.editText);

        if(h.getText().length()==0)
            h.setText("0");
        if(m.getText().length()==0)
            m.setText("0");
        if(s.getText().length()==0)
            s.setText("0");
        int hour = Integer.parseInt(h.getText().toString());
        int min = Integer.parseInt(m.getText().toString());
        int sec = Integer.parseInt(s.getText().toString());

        int totalsec = ((hour*3600)+(min*60)+sec)*1000;

        tobj a = new tobj(tName.getText().toString(),totalsec,0);
        MainActivity.sonEklenen=a;
        Intent aq = new Intent();
        setResult(Activity.RESULT_OK,aq);
        finish();
       // startActivity(aq);
    }

}
