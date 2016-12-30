package com.jb.third.share.platform;

import android.app.Activity;
import android.content.Context;

import com.jb.third.share.info.AudioObject;
import com.jb.third.share.info.LocalImageObject;
import com.jb.third.share.info.MediaObject;
import com.jb.third.share.info.ShareObject;
import com.jb.third.share.info.TextObject;
import com.jb.third.share.info.WebPageObject;

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

    @Override
    public boolean share(ShareObject shareInfo) {
        if (shareInfo.mediaObject != null) {
            switch (shareInfo.mediaObject.type()) {
                case MediaObject.TYPE_WEB:          return share(shareInfo, (WebPageObject)    shareInfo.mediaObject);
                case MediaObject.TYPE_TEXT:         return share(shareInfo, (TextObject)       shareInfo.mediaObject);
                case MediaObject.TYPE_AUDIO:        return share(shareInfo, (AudioObject)      shareInfo.mediaObject);
                case MediaObject.TYPE_LOCAL_IMAGE:  return share(shareInfo, (LocalImageObject) shareInfo.mediaObject);
            }
        }
        return false;
    }

    public abstract boolean share(ShareObject shareObject, TextObject       textObject);
    public abstract boolean share(ShareObject shareObject, AudioObject      audioObject);
    public abstract boolean share(ShareObject shareObject, WebPageObject    webPageObject);
    public abstract boolean share(ShareObject shareObject, LocalImageObject localImageObject);

}
