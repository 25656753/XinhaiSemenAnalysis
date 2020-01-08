package com.xinhai.xinhaisemenanalysis;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lib.SDKCONST;
import com.lib.funsdk.support.FunSupport;
import com.lib.funsdk.support.utils.MyAdapter;
import com.lib.funsdk.support.widget.FunVideoView;
import com.lib.sdk.struct.H264_DVR_FINDINFO;
import com.xinhai.xinhaisemenanalysis.devices.monitor.ActivityGuideDeviceCamera;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class RecordlistFragment extends Fragment {
    private  List<recordfile> flielist=new ArrayList<>();
    private   RecyclerView recyclerView;
    private LocfilelistAdapter LocfilelistAdapter;
    private TextView mTextTitle = null;
    private LinearLayoutManager mLayoutManager;
    private  final  String recordbaseurl= Environment.getExternalStorageDirectory()+
            "/com.xinhai.xinhaisemenanalysis/videorecord/";
    private  final  String picbaseurl= Environment.getExternalStorageDirectory()+
            "/com.xinhai.xinhaisemenanalysis/snapshot/";


    public RecordlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_recordlist, container, false);
        mTextTitle = (TextView)view.findViewById(R.id.textViewInTopLayout);
        mTextTitle.setText(R.string.locrecordfilelist);
        view.findViewById(R.id.backBtnInTopLayout).setVisibility(View.GONE);
        initdata();
        recyclerView=view.findViewById(R.id.cvfilelist);
        mLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        LocfilelistAdapter=new LocfilelistAdapter(getActivity(),flielist);
        recyclerView.setAdapter(LocfilelistAdapter);
        return  view;
    }


    public   void initdata()
    {
        flielist.clear();
        File file=new File(recordbaseurl);

        for (File r: file.listFiles()) {
            if (!r.isDirectory()) {
                recordfile f =new recordfile();
                f.setName(r.getName());
                f.setMediatype("0");
                getPlayTime("file://"+r.getAbsolutePath(),f);
                flielist.add(f);
            }
        }
        File picfiles=new File(picbaseurl);
        for (File r: picfiles.listFiles()) {
            if (!r.isDirectory()) {
                recordfile f =new recordfile();
                f.setName(r.getName());
                f.setMediatype("1");
                getPlayTime("file://"+r.getAbsolutePath(),f);
                flielist.add(f);
            }
        }

        Collections.reverse(flielist);

    }

    private void  getPlayTime(String mUri,recordfile f)
    {
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();
        try {
            if (mUri != null)
            {
                HashMap<String, String> headers = null;
                if (headers == null)
                {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(mUri, headers);
            } else
            {
                //mmr.setDataSource(mFD, mOffset, mLength);
            }

            String duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
            String width = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);//宽
            String height = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);//高
            f.setTimelong( Integer.parseInt(duration)/1000+"秒");
            f.setHeight(height);
            f.setWidth(width);
          //  Toast.makeText(getContext(), "playtime:"+ duration+"w="+width+"h="+height, Toast.LENGTH_SHORT).show();

        } catch (Exception ex)
        {
            Log.e("TAG", "MediaMetadataRetriever exception " + ex);
        } finally {
            mmr.release();
        }

    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            initdata();
            LocfilelistAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


public  class recordfile implements  Comparable<recordfile> {


    private String name;
    private String timelong;
    private  String width;

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    private String mediatype;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimelong() {
        return timelong;
    }

    public void setTimelong(String timelong) {
        this.timelong = timelong;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    private  String height;


    @Override
    public int compareTo(@NonNull recordfile o) {
        return name.compareTo(o.getName());
    }
}

}
