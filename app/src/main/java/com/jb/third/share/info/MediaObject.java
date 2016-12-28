package com.jb.third.share.info;

/**
 * Created by jianbin on 16/12/23.
 */

public interface MediaObject {
    int TYPE_UNKNOWN = 0;
    int TYPE_TEXT = 1;
    int TYPE_LOCAL_IMAGE = 2;
    int TYPE_AUDIO = 3;
    int TYPE_WEB = 5;

    int type();
}
