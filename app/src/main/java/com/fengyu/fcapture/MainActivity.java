package com.fengyu.fcapture;

import android.content.Intent;
import android.graphics.Color;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.fengyu.fcapture.adpater.FileListAdapter;
import com.fengyu.fcapture.record.FileManger;
import com.fengyu.fcapture.service.RecordService;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1212;
    private FileListAdapter fileListAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        Button fab = (Button) findViewById(R.id.btnStart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaProjectionManager manager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
                Intent intent = manager.createScreenCaptureIntent();
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        mListView = (ListView)findViewById(R.id.mListView);
        fileListAdapter = new FileListAdapter(this);
        mListView.setAdapter(fileListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                File file = fileListAdapter.getItem(position);
                Uri uri = Uri.parse(file.getAbsolutePath());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "video/mp4");
                startActivity(intent);
            }
        });
        fileListAdapter.setCallBack(new FileListAdapter.CallBack() {
            @Override
            public void doListChange() {
                fileListAdapter.notifyAdapter(FileManger.getInstance().getListFile());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        fileListAdapter.notifyAdapter(FileManger.getInstance().getListFile());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Intent intent = RecordService.newIntent(this, resultCode, data);
            startService(intent);
            finish();
        }
    }
}
