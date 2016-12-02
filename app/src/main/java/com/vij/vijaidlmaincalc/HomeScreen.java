package com.vij.vijaidlmaincalc;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vij.vijaidlservice.ICalculator;


public class HomeScreen extends Activity {

    private EditText mNumber1, mNumber2;
    private Button mCalculate;
    private TextView mResult;

    private int mN1, mN2;

    protected ICalculator mCalculator;
    private ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mNumber1 = (EditText) findViewById(R.id.editText);
        mNumber2 = (EditText) findViewById(R.id.editText2);

        mResult = (TextView) findViewById(R.id.textView);

        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCalculator != null) {

                    mN1 = Integer.parseInt(mNumber1.getText().toString());
                    mN2 = Integer.parseInt(mNumber2.getText().toString());

                    try {
                        mResult.setText("Add - > " + mCalculator.add(mN1, mN2) + '\n' +
                                "Sub - > " + mCalculator.sub(mN1, mN2) + '\n' +
                                "Mul - > " + mCalculator.mul(mN1, mN2) + '\n');

                        Toast.makeText(HomeScreen.this, mCalculator.LoginDetails(), Toast.LENGTH_SHORT).show();

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /**
         * Initialize The AIDL Service Connection
         */

        InitConnection();
    }

    void InitConnection() {

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mCalculator = ICalculator.Stub.asInterface((IBinder) iBinder);
                Toast.makeText(HomeScreen.this, "Service Connected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mCalculator = null;
                Toast.makeText(HomeScreen.this, "Service Disconnected", Toast.LENGTH_SHORT).show();
            }
        };

        if (mCalculator == null) {
            Intent it = new Intent();
            it.setAction("service.Cal");
            // binding to remote service
            bindService(it, mServiceConnection, Service.BIND_AUTO_CREATE);
        }

//        if (mCalculator == null) {
//            Intent mIntent = new Intent(this.getPackageName());
//            mIntent.setAction("service.Cal");
//            bindService(mIntent, mServiceConnection, Service.BIND_AUTO_CREATE);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(mServiceConnection);
    }
}