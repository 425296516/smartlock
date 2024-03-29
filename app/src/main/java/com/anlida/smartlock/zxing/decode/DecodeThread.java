package com.anlida.smartlock.zxing.decode;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.anlida.smartlock.zxing.QRCodeScanActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * This thread does all the heavy lifting of decoding the images.
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class DecodeThread extends Thread {

	public static final String BARCODE_BITMAP = "barcode_bitmap";

	public static final String BARCODE_SCALED_FACTOR = "barcode_scaled_factor";

	private final QRCodeScanActivity activity;

	private final Hashtable<DecodeHintType, Object> hints;

	private Handler handler;

	private final CountDownLatch handlerInitLatch;

	static final Collection<BarcodeFormat> PRODUCT_FORMATS;
	public static final Collection<BarcodeFormat> ONE_D_FORMATS;
	static final Collection<BarcodeFormat> QR_CODE_FORMATS = EnumSet
			.of(BarcodeFormat.QR_CODE);
	static final Collection<BarcodeFormat> DATA_MATRIX_FORMATS = EnumSet
			.of(BarcodeFormat.DATA_MATRIX);
	static {
		PRODUCT_FORMATS = EnumSet.of(BarcodeFormat.UPC_A, BarcodeFormat.UPC_E,
				BarcodeFormat.EAN_13, BarcodeFormat.EAN_8,
				BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED);
		ONE_D_FORMATS = EnumSet.of(BarcodeFormat.CODE_39,
				BarcodeFormat.CODE_93, BarcodeFormat.CODE_128,
				BarcodeFormat.ITF, BarcodeFormat.CODABAR);
		ONE_D_FORMATS.addAll(PRODUCT_FORMATS);
	}

	DecodeThread(QRCodeScanActivity activity,
                 Collection<BarcodeFormat> decodeFormats, String characterSet,
                 ResultPointCallback resultPointCallback) {

		this.activity = activity;
		handlerInitLatch = new CountDownLatch(1);

		hints = new Hashtable<DecodeHintType, Object>(3);

		if (decodeFormats == null || decodeFormats.isEmpty()) {
			decodeFormats = new Vector<BarcodeFormat>();
			decodeFormats.addAll(ONE_D_FORMATS);
			decodeFormats.addAll(QR_CODE_FORMATS);
			decodeFormats.addAll(DATA_MATRIX_FORMATS);
		}
		hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

		if (characterSet != null) {
			hints.put(DecodeHintType.CHARACTER_SET, characterSet);
		}
		hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK,
				resultPointCallback);
		Log.i("DecodeThread", "Hints: " + hints);
	}

	Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(activity, hints);
		handlerInitLatch.countDown();
		Looper.loop();
	}

	public void setDecodeFormats(Vector<BarcodeFormat> paramVector) {
		hints.put(DecodeHintType.POSSIBLE_FORMATS, paramVector);
		((DecodeHandler) handler).setHints(hints);
	}
}
