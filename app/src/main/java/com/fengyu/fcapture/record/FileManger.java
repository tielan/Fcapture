package com.fengyu.fcapture.record;

import android.content.Context;
import android.os.Environment;

import com.fengyu.fcapture.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_MOVIES;

/**
 * Created by Administrator on 2017/2/9.
 */

public class FileManger {
    private static FileManger INSTANCE = new FileManger();
    private final DateFormat fileFormat = new SimpleDateFormat("'f_'yyyy-MM-dd_HH-mm-ss'.mp4'", Locale.US);
    private Context mContext;
    private File outputDir;

    protected FileManger() {
    }

    public static FileManger getInstance() {
        return INSTANCE;
    }

    public void init(Context mContext) {
        this.mContext = mContext;
        createDir();
    }

    private void createDir() {
        File picturesDir = Environment.getExternalStoragePublicDirectory(DIRECTORY_MOVIES);
        outputDir = new File(picturesDir, mContext.getResources().getString(R.string.app_name));
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
    }

    public String getNewFilePath() {
        String outputName = fileFormat.format(new Date());
        return new File(outputDir, outputName).getAbsolutePath();
    }

    public List<File> getListFile() {
        List<File> fileList = new ArrayList<>();
        if (outputDir != null && outputDir.exists()) {
            File[] files = outputDir.listFiles();
            for (File f : files) {
                if (f.getName().endsWith(".mp4")) {
                    fileList.add(f);
                }
            }
        }
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o2.getName().compareTo(o1.getName());
            }
        });
        return fileList;
    }
}
