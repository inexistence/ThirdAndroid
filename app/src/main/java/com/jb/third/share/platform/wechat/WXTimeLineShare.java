package com.jb.third.share.platform.wechat;

import android.content.Context;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;

/**
 * 朋友圈分享
 * Created by jianbin on 16/12/23.
 */
public class WXTimeLineShare extends WXPlatform {

    public WXTimeLineShare(Context context) {
        super(context);
    }

    @Override
    protected int getShareScene() {
        return SendMessageToWX.Req.WXSceneTimeline;
    }
}
