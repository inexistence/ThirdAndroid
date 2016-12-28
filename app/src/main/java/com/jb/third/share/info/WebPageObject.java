package com.jb.third.share.info;

/**
 * 网页分享实体
 * Created by jianbin on 16/12/21.
 */
public class WebPageObject implements MediaObject {
    public String webUrl;

    public WebPageObject(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public int type() {
        return TYPE_WEB;
    }
}
