package com.example.administrator.sms_app;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String sendTo="";
    EditText editText;
    EditText editText2;
    String msg="";
    SmsManager smsManager=SmsManager.getDefault();

    String SMS_SENT_ACTION="SMS_SENT_ACTION";
    String SMS_DELIVERED_ACTION="SMS_DELIVERED_ACTION";
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        btn=(Button)findViewById(R.id.btnSendSMS);
        editText=(EditText) findViewById(R.id.editText);
        editText2=(EditText) findViewById(R.id.editText2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });


    }
    public void sendMsg()
    {
        sendTo=editText.getText().toString();
        msg=editText2.getText().toString();
        Intent intent_sent=new Intent(SMS_SENT_ACTION);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),0,intent_sent,0);
        Intent intent_delivery=new Intent(SMS_DELIVERED_ACTION);
        PendingIntent pendingIntent1=PendingIntent.getBroadcast(getApplicationContext(),0,intent_delivery,0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                /*switch( getResultCode()){
                    case Activity.RESULT_OK:
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        break;

                }*/
            }
        }, new IntentFilter(SMS_SENT_ACTION) );
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        }, new IntentFilter(SMS_DELIVERED_ACTION));
        smsManager.sendTextMessage(sendTo,null,msg,null,null);
        Toast.makeText(MainActivity.this,"SMS Sent",Toast.LENGTH_SHORT).show();

    }
}
