package com.xinhai.xinhaisemenanalysis;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.lib.funsdk.support.FunPath;
import com.lib.funsdk.support.FunSupport;
import com.xinhai.xinhaisemenanalysis.download.XDownloadFileManager;


public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        DiskCacheConfig build = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("image")
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                .build();
        //生成配置文件
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(build)
                .build();
        Fresco.initialize(this,config);




        FunSupport.getInstance().init(this);
     /**
     * 以下是网络图片下载等的本地缓存初始化,可以加速图片显示,和节省用户流量
    * 跟FunSDK无关,只跟com.example.download内容相关
    */
        String cachePath = FunPath.getCapturePath();
        XDownloadFileManager.setFileManager(
                cachePath, 				// 缓存目录
                20 * 1024 * 1024		// 20M的本地缓存空间
        );
    }


    @Override
    public void onTerminate() {
        Log.i(TAG,"app exit");
        super.onTerminate();
        FunSupport.getInstance().term();
    }

    public void exit() {

        FunSupport.getInstance().term();
    }

}
