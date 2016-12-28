package com.jb.third.share.info;

/**
 * Created by jianbin on 16/12/23.
 */
public class TextObject implements MediaObject {
    public String content;

    public TextObject(String content) {
        this.content = content;
    }

    @Override
    public int type() {
        return TYPE_TEXT;
    }
}
