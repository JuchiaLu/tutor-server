package com.juchia.tutor.auth.base.authentication.code.impl.image;


import com.juchia.tutor.auth.base.authentication.code.support.DefaultCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode extends DefaultCode {

    private BufferedImage bufferedImage;

    public ImageCode(String validateCode, int expireSeconds, BufferedImage bufferedImage) {
        super(validateCode, expireSeconds);
        this.bufferedImage = bufferedImage;
    }

    public ImageCode(String validateCode, LocalDateTime expireTime, BufferedImage bufferedImage) {
        super(validateCode, expireTime);
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
