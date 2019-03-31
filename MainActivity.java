package com.example.mustafa.multitimer;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static tobj sonEklenen;

    ArrayList<tobj> timerlar;
    TextView t1;
    TextView t2;
    LinearLayoutCompat cp;
    TinyDB db;
    LinearLayout sutun1;
    LinearLayout sutun2;
    NotificationManagerCompat NM;
    Typeface custom_font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



         custom_font = Typeface.createFromAsset(getAssets(),"fonts/ubuntuc.ttf");




        NM=NotificationManagerCompat.from(this);



        /*sharedPreferences = getApplicationContext().getSharedPreferences("db", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        Set<String> stringSet = new HashSet<String>();
        stringSet.add("");*/

        sutun1 = findViewById(R.id.sutun1);
        sutun2 = findViewById(R.id.sutun2);
        timerlar = new ArrayList<>();

        db = new TinyDB(getApplicationContext());

        int indx=0;
        ArrayList<String> gelenler = db.getListString("timers");
        for(String s : gelenler)
        {
            if(indx%3==0)
            {
                tobj ek = new tobj(gelenler.get(indx),Integer.valueOf(gelenler.get(indx+1)),Integer.valueOf(gelenler.get(indx+2)));
                yeniTimerEkle(ek);
            }
            indx++;
        }
        //getLocalVariables();


        //ed.putStringSet() // hashsete start,left,tkey girdir eleman sayısı /3 = timer sayısı


       // t2 = findViewById(R.id.textView);
        //t1 = findViewById(R.id.textView2);;
        cp = findViewById(R.id.cos);
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.m, menu);

        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent a = new Intent(this,AddTimer.class);
        startActivityForResult(a,1);



        return super.onOptionsItemSelected(item);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                ArrayList<String> vova = db.getListString("timers");
                vova.add(sonEklenen.tkey);
                vova.add(String.valueOf(sonEklenen.start));
                vova.add(String.valueOf(sonEklenen.left));
                db.putListString("timers",vova);

                yeniTimerEkle(sonEklenen);

                timerlar.add(sonEklenen);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
    public void getLocalVariables()
    {
        ArrayList<tobj> tArray = new ArrayList<>();
        int i=0;
        tobj nObj = new tobj();
        for(String variable : db.getListString("timers"))
        {
            if(i==0)
            {
                nObj = new tobj();
                nObj.tkey = variable;
            }
            if(i==1)
                nObj.start = Integer.parseInt(variable);
            if(i==2){nObj.left = Integer.parseInt(variable);
                i=-1;
                tArray.add(nObj);
                yeniTimerEkle(nObj);
            }

            i++;

            System.out.println("Variable = "+variable);

        }
    }
    public void yeniTimerEkle(tobj newT)
    {
        System.out.println("view = [" + "girdik be" + "]");
        timerlar.add(newT);

        LinearLayout newInner=new LinearLayout(getApplicationContext());
        newInner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1f));
        newInner.setOrientation(LinearLayout.VERTICAL);

        TextView tx = new TextView(getApplicationContext());
        tx.setText(newT.tkey);
        tx.setTypeface(custom_font);
        tx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tx.setId(View.generateViewId());
        tx.setBackgroundColor(Color.argb(18,255,115,28));
        tx.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tx.setTextSize(30);
        tx.setTextColor(Color.WHITE);

        newInner.addView(tx);

        TextView tx2 = new TextView(getApplicationContext());
        if(newT.left<1000)
            tx2.setText(msTo(newT.start));
        else
            tx2.setText(msTo(newT.left));
        tx2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tx2.setId(View.generateViewId());
        tx2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tx2.setBackgroundColor(Color.argb(30,255,115,28));
        tx2.setTextColor(Color.argb(200,234,242,19));
        tx2.setTypeface(custom_font);
        tx2.setTextSize(50);

        newInner.addView(tx2);



        Button bt =new Button(getApplicationContext());
        bt.setText("start");
        bt.setTextSize(25);
        bt.setTypeface(custom_font);
        bt.setTextColor(Color.WHITE);
        bt.setBackgroundColor(Color.argb(80,244,167,66));
        bt.setLayoutParams(tx.getLayoutParams());
        bt.setId(View.generateViewId());
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v;




                //String resId = getResources().getResourceEntryName(v.getId());

                //System.out.println("view = [" + resId + "]");

                if(b.getText().toString().toLowerCase().equals("start"))
                {
                    b.setText("pause");
                    LinearLayout ana = (LinearLayout)b.getParent().getParent().getParent();
                    String tkey="";
                    TextView leftT=new TextView(getApplicationContext());
                    int j=0;
                    for(int i=0;i<ana.getChildCount();i++)
                    {
                        View vq = ana.getChildAt(i);
                        if(vq instanceof TextView && j==0){
                            tkey = ((TextView) vq).getText().toString();j++;}
                        else if(vq instanceof TextView && j==1 && (vq instanceof Button)==false)
                            leftT = (TextView)vq;

                    }
                    if(tkey!=null)
                        calistir(tkey,leftT);
                }
                else if(b.getText().toString().toLowerCase().equals("pause"))
                {
                    b.setText("start");
                    LinearLayout ana = (LinearLayout)b.getParent().getParent().getParent();
                    String tkey="";
                    TextView leftT=new TextView(getApplicationContext());
                    int j=0;
                    for(int i=0;i<ana.getChildCount();i++)
                    {
                        View vq = ana.getChildAt(i);
                        if(vq instanceof TextView && j==0){
                            tkey = ((TextView) vq).getText().toString();j++;}
                        else if(vq instanceof TextView && j==1 && (vq instanceof Button)==false)
                            leftT = (TextView)vq;

                    }
                    for(tobj jq : timerlar)
                    {
                        if(jq.tkey.equals(tkey)){
                            if(jq.time!=null){jq.time.cancel();jq.time =null;}

                        }
                    }
                }
            }
        });
        LinearLayout kapsar=new LinearLayout(getApplicationContext());
        kapsar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1));
        kapsar.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout butLL=new LinearLayout(getApplicationContext());
        butLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1));
        butLL.setOrientation(LinearLayout.HORIZONTAL);
        butLL.addView(bt);
        kapsar.addView(butLL);

        Button btd =new Button(getApplicationContext());
        btd.setText("delete");
        btd.setTextSize(25);
        btd.setTypeface(custom_font);
        btd.setTextColor(Color.WHITE);
        btd.setBackgroundColor(Color.argb(80,244,167,66));
        btd.setLayoutParams(tx.getLayoutParams());
        btd.setId(View.generateViewId());
        btd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button delete = (Button)v;
                if(delete.getText().toString().toLowerCase().equals("delete"))
                {
                    LinearLayout lay = (LinearLayout)delete.getParent().getParent().getParent().getParent();
                    LinearLayout layc = (LinearLayout)delete.getParent().getParent().getParent();

                    String tkey = String.valueOf(((TextView)layc.getChildAt(0)).getText());
                    for(tobj w : timerlar)
                        if(w.tkey.equals(tkey)){
                            timerlar.remove(w);break;}

                    ArrayList<String> vova = db.getListString("timers");
                    int ju =0;
                    for(String ww : vova){
                        if(ww.equals(tkey)){
                            vova.remove(ju);
                            vova.remove(ju);
                            vova.remove(ju);
                            break;}
                        ju++;
                    }
                    db.putListString("timers",vova);
                    lay.removeView(layc);
                }
            }
        });

        LinearLayout butLR=new LinearLayout(getApplicationContext());
        butLR.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1));
        butLR.setOrientation(LinearLayout.HORIZONTAL);
        butLR.addView(btd);
        kapsar.addView(butLR);

        newInner.addView(kapsar);

        if(sutun1.getChildCount()==sutun2.getChildCount())
            sutun1.addView(newInner);
        else if(sutun1.getChildCount()<sutun2.getChildCount())
            sutun1.addView(newInner);
        else
            sutun2.addView(newInner);
        //newL.setOrientation();

        //cp.addView(newL,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }
    public String msTo(long ms)
    {
        if(ms<1000)
            return "0";

        int s=(int)ms/1000;

        int m=0;
        int h=0;
        if(s>60 && s<3600){
            m=s/60;
            String sfx="";
            if(m<=9)
                sfx="0";
            String sfxsec="";
            if(s%60<=9)
                sfxsec="0";
            return sfx+String.valueOf(m)+":"+sfxsec+s%60;
        }
        else if(s>=3600)
        {
            h=s/3600;
            int mq=((s-(h*3600))/60);
            if(mq<9)
                return String.valueOf(h)+":0"+mq+":"+s%60;
            else
                return String.valueOf(h)+":"+mq+":"+s%60;
        }
        else if(s<60 && s>=10)
        {
            return String.valueOf("00:"+s);
        }
        else if(s<10)
            return String.valueOf("00:0"+s);
        else if(s<=0)
            return "00:00";
       return String.valueOf(s);
    }
    public void tiklandi(View view)
    {


       /* if(resId.equals("addTimer")){
            Intent addTi = new Intent(getApplicationContext(),AddTimer.class);
            startActivity(addTi);
        }
        if(resId.equals("button6"))
        {

        }
        if(resId.equals("button")){
            System.out.println("view = girdi]");
            tobj o = new tobj();
            o.tkey="kitap oku";
            o.start=1000*60*2*60;
            timerlar.add(o);
            calistir(o,t1);
        }
        else if(resId.equals("button2")) {
            tobj o = new tobj();
            o.tkey = "ders çalış";
            o.start = 1000 * 60 * 1;
            timerlar.add(o);
            calistir(o, t2);
        }

        else if(b.getText().toString().toLowerCase().equals("pause"))
        {
            for(tobj j : timerlar)
            {
                if(j.tkey.equals("kitap oku")){
                    if(j.time!=null){j.time.cancel();j.time =null;}

                }
            }
            b.setText("start");
        }
        else if(b.getText().toString().toLowerCase().equals("start"))
        {
            for(tobj j : timerlar)
            {
                if(j.tkey.equals("kitap oku")){
                    //if(j.time!=null)
                        calistir(j,t1);

                }
            }
            b.setText("pause");
        }
        //Toast.makeText(getApplicationContext(),resId, Toast.LENGTH_LONG).show();


       /* bv = findViewById(R.id.button);

        Button newBut = new Button(this);

        newBut.setText("New button");
        bv.offsetTopAndBottom(bv.getHeight()+20);
        ConstraintLayout cs = findViewById(R.id.cos);
        cs.addView(newBut);*/

    }
    Handler h=new Handler();
    tobj p;
    public void calistir(final String tkey, final TextView tv)
    {

        if(p!=null)
            if(p.tkey!=tkey)
                for(tobj aq : timerlar)
                    if(aq.tkey==tkey)
                        p=aq;
        if(p==null)
            for(tobj aq : timerlar)
                if(aq.tkey==tkey)
                    p=aq;
         CountDownTimer c;

        if(p.time == null){
            int st =0;
            if(p.left!=0)
                st=(int)p.left;
            else
                st=p.start;
            p.time = new CountDownTimer(st,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tv.setText(msTo(millisUntilFinished));
                    p.left=millisUntilFinished;
                    ArrayList<String> vova = db.getListString("timers");
                    int ju =0;
                    for(String ww : vova) {
                        if (ww.equals(tkey))
                            break;
                        ju++;
                    }
                    vova.set(ju+2,String.valueOf(millisUntilFinished));
                    db.putListString("timers",vova);

                }

                @Override
                public void onFinish() {
                   pushSend(p);
                   p.left=0;
                   tv.setText(msTo(p.start));
                   LinearLayout lay = (LinearLayout)tv.getParent();
                    LinearLayout layin = (LinearLayout)lay.getChildAt(2);
                    LinearLayout layinner = (LinearLayout)layin.getChildAt(0);
                   Button b = (Button)layinner.getChildAt(0);
                   b.setText("start");
                }
            }.start();
        }
        else {

           /* p.time = new CountDownTimer(p.left,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tv.setText(String.valueOf(millisUntilFinished));
                    p.left=millisUntilFinished;
                }

                @Override
                public void onFinish() {

                }
            }.start();*/
        }


    }

    public void pushSend(tobj ax)
    {
        int NOTIFICATION_ID = 234;

        String CHANNEL_ID = "my_channel_01";

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle("Time is up!")
                .setContentText(ax.tkey+" is over.");

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
