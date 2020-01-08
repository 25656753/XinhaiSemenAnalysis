package com.xinhai.xinhaisemenanalysis;

import android.content.Context;
import android.content.Intent;


import com.lib.funsdk.support.models.FunDevType;
import com.lib.funsdk.support.models.FunDevice;
import com.xinhai.xinhaisemenanalysis.devices.ActivityGuideDeviceBulb;
import com.xinhai.xinhaisemenanalysis.devices.ActivityGuideDeviceSocket;
import com.xinhai.xinhaisemenanalysis.devices.ActivityGuideDeviceSport;
import com.xinhai.xinhaisemenanalysis.devices.monitor.ActivityGuideDeviceCamera;

import java.util.HashMap;
import java.util.Map;

public class DeviceActivitys {

	private static Map<FunDevType, Class<?>> sDeviceActivityMap = new HashMap<FunDevType, Class<?>>();
	
	static {
		// 监控设备
		sDeviceActivityMap.put(FunDevType.EE_DEV_NORMAL_MONITOR,
				ActivityGuideDeviceCamera.class);
		
		// 智能插座
		sDeviceActivityMap.put(FunDevType.EE_DEV_INTELLIGENTSOCKET,
				ActivityGuideDeviceSocket.class);
		
		// 情景灯泡
		sDeviceActivityMap.put(FunDevType.EE_DEV_SCENELAMP,
				ActivityGuideDeviceBulb.class);
		
		// 大眼睛行车记录仪
		sDeviceActivityMap.put(FunDevType.EE_DEV_BIGEYE,
				ActivityGuideDeviceCamera.class);
		
		// 小雨点
		sDeviceActivityMap.put(FunDevType.EE_DEV_SMALLEYE,
				ActivityGuideDeviceCamera.class);
		
		// 鱼眼小雨点
		sDeviceActivityMap.put(FunDevType.EE_DEV_SMALLRAINDROPS_FISHEYE,
				ActivityGuideDeviceCamera.class);
		
		// 鱼眼灯泡,全景灯
		sDeviceActivityMap.put(FunDevType.EE_DEV_LAMP_FISHEYE,
				ActivityGuideDeviceCamera.class);
		
		// 雄迈摇头机
		sDeviceActivityMap.put(FunDevType.EE_DEV_BOUTIQUEROTOT,
				ActivityGuideDeviceCamera.class);

		// 小黄人
		sDeviceActivityMap.put(FunDevType.EE_DEV_MINIONS,
				ActivityGuideDeviceCamera.class);
		
		// 运动相机
		sDeviceActivityMap.put(FunDevType.EE_DEV_SPORTCAMERA,
				ActivityGuideDeviceSport.class);

		// 智能门铃
		sDeviceActivityMap.put(FunDevType.EE_DEV_IDR,
                ActivityGuideDeviceCamera.class);

		// 智能门铃
		sDeviceActivityMap.put(FunDevType.EE_DEV_IDR,
				ActivityGuideDeviceCamera.class);
	}
	
	public static void startDeviceActivity(Context context, FunDevice funDevice) {
		Class<?> _class = sDeviceActivityMap.get(funDevice.devType);
		//Class<?> _class = sDeviceActivityMap.get(FunDevType.EE_DEV_BIGEYE);
		if (_class == null) {
			_class = ActivityGuideDeviceCamera.class;
		}
		if ( null != _class ) {
			Intent intent = new Intent();
			intent.setClass(context, _class);
			intent.putExtra("FUN_DEVICE_ID", funDevice.getId());
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}
	
	public static Class<?> getDeviceActivity(FunDevice funDevice) {
		if ( null == funDevice ) {
			return null;
		}
		return sDeviceActivityMap.get(funDevice.devType);
	}
}
