package com.xinhai.xinhaisemenanalysis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.xinhai.xinhaisemenanalysis.devices.monitor.ActivityGuideDeviceCamera;

public class ActivityStartup extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_startup);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				entermain();
				finish();
			}
		},2000);

	}

	private void entermain()
	{
		Intent intent=new Intent(ActivityStartup.this, ActivityGuideDeviceCamera.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	

}
