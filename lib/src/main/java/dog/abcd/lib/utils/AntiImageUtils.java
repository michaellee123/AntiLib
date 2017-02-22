package dog.abcd.lib.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;

/**
 * <b>图片处理</b><br>
 * 对图片进行尺寸和质量压缩以及base64编码
 * 
 * @Company RZQC
 * @author Michael Lee<br>
 *         <b> create at </b>2016-1-28 上午11:19:34
 * @Mender Michael Lee<br>
 *         <b> change at </b>2016-1-28 上午11:19:34
 */
public class AntiImageUtils {

	private AntiImageUtils() {

	}

	/**
	 * 缩放大小，不改变尺寸（压缩质量）
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 512) { // 循环判断如果压缩后图片是否大于1M,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 缩放尺寸并减小大小（压缩尺寸后并在压缩质量）
	 * 
	 * @param image
	 * @param ww
	 *            宽度
	 * @param hh
	 *            高度
	 * @return
	 */
	public static Bitmap comp(Bitmap image, float ww, float hh) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// newOpts.inPreferredConfig = Config.RGB_565;// 降低图片从ARGB888到RGB565
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 把图片转换成流输出成字符串
	 * 
	 * @param bitmap
	 */
	public static String disposeImage(Bitmap bitmap) {
		if (bitmap == null) {
			AntiLog.e("disposeImage", "NULL!");
			return "";
		}
		ByteArrayOutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			/*
			 * 读取和压缩图片
			 */
			bitmap.compress(CompressFormat.JPEG, 50, outputStream);
			String imgBase64 = new String(Base64.encode(outputStream.toByteArray(), Base64.DEFAULT));
			return imgBase64;
		} catch (Exception e) {
			AntiLog.e("disposeImage", e.toString());
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			return "";
		}
	}

	/**
	 * base64转为bitmap
	 * 
	 * @param base64Data
	 *            base64的字符串
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}
}
