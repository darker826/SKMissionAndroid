package com.example.administrator.myapplication;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.util.Set;

/**
 * Created by YJ on 2016-08-04.
 */
public class ImageActivity extends AppCompatActivity {
    private BluetoothSerialClient mClient;
    private BluetoothDevice mDevice;
    private byte mDataBuffer[] = new byte[24];
    private int mCount = 0;

    private long nstartA = 0;
    private long nendA = 0;

    private long nstartB = 0;
    private long nendB = 0;

    private long nstartC = 0;
    private long nendC = 0;

    int streamID;

    //vibrate
    private Vibrator vibe;

    //sound
    final SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_ALARM, 0);
    private int soundID;

    //image
    private Drawable red;
    private Drawable green;

    //imageView 정의부분

    private ImageView imageViewR1;
    private ImageView imageViewL1;
    private ImageView imageViewR2;
    private ImageView imageViewL2;
    private ImageView imageViewR3;
    private ImageView imageViewL3;

    @Override
    protected void onDestroy() {
//        mClient.clear();
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        soundID = soundPool.load(this, R.raw.realsound, 1);

        red = getResources().getDrawable(R.drawable.red);
        green = getResources().getDrawable(R.drawable.green);

        imageViewR1 = (ImageView) findViewById(R.id.imageViewR1);
        imageViewL1 = (ImageView) findViewById(R.id.imageViewL1);

        imageViewR2 = (ImageView) findViewById(R.id.imageViewR2);
        imageViewL2 = (ImageView) findViewById(R.id.imageViewL2);

        imageViewR3 = (ImageView) findViewById(R.id.imageViewR3);
        imageViewL3 = (ImageView) findViewById(R.id.imageViewL3);

/*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("IMAGE");
*/
        /*
        mClient = BluetoothSerialClient.getInstance();
        if (mClient == null) {
            Log.d("블루투스", "블루투스를 지원하지 않습니다.");
            finish();
        }

        enableBluetooth();
        if (mDevice != null) {
            connect(mDevice);
        }
        */
        byte[] data = {127, 50, 127, 30, 12, 127};
        setGraph(data);
    }

