package com.jb.third.share.platform.wechat;

import android.content.Context;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;

/**
 * 微信收藏
 * Created by jianbin on 16/12/23.
 */
public class WXFavoriteShare extends WXPlatform {

    public WXFavoriteShare(Context context) {
        super(context);
    }

    @Override
    protected int getShareScene() {
        return SendMessageToWX.Req.WXSceneFavorite;
    }
}
