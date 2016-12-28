package com.jb.third.share.platform;

import android.content.Context;

/**
 * Created by jianbin on 16/12/23.
 */
public abstract class SharePlatform implements ShareAction {
    private static ShareConfig shareConfig;

    public static void setShareConfig(ShareConfig config) {
        shareConfig = config;
    }

    public static ShareConfig getShareConfig() {
        if (shareConfig == null) {
            throw new IllegalArgumentException("please call SharePlatform.setShareConfig(ShareConfig) first!");
        }
        return shareConfig;
    }

    private Context mContext;

    public SharePlatform(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

}
