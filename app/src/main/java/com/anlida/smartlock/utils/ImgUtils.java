package com.anlida.smartlock.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片相关
 */
public class ImgUtils {

	/**
	 * bitmap转base64
	 *
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {

		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				// 加密后的字符串带有"\n"，导致在进行字符串比较的时候出现错误
				// 解决办法是将 Base64.DEFAULT 替换成 Base64.NO_WRAP
				result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * base64转为bitmap
	 * @param base64Data
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

    /**
     * 将bitmap图片保存到本地,之前用分线程，现在不用，因为需要加载完后，才能复制给对应的控件
     * 小头像
     * @param mBitmap
     * @param bitName
     */
    public static void saveBitmap(final Bitmap mBitmap, final String bitName, Context context) {
        String s = bitName.substring(bitName.lastIndexOf("/") + 1);
		FileOutputStream out = null;
		// 获取扩展SD卡设备状态
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				// 获取扩展存储设备的文件目录
				String path = Environment.getExternalStorageDirectory().getPath() + "/headimg";
				File f = new File(path);
				if (!f.exists()) {
					try {
						// 创建文件夹
						f.mkdirs();
					} catch (Exception e) {
					}
				}
				File dir = new File(path + "/" + s);
				if (!dir.exists()) {
					try {
						// 在指定的文件夹中创建文件
						dir.createNewFile();
					} catch (Exception e) {
					}
				}

				out = new FileOutputStream(dir);
				mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

				out.flush();
				out.close();
				Log.e("上传的小头像", "已保存到本地");

				//保存图片后发送广播通知更新数据库
				Uri uri = Uri.fromFile(f);
				context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将bitmap图片保存到本地,之前用分线程，现在不用，因为需要加载完后，才能复制给对应的控件
	 * 大头像
	 * @param mBitmap
	 * @param bitName
	 */
	public static void saveBigBitmap(final Bitmap mBitmap, final String bitName, Context context) {
		String s = bitName.substring(bitName.lastIndexOf("/") + 1);
		FileOutputStream out = null;
		// 获取扩展SD卡设备状态
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				// 获取扩展存储设备的文件目录
				String path = Environment.getExternalStorageDirectory().getPath() + "/headimg/big";
				File f = new File(path);
				if (!f.exists()) {
					try {
						// 创建文件夹
						f.mkdirs();
					} catch (Exception e) {
					}
				}
				File dir = new File(path + "/" + s);
				if (!dir.exists()) {
					try {
						// 在指定的文件夹中创建文件
						dir.createNewFile();
					} catch (Exception e) {
					}
				}

				out = new FileOutputStream(dir);
				mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

				out.flush();
				out.close();
				Log.e("上传的大头像", "已保存到本地");

				//保存图片后发送广播通知更新数据库
				Uri uri = Uri.fromFile(f);
				context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public static void onLoadImage(final URL bitmapUrl, final OnLoadImageListener onLoadImageListener){
		final Handler handler = new Handler(){
			public void handleMessage(Message msg){
				onLoadImageListener.onLoadImage((Bitmap) msg.obj, null);
			}
		};
		new Thread(new Runnable(){

			@Override
			public void run() {
				URL imageUrl = bitmapUrl;
				try {
					HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
					InputStream inputStream = conn.getInputStream();
					Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					Message msg = new Message();
					msg.obj = bitmap;
					handler.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}).start();

	}

	/**
	 * 检查文件是否存在
	 */
	private static String checkDirPath(String dirPath) {
		if (TextUtils.isEmpty(dirPath)) {
			return "";
		}
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dirPath;
	}

	public interface OnLoadImageListener{
		void onLoadImage(Bitmap bitmap, String bitmapPath);
	}

}
