package com.anlida.smartlock.zxing.camera;

import android.hardware.Camera;
import android.util.Log;


/**
 * 该类用于检测手机上摄像头的个数，如果有两个摄像头，则取背面的摄像头
 */
public final class OpenCameraInterface {

	private static final String TAG = OpenCameraInterface.class.getName();

	private OpenCameraInterface() {
	}

	/**
	 * Opens a rear-facing camera with {@link Camera#open(int)}, if one exists,
	 * or opens camera 0.
	 */
	public static Camera open() {

		int numCameras = Camera.getNumberOfCameras();
		if (numCameras == 0) {
			Log.w(TAG, "No cameras!");
			return null;
		}

		int index = 0;
		while (index < numCameras) {
			Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
			Camera.getCameraInfo(index, cameraInfo);
			// CAMERA_FACING_BACK：手机背面的摄像头
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
				break;
			}
			index++;
		}

		Camera camera;
		if (index < numCameras) {
			Log.i(TAG, "Opening camera #" + index);
			camera = Camera.open(index);
		} else {
			Log.i(TAG, "No camera facing back; returning camera #0");
			camera = Camera.open(0);
		}

		return camera;
	}

}
