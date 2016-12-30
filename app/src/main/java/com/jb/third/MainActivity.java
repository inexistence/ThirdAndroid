package com.jb.third;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jb.third.share.info.ShareObjectFacctory;
import com.jb.third.share.platform.ShareConfig;
import com.jb.third.share.platform.SharePlatform;
import com.jb.third.share.platform.wechat.WXTimeLineShare;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharePlatform.setShareConfig(new ShareConfig().wxAppId("wxd930ea5d5a258f4f"));

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("分享网页")
                        .setCancelable(true)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                new WXTimeLineShare(MainActivity.this)
//                                        .share(ShareObjectFacctory.createAudioObject("http://bmob-cdn-6855.b0.upaiyun.com/2016/11/29/cdcbcc9a407bfba48014abc30a1f541b.mp3"));
                                new WXTimeLineShare(MainActivity.this)
                                        .share(ShareObjectFacctory.createWebObject(MainActivity.this, "http://wwww.baidu.com"));
                            }
                        });
                builder.create().show();
            }
        });


    }
}
