package com.jb.third.share.info;

/**
 * 音频分享
 * Created by jianbin on 16/12/21.
 */
public class AudioObject implements MediaObject {
    public String audioUrl;

    public AudioObject(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    @Override
    public int type() {
        return TYPE_AUDIO;
    }
}
