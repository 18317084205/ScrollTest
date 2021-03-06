package com.example.administrator.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MainActivity extends Activity {

    private TextSwitcher textSwitcher;
    private String[] strings = new String[]{"第一行", "第二行", "第三行", "第四行", "第五行"};
    private int switcherCount = 0;
    private Handler mHandler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            scroll();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        textSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                TextView tv = new TextView(MainActivity.this);
                return tv;
            }
        });
        textSwitcher.setInAnimation(getApplicationContext(),
                R.anim.enter);
        textSwitcher.setOutAnimation(getApplicationContext(), R.anim.out);
    }

    private void start() {
        mHandler.postDelayed(runnable, 2000);
    }

    private void scroll() {
        String strA = strings[switcherCount % strings.length];
        String strB = switcherCount % strings.length + 1 < strings.length ? strings[switcherCount % strings.length + 1] : strings[0];
        switcherCount += 2;
        textSwitcher.setText(strA + "\n" + strB);
        start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        scroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
    }
}
