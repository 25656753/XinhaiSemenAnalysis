package com.xinhai.xinhaisemenanalysis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinhai.xinhaisemenanalysis.devices.monitor.ActivityGuideDeviceCamera;
import com.xinhai.xinhaisemenanalysis.utils.IntentUtils;

import java.io.File;
import java.util.List;

public class LocfilelistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RecordlistFragment.recordfile> list;
    private Context context;
    private final String recordbaseurl = Environment.getExternalStorageDirectory() +
            "/com.xinhai.xinhaisemenanalysis/videorecord/";
    private final String picbaseurl = Environment.getExternalStorageDirectory() +
            "/com.xinhai.xinhaisemenanalysis/snapshot/";

    static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imageView;
        TextView tvfilename;
        TextView tvtimelong;
        TextView tvwidth;
        TextView tvheight;
        ImageButton btndelfile, btnshare;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.imageview);
            tvfilename = itemView.findViewById(R.id.filename);
            tvtimelong = itemView.findViewById(R.id.retimelong);
            tvwidth = itemView.findViewById(R.id.tvwidth);
            tvheight = itemView.findViewById(R.id.tvheight);
            btndelfile = itemView.findViewById(R.id.btndelfile);
            btnshare = itemView.findViewById(R.id.btnshare);
        }
    }


    public LocfilelistAdapter(Context context, List<RecordlistFragment.recordfile> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 分享图片
     */
    public void shareImage(RecordlistFragment.recordfile recordfile) {
        File file = null;
        int type = 0;
        if ("0".equals(recordfile.getMediatype())) {
            file = new File(recordbaseurl + recordfile.getName());
            type = 1;
        } else {
            file = new File(picbaseurl + recordfile.getName());
            type = 0;
        }
        if (!file.exists()) {
            return;
        }
        Uri imgUri;
        ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // UpdateConfig.FILE_PROVIDER_AUTH 即是在清单文件中配置的authorities
            imgUri = FileProvider.getUriForFile(context, "com.xinhai.xinhaisemenanalysis.FileProvider", file);
            // 给目标应用一个临时授权

        } else {
            imgUri = Uri.fromFile(file);
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        //其中imgUri为图片的标识符
        shareIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
        if (type == 0) {
            shareIntent.setType("image/*");
        } else {
            shareIntent.setType("video/*");
        }
        //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
        shareIntent = Intent.createChooser(shareIntent, "请选择需分享的应用");

        context.startActivity(shareIntent);
    }

    @NonNull
    @Override
    public LocfilelistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        View view = LinearLayout.inflate(context, R.layout.layout_locfilelist_item, null);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                RecordlistFragment.recordfile recordfile = list.get(position);
                if ("0".equals(recordfile.getMediatype())) {
                    ((ActivityGuideDeviceCamera) context).playlocVideofile(recordfile.getName());
                } else {
                    Toast.makeText(v.getContext(), "图片" + recordfile.getName() + "不支持播放", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //控件设置值
        final RecordlistFragment.recordfile fn = list.get(position);
        ((ViewHolder) holder).tvfilename.setText(fn.getName());
        ((ViewHolder) holder).tvwidth.setText(fn.getWidth());
        ((ViewHolder) holder).tvheight.setText(fn.getHeight());
        if ("0".equals(fn.getMediatype())) {
            ((ViewHolder) holder).imageView.setImageURI(Uri.parse("file://" + recordbaseurl + fn.getName()));
            ((ViewHolder) holder).tvtimelong.setText(fn.getTimelong() + "  (视频)");
        } else {
            ((ViewHolder) holder).imageView.setImageURI(Uri.parse("file://" + picbaseurl + fn.getName()));
            ((ViewHolder) holder).tvtimelong.setText(fn.getTimelong() + "  (图片)");
        }
        ((ViewHolder) holder).btndelfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.socket_timing_on);
                builder.setTitle(R.string.app_name);
                builder.setMessage("您确定要删除当前文件吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File f = null;
                        if ("0".equals(fn.getMediatype()))
                            f = new File(recordbaseurl + fn.getName());
                        else
                            f = new File(picbaseurl + fn.getName());
                        if (f != null) {
                            try {
                                f.delete();
                            } catch (Exception e) {
                            }
                        }
                        list.remove(fn);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });


        ((ViewHolder) holder).btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = null;
                try {
                    if ("0".equals(fn.getMediatype())) {
                        file = new File(recordbaseurl + fn.getName());
                        IntentUtils.shareVideo(context, file, "请选择需分享视频的应用", context.getPackageName() + ".FileProvider");
                    } else {
                        file = new File(picbaseurl + fn.getName());
                        IntentUtils.shareImage(context, file, "请选择需分享图片的应用", context.getPackageName() + ".FileProvider");
                    }
                } catch (Exception e) {
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }


}