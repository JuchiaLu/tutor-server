package com.juchia.tutor.auth.base.authentication.code.impl.image;


import com.juchia.tutor.auth.base.authentication.code.core.Provider;
import com.juchia.tutor.auth.base.authentication.code.core.ValidateCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

@Component
public class ImageCodeProvider implements Provider {
    @Override
    public String support() {
        return "imageCode";
    }

    @Override
    public void sent(HttpServletRequest request, HttpServletResponse response, ValidateCode code) {

        String responseType = ServletRequestUtils.getStringParameter(request, "responseType", "image");

        ImageCode imageCode = (ImageCode) code;
        BufferedImage bufferedImage = imageCode.getBufferedImage();

        if (responseType.equalsIgnoreCase("image")){
            response.setHeader("Content-Type","image/png");
            try {
                ImageIO.write(bufferedImage, "png",response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String pngBase64 = bufferedImage2base64(bufferedImage);
                response.getWriter().write("data:image/png;base64,"+pngBase64);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public ValidateCode generate(HttpServletRequest request) {
        int width = ServletRequestUtils.getIntParameter(request, "width", 60);
        int height = ServletRequestUtils.getIntParameter(request, "height", 80);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String validateCode = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            validateCode += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(validateCode, 60,image);
    }

    /**
     * 生成随机背景条纹
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }


    /**
     * 转 图片转 base64
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    private  String bufferedImage2base64(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//io流
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);//写入流中
        byte[] bytes = byteArrayOutputStream.toByteArray();//转换成字节
        String pngBase64 =  Base64.getEncoder().encodeToString(bytes).trim();//转换成base64串
        return pngBase64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
    }
}
