package com.jb.third.share.platform.wechat;

import android.content.Context;
import android.widget.Toast;

import com.jb.third.share.info.AudioObject;
import com.jb.third.share.info.LocalImageObject;
import com.jb.third.share.info.MediaObject;
import com.jb.third.share.info.ShareObject;
import com.jb.third.share.info.TextObject;
import com.jb.third.share.info.WebPageObject;
import com.jb.third.share.platform.SharePlatform;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信分享基类
 * Created by jianbin on 16/12/23.
 */
public abstract class WXPlatform extends SharePlatform {

    private static IWXAPI iwxapi;

    public WXPlatform(Context context) {
        super(context);
    }

    public static IWXAPI getWXApi(Context context) {
        if (iwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(context, getShareConfig().wxAppId);
            iwxapi.registerApp(getShareConfig().wxAppId);
        }
        return iwxapi;
    }

    /**
     * 构建分享实体
     * @param object
     * @return
     */
    private WXMediaMessage buildShareObject(ShareObject object) {
        WXMediaMessage mediaMessage = new WXMediaMessage();

        mediaMessage.title = object.title;
        mediaMessage.description = object.description;
        mediaMessage.thumbData = object.thumbData;

        if (object.mediaObject != null) {
            int type = object.mediaObject.type();
            switch (type) {
                case MediaObject.TYPE_AUDIO:
                    WXMusicObject musicObject = new WXMusicObject();
                    musicObject.musicUrl = ((AudioObject) object.mediaObject).audioUrl;
                    mediaMessage.mediaObject = musicObject;
                    break;
                case MediaObject.TYPE_LOCAL_IMAGE:
                    mediaMessage.mediaObject = new WXImageObject(((LocalImageObject) object.mediaObject).bytes);
                    break;
                case MediaObject.TYPE_TEXT:
                    mediaMessage.mediaObject = new WXTextObject(((TextObject)object.mediaObject).content);
                    break;
                case MediaObject.TYPE_WEB:
                    mediaMessage.mediaObject = new WXWebpageObject(((WebPageObject) object.mediaObject).webUrl);
                    break;
                case MediaObject.TYPE_UNKNOWN: // 不实现
                    break;
            }
        }
        return mediaMessage;
    }

    @Override
    public boolean share(ShareObject shareObject) {
        if (!getWXApi(getContext()).isWXAppInstalled()) {
            Toast.makeText(getContext(), "未安装微信", Toast.LENGTH_SHORT).show();
            return false;
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis()) + req.hashCode();
        req.message = buildShareObject(shareObject);
        req.scene = getShareScene();

        getWXApi(getContext()).sendReq(req);
        return true;
    }

    protected abstract int getShareScene();
}
