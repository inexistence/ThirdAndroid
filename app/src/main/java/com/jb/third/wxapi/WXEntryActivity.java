package com.jb.third.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.jb.third.R;
import com.jb.third.share.platform.wechat.WXPlatform;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

/**
 * Created by jianbin on 16/12/23.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WXPlatform.getWXApi(this).handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        WXPlatform.getWXApi(this).handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp instanceof SendMessageToWX.Resp) {
            if (baseResp.errCode == 0) {
                Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
            } else if (baseResp.errCode == -1) {
                Toast.makeText(this, "分享失败", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "分享取消", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }
}
