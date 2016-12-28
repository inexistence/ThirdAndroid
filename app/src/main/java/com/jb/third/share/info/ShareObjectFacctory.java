package com.jb.third.share.info;

/**
 * Created by jianbin on 16/12/23.
 */

public class ShareObjectFacctory {

    public static ShareObject createAudioObject(String audioUrl) {
        ShareObject shareObject = new ShareObject();
        shareObject.mediaObject = new AudioObject(audioUrl);
        shareObject.title = "title";
        shareObject.description = "description";
        return shareObject;
    }

    public static ShareObject createWebObject(String webUrl) {
        ShareObject shareObject = new ShareObject();
        shareObject.mediaObject = new WebPageObject(webUrl);
        shareObject.title = "title";
        shareObject.description = "description";
        return shareObject;
    }
}
