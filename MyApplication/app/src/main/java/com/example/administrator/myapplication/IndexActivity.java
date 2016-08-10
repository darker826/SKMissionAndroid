package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        ImageView image =(ImageView)this.findViewById(R.id.image_chair);
        //setScaleType(ImageView.ScaleType.FIT_CENTER);

        Button imageButton = (Button)findViewById(R.id.button_image);
        imageButton.setOnClickListener(mClickListener);

        Button graphButton = (Button)findViewById(R.id.button_graph);
        graphButton.setOnClickListener(mClickListener);

        Button settingButton = (Button)findViewById(R.id.button_mypage);
        settingButton.setOnClickListener(mClickListener);
    }

    Button.OnClickListener mClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()){
                case R.id.button_image:
                    intent = new Intent(IndexActivity.this, ImageActivity.class);
                    // 첫번째 액티비티를 실행하기 위한 인텐트
                    startActivity(intent);
                    // 첫번째 액티비티를 실행합니다.
                    break;

                case R.id.button_graph:
                    intent = new Intent(IndexActivity.this, GraphActivity.class);
                    // 두번째 액티비티를 실행하기 위한 인텐트
                    startActivity(intent);
                    // 두번째 액티비티를 실행합니다.
                    break;

                case R.id.button_mypage:
                    intent = new Intent(IndexActivity.this, MyPageActivity.class);
                    // 두번째 액티비티를 실행하기 위한 인텐트
                    startActivity(intent);
                    // 두번째 액티비티를 실행합니다.
                    break;
            }
        }
    };
}

