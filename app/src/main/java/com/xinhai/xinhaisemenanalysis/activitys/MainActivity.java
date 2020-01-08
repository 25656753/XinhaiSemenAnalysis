package com.xinhai.xinhaisemenanalysis.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.lib.funsdk.support.OnFunLoginListener;
import com.xinhai.xinhaisemenanalysis.ActivityGuideDeviceListAP;
import com.xinhai.xinhaisemenanalysis.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnFunLoginListener {
    @BindView(R.id.button1)
    public ImageButton b1;


    private static final String TAG = "MainActivity";

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.button1})
    public void showtoast(View v) {
      //  readSystem();
        //Toast.makeText(this, ((Button)v).getText(), Toast.LENGTH_LONG).show();

        Intent intent = new Intent();
        intent.setClass(this, ActivityGuideDeviceListAP.class);
        startActivity(intent);

    }
    void readSystem() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSizeLong();
        long blockCount = sf.getBlockCountLong();
        long availCount = sf.getAvailableBlocksLong();
        Log.d("aaa", "block大小:"+ blockSize+",block数目:"+ blockCount+",总大小:"+blockSize*blockCount/1024+"KB");
        Log.d("aaa", "可用的block数目：:"+ availCount+",可用大小:"+ availCount*blockSize/1024/2014+"MB");
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFailed(Integer errCode) {

    }

    @Override
    public void onLogout() {

    }


}
