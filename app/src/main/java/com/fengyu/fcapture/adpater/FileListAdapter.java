package com.fengyu.fcapture.adpater;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fengyu.fcapture.R;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class FileListAdapter extends FBaseAdapter<File> {

    public FileListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.file_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final File file = getItem(position);
        holder.fileName.setText(file.getName());
        holder.shareBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                share(file);
            }
        });
        holder.delBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                file.delete();
                if (callBack != null) {
                    callBack.doListChange();
                }
            }
        });
        return convertView;
    }

    private void share(File file) {
        Intent targeted = new Intent(Intent.ACTION_VIEW);
        targeted.setDataAndType(Uri.fromFile(file), "application/x-msmediaview");
        List<ResolveInfo> resInfo = mContext.getPackageManager().queryIntentActivities(targeted, 0);
        ActivityInfo activityInfo = null;
        for (ResolveInfo info : resInfo) {
            if (info.activityInfo.packageName.contains("com.tencent.mobileqq")) {
                activityInfo = info.activityInfo;
                break;
            }
        }
        if (activityInfo != null) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            targeted.setPackage(activityInfo.packageName);
            intent.setDataAndType(Uri.fromFile(file), "application/x-msmediaview");
            ;
            mContext.startActivity(intent);
        } else {
            Toast.makeText(mContext, "没有安装QQ程序！", Toast.LENGTH_SHORT).show();
        }
    }

    public interface CallBack {
        public void doListChange();
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    static class ViewHolder {

        TextView fileName;
        Button shareBtn;
        Button delBtn;

        public ViewHolder(View convertView) {
            fileName = (TextView) convertView.findViewById(R.id.fileName);
            shareBtn = (Button) convertView.findViewById(R.id.shareBtn);
            delBtn = (Button) convertView.findViewById(R.id.delBtn);
        }
    }

}