/*
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    //데이터가 6개 쌓이면 불리는 함수
    private void setGraph(byte[] data) {
        //여기서 데이터 처리를 하시면 됩니다.

        int count = 0;
        int valueA = 0;
        int valueB = 0;
        int valueC = 0;

        valueA = data[0] - data[3];
        valueB = data[1] - data[4];
        valueC = data[2] - data[5];

        if (valueA > 30 || valueA < -30) {

            count++;

            if (nstartA == 0) {
                nstartA = System.currentTimeMillis();
            } else {
                nendA = System.currentTimeMillis();
                if (((nendA - nstartA) / 1000) > 20 && count >= 2) {

                    streamID = soundPool.play(soundID, 1, 1, 0, 0, 0.5f);

                    //소리
                } else if (((nendA - nstartA) / 1000) > 10 && count >= 2) {
                    //진동
                    long[] pattern = {0, 500, 200, 400, 100};
                    vibe.vibrate(pattern, -1);

                } else {
                    soundPool.stop(streamID);
                }
            }
            imageViewR1.setImageDrawable(red);
            imageViewL1.setImageDrawable(red);

            //색변환

        } else {
            nstartA = 0;
            nendA = 0;
            imageViewR1.setImageDrawable(green);
            imageViewL1.setImageDrawable(green);

        }

        if (valueB > 30 || valueB < -30) {

            count++;

            if (nstartB == 0) {
                nstartB = System.currentTimeMillis();
            } else {
                nendB = System.currentTimeMillis();
                if (((nendB - nstartB) / 1000) > 20 && count >= 2) {

                    streamID = soundPool.play(soundID, 1, 1, 0, 0, 0.5f);

                    //소리
                } else if (((nendB - nstartB) / 1000) > 10 && count >= 2) {
                    //진동
                    long[] pattern = {0, 500, 200, 400, 100};
                    vibe.vibrate(pattern, -1);

                } else {
                    soundPool.stop(streamID);
                }
            }

            //색변환
            imageViewR2.setImageDrawable(red);
            imageViewL2.setImageDrawable(red);


        } else {
            nstartB = 0;
            nendB = 0;


            imageViewR2.setImageDrawable(green);
            imageViewL2.setImageDrawable(green);


        }


        if (valueC > 30 || valueC < -30) {

            count++;

            if (nstartC == 0) {
                nstartC = System.currentTimeMillis();
            } else {
                nendC = System.currentTimeMillis();

                if (((nendC - nstartC) / 1000) > 20 && count >= 2) {

                    streamID = soundPool.play(soundID, 1, 1, 0, 0, 0.5f);

                    //소리
                } else if (((nendC - nstartC) / 1000) > 10 && count >= 2) {
                    //진동
                    long[] pattern = {0, 500, 200, 400, 100};
                    vibe.vibrate(pattern, -1);

                } else {
                    soundPool.stop(streamID);
                }
            }

            imageViewR3.setImageDrawable(red);
            imageViewL3.setImageDrawable(red);


            //색변환

        } else {
            nstartC = 0;
            nendC = 0;

            imageViewR3.setImageDrawable(green);
            imageViewL3.setImageDrawable(green);

        }

    }

    //블루투스가 켜져있지 않으면 블루투스를 켜달라고 함.
    private void enableBluetooth() {
        BluetoothSerialClient btSet = mClient;
        btSet.enableBluetooth(this, new BluetoothSerialClient.OnBluetoothEnabledListener() {
            @Override
            public void onBluetoothEnabled(boolean success) {
                if (success) {
                    getPairedDevices();
                } else {
                    Log.d("블루투스", "블루투스를 실행해야합니다.");
                    finish();
                }
            }
        });
    }

    //사용하고자하는 블루투스 기기랑 미리 페어링을 해놓아야한다.
    //디바이스와 페어링 되어있는 블루투스 기기들의 목록을 가져온다.
    private void getPairedDevices() {
        Set<BluetoothDevice> devices = mClient.getPairedDevices();
        for (BluetoothDevice device : devices) {
            if (device.getName().equals("PROJECT")) {
                Log.d("블루투스", "기기를 찾았습니다.");
                mDevice = device;
            }
        }
        if (mDevice == null) {
            Log.d("블루투스", "블루투스를 찾지 못했습니다.");
            finish();
        }
    }

    //블루투스 연결 및 쓰레드 생성
    private void connect(BluetoothDevice device) {
        BluetoothSerialClient btSet = mClient;
        btSet.connect(getApplicationContext(), device, mBTHandler);
    }

    private BluetoothSerialClient.BluetoothStreamingHandler mBTHandler = new BluetoothSerialClient.BluetoothStreamingHandler() {
        @Override
        public void onError(Exception e) {
            Log.d("블루투스", "에러발생" + e);
        }

        @Override
        public void onDisconnected() {
            Log.d("블루투스", "연결종료");
        }

        @Override
        public void onData(byte[] buffer, int length) {
            Log.d("블루투스", "데이터 들어옴");

            //데이터는 A, 백의자리, 십의자리, 일의자리로 들어옴.
            //데이터가 Buffer한번으로 들어오지 않고 따로따로 들어오는 경우가 많아서 이렇게 생성하였음.
            for (int i = 0; i < length; i++) {
                Log.d("블루투스", "" + buffer[i] + " count = " + mCount);
                if (mCount == 6) {
                    mCount = 0;
                }
                mDataBuffer[mCount] = buffer[i];
                if (mCount == 5) {
                    //   dataChange(mDataBuffer);
                    setGraph(mDataBuffer);
                }
                mCount++;
            }
        }

        @Override
        public void onConnected() {
            Log.d("블루투스", "연결됨");
        }
    };
}
