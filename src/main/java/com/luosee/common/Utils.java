package com.luosee.common;

import com.aliyun.oss.OSSClient;
import com.luosee.oss.Constant;
import com.luosee.oss.RandomStrings;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Hex;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by server1 on 2016/11/15.
 */
public class Utils {
    public static String getSHA256Base64String(String sourceString) {
        try {
            return getBase64WithDigestString(sourceString, "SHA-256");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String getMD5HexString(String sourceString) {
        try {
            return getHexWithDigestString(sourceString, "MD5");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private static String getHexWithDigestString(String sourceString, String algorithmn) throws UnsupportedEncodingException {
        MessageDigest md5Digest = getMessageDigest(algorithmn);
        byte[] result = md5Digest.digest(sourceString.getBytes("UTF-8"));
        return new String(Hex.encode(result));
    }
    private static String getBase64WithDigestString(String sourceString, String algorithmn) throws UnsupportedEncodingException {
        MessageDigest md5Digest = getMessageDigest(algorithmn);
        byte[] result = md5Digest.digest(sourceString.getBytes("UTF-8"));
        return new String(Base64.encode(result));
    }

    protected static final MessageDigest getMessageDigest(String algorithm) throws IllegalArgumentException {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException var2) {
            throw new IllegalArgumentException("No such algorithm [" + algorithm + "]");
        }
    }

    public static String getOSSBucket(boolean formal) {
        return formal ? Constant.OSS_BUCKET_NAME : Constant.OSS_BUCKET_TEST_NAME;
    }

    public static OSSClient getOSSClient() {
        OSSClient ossClient = new OSSClient(Constant.OSS_END_POINT, Constant.OSS_ACCESSKEY_ID, Constant.OSS_ACCESSKEY_SECRET);
        return ossClient;
    }

    public static String generateFileName(String string) {
        String  fileName = string+"_"+
                new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:SSS").format(new Date())+"_"
                +new RandomStrings(5).nextString();
        return fileName;
    }

    //生成截图文件名
    public static String generateOSSSreenshotFileName(String userId, String suffix) {
        return Constant.OSS_SCREENSHOTS  + generateFileName(userId) + "." + suffix;
    }

    //生成户型文件名
    public static String generateOSSFloorplanFileName(String userId) {
        return Constant.OSS_FLOOR_PLANS + generateFileName(userId);
    }

    //生成方案文件名
    public static String generateOSSPlanFileName(String userId) {
        return Constant.OSS_MY_PLANS + generateFileName(userId);
    }

    //文件扩展名???
    public static String getFileExtension(String filename) {
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i + 1);
        }
        return extension;
    }

    //转换url格式
    public static String decodeOSSURL(String src) {
        try {
            return URLDecoder.decode(src, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
