package com.example.brian.quintaentrega;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Messenger messengerService=null;
    boolean nBound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button s3=(Button) findViewById(R.id.buttontest);

        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nBound){
                    Message msg=Message.obtain(null,1);
                    try{
                        messengerService.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        Intent intent= new Intent();
        intent.setComponent(new ComponentName("com.example.brian.entregableparcial","com.example.brian.entregableparcial.MessengerService"));
        bindService(intent,nConnection,Context.BIND_AUTO_CREATE);
    }


    private ServiceConnection nConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messengerService= new Messenger(service);
            nBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            nBound=false;
            messengerService=null;
        }
    };
}