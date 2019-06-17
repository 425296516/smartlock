package com.anlida.smartlock.zxing.encode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * This class does the work of decoding the user's request and extracting all the DataBean
 * to be encoded in a barcode.
 */
public final class QRCodeEncoder {

    private static Bitmap scanBitmap;

    /**
     * 生成二维码图片
     * url 要转换的地址或字符串,可以是中文
     */
    public static Bitmap createQRPic(String url, int dimension) throws WriterException {
        // 判断URL合法性
        if (url == null || "".equals(url) || url.length() < 1) {
            return null;
        }
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 图像数据转换，使用了矩阵转换
        BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, dimension, dimension, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        // 下面这里按照二维码的算法，逐个生成二维码的图片，
        // 两个for循环是图片横列扫描的结果
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (bitMatrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * height + x] = 0xffffffff;
                }
            }
        }
        // 生成二维码图片的格式，使用ARGB_8888
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 扫描二维码图片
     */
    public static Result scanQRPic(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable();
        // 设置二维码内容的编码
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        // 获取到待解析的图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置inJustDecodeBounds=true， 这时bitmap为null,只是把图片的宽高放在Options里
        options.inJustDecodeBounds = true;
        // 这段代码之后，options.outWidth 和 options.outHeight就是我们想要的宽和高了
        scanBitmap = BitmapFactory.decodeFile(path, options);
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        // 设置inSimpleSize属性，实现图片缩放，节约内存
        options.inSampleSize = sampleSize;
        // 重新设置inJustDecodeBounds=false，获取图片真正的宽高
        options.inJustDecodeBounds = false;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        int[] data = new int[scanBitmap.getWidth() * scanBitmap.getHeight()];
        scanBitmap.getPixels(data, 0, scanBitmap.getWidth(), 0, 0, scanBitmap.getWidth(), scanBitmap.getHeight());
        // 新建一个RGBLuminanceSource对象，将图片宽高及图片像素数据传给此对象
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(scanBitmap.getWidth(), scanBitmap.getHeight(), data);
        // 将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        // 初始化解析对象
        QRCodeReader reader = new QRCodeReader();
        // 开始解析
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (NotFoundException e) {
            Log.e("错误提示", "NotFoundException");
        } catch (ChecksumException e) {
            Log.e("错误提示", "ChecksumException");
        } catch (FormatException e) {
            Log.e("错误提示", "FormatException");
        }
        return result;
    }

    /**
     * 扫描二维码图片
     */
    public static Result scanQRPic(Bitmap bitmap) {
        Hashtable<DecodeHintType, String> hints = new Hashtable();
        // 设置二维码内容的编码
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        int[] data = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(data, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        // 新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), data);
        // 将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        // 初始化解析对象
        QRCodeReader reader = new QRCodeReader();
        // 开始解析
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (NotFoundException e) {
            Log.e("错误提示", "NotFoundException");
        } catch (ChecksumException e) {
            Log.e("错误提示", "ChecksumException");
        } catch (FormatException e) {
            Log.e("错误提示", "FormatException");
        }
        return result;
    }
}
