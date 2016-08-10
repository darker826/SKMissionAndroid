package com.example.administrator.myapplication;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Set;

/**
 * SKMission 아두이노를 활용한 자세교정
 * @author Choi Jaeung(darker826, darker826@gmail.com)
 * 2016-08-04
 */

public class GraphActivity extends AppCompatActivity {
    private BluetoothSerialClient mClient;

    private BluetoothDevice mDevice;
    private GraphView mGraphView;
    private AxisYView mAxisYView;

    private byte mDataBuffer[] = new byte[24];
    private byte mCount = 0;

    private Vibrator vibe;

    @Override
    protected void onDestroy() {
//        mClient.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //Y축 View 생성
        mAxisYView = (AxisYView) findViewById(R.id.GraphAxisY);

        //GraphView 생성
        mGraphView = (GraphView) findViewById(R.id.GraphView);

        //간격설정
        mAxisYView.setSpace(20);
        mGraphView.setSpace(50);

        //그래프에 점 추가 함수
        mGraphView.setPointY(200);
        mGraphView.setPointY(300);
        mGraphView.setPointY(380);
        mGraphView.setPointY(-200);
        mGraphView.setPointY(-100);

        /*
        //블루투스 생성 및 연결
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

    //데이터가 6개 쌓이면 불리는 함수
    private void setGraph(byte[] data){
        int gap = 0;
        int value = 0;
        int count = 0;

        gap = data[0] - data[3];
        value += gap;
        if(gap != 0) count++;

        gap = data[1] - data[4];
        value += gap;
        if(gap != 0) count++;

        gap= data[2] - data[5];
        value += gap;
        if(gap != 0) count++;

        if(count>= 2) {
            long[] pattern = {0, 500, 200, 400, 100};
            vibe.vibrate(pattern, -1);
        }

        mGraphView.setPointY(value);
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

            //데이터가 Buffer 한번으로 들어오지 않고 따로따로 들어오는 경우가 많아서 이렇게 생성하였음.
            //데이터가 전송될 때마다 버퍼에 집어넣고 데이터가 6개 쌓이면 카운트를 초기화함.
            for (int i = 0; i < length; i++){
                Log.d("블루투스", ""+buffer[i] + " count = " + mCount);
                if(mCount == 6){
                    mCount = 0;
                }
                mDataBuffer[mCount] = buffer[i];
                if(mCount == 5){
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
