package com.xinhai.xinhaisemenanalysis.devices;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


import com.lib.funsdk.support.FunSupport;
import com.lib.funsdk.support.models.FunDevice;
import com.xinhai.xinhaisemenanalysis.ActivityGuide;
import com.xinhai.xinhaisemenanalysis.DemoModule;
import com.xinhai.xinhaisemenanalysis.R;
import com.xinhai.xinhaisemenanalysis.devices.settings.ActivityGuideDeviceChangePassw;
import com.xinhai.xinhaisemenanalysis.devices.settings.ActivityGuideDeviceSetupAlarm;
import com.xinhai.xinhaisemenanalysis.devices.settings.ActivityGuideDeviceSetupCamera;
import com.xinhai.xinhaisemenanalysis.devices.settings.ActivityGuideDeviceSystemInfo;

import java.util.ArrayList;
import java.util.List;

/*
 * 插座的設置配置界面
 * 
 */
public class ActivityGuideDeviceSocketSetting extends ActivityGuide implements OnItemClickListener{

	private static List<DemoModule> mGuideModules = new ArrayList<DemoModule>();
	
	private FunDevice mFunDevice = null;
	
	static {
		//圖像上下翻轉
		mGuideModules.add(new DemoModule(
				R.drawable.icon_funsdk,
				R.string.device_setup_camera_flip,
				R.string.device_setup_camera_flip_prompt,
				ActivityGuideDeviceSetupCamera.class));
		//移动报警
		mGuideModules.add(new DemoModule(
				R.drawable.icon_funsdk, 
				R.string.device_alarm_motion_detection, 
				R.string.device_alarm_motion_detection_tip,
				ActivityGuideDeviceSetupAlarm.class));
		//状态指示灯
		mGuideModules.add(new DemoModule(
				R.drawable.icon_funsdk, 
				R.string.device_setup_status_indicator, 
				R.string.device_setup_status_indicator_prompt,
				ActivityGuideDevicesSettingStatusLed.class));
		//自动感应灵敏度
		mGuideModules.add(new DemoModule(
				R.drawable.icon_funsdk, 
				R.string.device_setup_auto_sense, 
				R.string.device_setup_auto_sense_config_alarm,
				 ActivityGuideDevicesSocketSensitiveSetting.class));
		//电量统计
		mGuideModules.add(new DemoModule(
				R.drawable.icon_funsdk,
				R.string.device_setup_power, 
				R.string.device_setup_hint_workrecord_alarm,
				 ActivityGuideDevicesSocketAboutWork.class));
		// 密码修改
		mGuideModules.add(new DemoModule(
				R.drawable.icon_funsdk, 
				R.string.device_setup_change_password, 
				R.string.device_setup_hint_pwd_modify_alarm, 
				ActivityGuideDeviceChangePassw.class));
		//关于设备
		mGuideModules.add(new DemoModule(
				R.drawable.icon_funsdk, 
				R.string.device_system_info, 
				R.string.device_setup_hint_about_dev_alarm,
				ActivityGuideDeviceSystemInfo.class));
	}
	
	
	
	
	@Override
	protected List<DemoModule> getGuideModules() {
		return mGuideModules;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int devId = getIntent().getIntExtra("FUN_DEVICE_ID", 0);
		Log.e("devId", devId+"");
		mFunDevice = FunSupport.getInstance().findDeviceById(devId);
		Log.e("devId", mFunDevice+"");
		
		// 设置标题为设备配置			
		mTextTitle.setText(R.string.device_setup);  
		// 显示返回按钮
		mBtnBack.setVisibility(View.VISIBLE);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		try {
			getGuideModule(position).startModule(this, mFunDevice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
	
