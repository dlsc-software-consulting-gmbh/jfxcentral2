package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.IkonUtil;

public class VideoDetailHeader extends DetailHeader<Video> {

    public VideoDetailHeader(Video video) {
        super(video);
        setIkon(IkonUtil.video);
        setTitle(video.getName());
    }
}
