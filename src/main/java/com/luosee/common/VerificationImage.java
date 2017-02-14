package com.luosee.common;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by server1 on 2016/11/14.
 */
//生成验证码
public class VerificationImage {
    private final static String VERIFICATIONCODE="abcdefghjklmnpqrstuvwsyz23456789";
    private final static int IMAGEWIDTH=95;
    private final static int IMAGEHEIGHT=33;
    private Random random=new Random();
    public String getImage(HttpServletResponse httpResponse)
    {
        String code=getCode();
        try {
            creatImage(httpResponse.getOutputStream(),code);
            return code;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCode()
    {
        StringBuilder code=new StringBuilder();
        for (int i=0;i<4;i++)
        {
            code.append(VERIFICATIONCODE.charAt(random.nextInt(VERIFICATIONCODE.length())));
        }
        return code.toString();
    }

    public void creatImage(OutputStream os, String code)
    {
        BufferedImage bufferedImage=new BufferedImage(IMAGEWIDTH,IMAGEHEIGHT,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D= bufferedImage.createGraphics();

        Color[] backgroundColor={Color.MAGENTA,Color.pink,Color.RED,Color.orange,Color.DARK_GRAY,Color.gray};
        graphics2D.setColor(getColor(200,255));
        graphics2D.fillRect(1,1,IMAGEWIDTH-2,IMAGEHEIGHT-2);

        graphics2D.setColor(backgroundColor[random.nextInt(backgroundColor.length)]);
        graphics2D.drawRect(0,0,IMAGEWIDTH,IMAGEHEIGHT);

        graphics2D.setColor(getColor(150,200));
        graphics2D.setStroke(new BasicStroke(2.5f));
        graphics2D.drawLine(0,random.nextInt(IMAGEHEIGHT),IMAGEWIDTH,random.nextInt(IMAGEHEIGHT));

        graphics2D.setColor(getColor(100,150));

        int fontSize=IMAGEHEIGHT-6;
        Font font=new Font("Algerian", Font.ITALIC, fontSize);
        graphics2D.setFont(font);
        char[] chars = code.toCharArray();
        int verifySize=code.length();
        for(int i = 0; i <verifySize ; i++){
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 1 : -1), (IMAGEWIDTH / verifySize) * i + fontSize/2, IMAGEHEIGHT/2);
            graphics2D.setTransform(affine);
            graphics2D.drawChars(chars, i, 1, ((IMAGEWIDTH-10) / verifySize) * i + 5, IMAGEHEIGHT/2 + fontSize/2 - 10);
        }
        graphics2D.dispose();
        try {
            ImageIO.write(bufferedImage,"jpg",os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Color getColor(int bc,int tc)
    {
        int r=bc+random.nextInt(tc-bc);
        int g=bc+random.nextInt(tc-bc);
        int b=bc+random.nextInt(tc-bc);
        return new Color(r,g,b);
    }
}
