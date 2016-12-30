package com.jb.third.share.platform.wechat;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.jb.third.share.info.AudioObject;
import com.jb.third.share.info.LocalImageObject;
import com.jb.third.share.info.ShareObject;
import com.jb.third.share.info.TextObject;
import com.jb.third.share.info.WebPageObject;
import com.jb.third.share.platform.SharePlatform;
import com.jb.third.utils.ImageUtils;
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
            iwxapi = WXAPIFactory.createWXAPI(context.getApplicationContext(), getShareConfig().wxAppId, false);
            iwxapi.registerApp(getShareConfig().wxAppId);
        }
        return iwxapi;
    }

    @Override
    public boolean share(ShareObject shareObject, TextObject textObject) {
        return share(buildDefaultMessage(shareObject, new WXTextObject((textObject.content))));
    }

    @Override
    public boolean share(ShareObject shareObject, AudioObject audioObject) {
        WXMusicObject musicObject = new WXMusicObject();
        musicObject.musicUrl = audioObject.audioUrl;

        WXMediaMessage mediaMessage = buildDefaultMessage(shareObject, musicObject);
        return share(mediaMessage);
    }

    @Override
    public boolean share(ShareObject shareObject, WebPageObject webPageObject) {
        return share(buildDefaultMessage(shareObject, new WXWebpageObject(webPageObject.webUrl)));
    }

    @Override
    public boolean share(ShareObject shareObject, LocalImageObject localImageObject) {
        return share(buildDefaultMessage(shareObject, new WXImageObject(localImageObject.bytes)));
    }

    @Override
    public boolean share(ShareObject shareInfo) {
        if (shareInfo == null) return false;

        if (!getWXApi(getContext()).isWXAppInstalled()) {
            Toast.makeText(getContext(), "未安装微信", Toast.LENGTH_LONG).show();
            return false;
        }
        return super.share(shareInfo);
    }

    private boolean share(WXMediaMessage mediaMessage) {
        // 构建分享实体
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis()) + req.hashCode();
        req.message = mediaMessage;
        req.scene = getShareScene();
        // 进行分享
        return getWXApi(getContext()).sendReq(req);
    }

    private WXMediaMessage buildDefaultMessage(ShareObject object, WXMediaMessage.IMediaObject mediaObject) {
        WXMediaMessage mediaMessage = new WXMediaMessage(mediaObject);

        mediaMessage.title = object.title;
        mediaMessage.description = object.description;
        if (!TextUtils.isEmpty(object.thumbLocalUrl)) {
            // 图片大小需要控制，否则无法分享
            mediaMessage.thumbData = ImageUtils.bmpToByteArray(ImageUtils.getCompressedBitmap(object.thumbLocalUrl, 30, 30), true);
        }

        return mediaMessage;
    }

    protected abstract int getShareScene();
}
