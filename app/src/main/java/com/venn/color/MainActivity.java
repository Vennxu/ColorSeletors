package com.venn.color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ColorControlView mColorControlView;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mColorControlView = findViewById(R.id.ccv_sign_select);
        mView = findViewById(R.id.bg);
        mColorControlView.setSingeTouchListener(new ColorControlView.SingleTouchListener() {
            @Override
            public void onChangeColor(int color) {
                mView.setBackgroundColor(color);
            }
        });
    }
}
