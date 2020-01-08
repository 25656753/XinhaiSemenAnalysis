package com.xinhai.xinhaisemenanalysis.devices.monitor;



import com.xinhai.xinhaisemenanalysis.ActivityGuide;
import com.xinhai.xinhaisemenanalysis.ActivityGuideDeviceListAP;
import com.xinhai.xinhaisemenanalysis.DemoModule;
import com.xinhai.xinhaisemenanalysis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-11-24.
 */

public class ActivityGuideMediaRealPlay extends ActivityGuide {

    private static List<DemoModule> mMoudules = new ArrayList<DemoModule>();

    static {
        // 2.1 连接设备(通过序列号连接)
        mMoudules.add(new DemoModule(-1,
                R.string.guide_module_title_device_sn,
                -1,
                ActivityGuideDeviceListAP.class));

        // 2.2 连接设备(附近AP直连)
        mMoudules.add(new DemoModule(-1,
                R.string.guide_module_title_device_ap,
                -1,
                ActivityGuideDeviceListAP.class));

        // 2.3 连接设备(局域网内)
        mMoudules.add(new DemoModule(-1,
                R.string.guide_module_title_device_lan,
                -1,
                ActivityGuideDeviceListAP.class));
    }
    @Override
    protected List<DemoModule> getGuideModules() {
        return mMoudules;
    }
}
