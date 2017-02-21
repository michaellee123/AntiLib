package dog.abcd.lib.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 
 * <b>自定义圆形图片框</b><br>
 * 头像类型圆形图片框
 * 
 * @Company RZQC
 * @author Michael Lee<br>
 *         <b> create at </b>2016年2月15日 上午9:19:30
 * @Mender ramon<br>
 *         <b> change at </b>2016年2月15日 上午9:19:30
 */
public class AstiCircleImageView extends ImageView {

	private static final Xfermode MASK_XFERMODE;
	// 显示图片
	private Bitmap mask;
	// 背景
	private Paint paint;
	// 边框粗细
	private int mBorderWidth = 3;
	// 边框颜色
	private int mBorderColor = Color.parseColor("#FFFFFF");

	private boolean useDefaultStyle = false;

	public static Xfermode getMaskXfermode() {
		return MASK_XFERMODE;
	}

	public Bitmap getMask() {
		return mask;
	}

	public Paint getPaint() {
		return paint;
	}

	public int getmBorderWidth() {
		return mBorderWidth;
	}

	public int getmBorderColor() {
		return mBorderColor;
	}

	public boolean isUseDefaultStyle() {
		return useDefaultStyle;
	}

	static {
		PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
		MASK_XFERMODE = new PorterDuffXfermode(localMode);
	}

	/**
	 * 构造方法
	 * 
	 * @param paramContext
	 */
	public AstiCircleImageView(Context paramContext) {
		super(paramContext);
	}

	public AstiCircleImageView(Context paramContext,
							   AttributeSet paramAttributeSet) {
		this(paramContext, paramAttributeSet, 0);
	}

	public AstiCircleImageView(Context paramContext,
							   AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}


	public void setUseDefaultStyle(boolean useDefaultStyle) {
		this.useDefaultStyle = useDefaultStyle;
	}

	/**
	 * 设置边框宽度和颜色
	 * @param borderWidth
	 * @param borderColor
     */
	public void setBorder(int borderWidth,int borderColor){
		this.mBorderColor = borderColor;
		this.mBorderWidth = borderWidth;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas paramCanvas) {
		if (useDefaultStyle) {
			super.onDraw(paramCanvas);
			return;
		}
		final Drawable localDrawable = getDrawable();
		if (localDrawable == null)
			return;
		if (localDrawable instanceof NinePatchDrawable) {
			return;
		}
		if (this.paint == null) {
			final Paint localPaint = new Paint();
			localPaint.setFilterBitmap(false);
			localPaint.setAntiAlias(true);
			localPaint.setXfermode(MASK_XFERMODE);
			this.paint = localPaint;
		}
		final int width = getWidth();
		final int height = getHeight();
		/** 保存layer */
		int layer = paramCanvas.saveLayer(0.0F, 0.0F, width, height, null, 31);
		super.onDraw(paramCanvas);
		if ((this.mask == null) || (this.mask.isRecycled())) {
			this.mask = createOvalBitmap(width, height);
		}
		/** 将bitmap画到canvas上面 */
		paramCanvas.drawBitmap(this.mask, 0.0F, 0.0F, this.paint);
		/** 将画布复制到layer上 */
		paramCanvas.restoreToCount(layer);
		drawBorder(paramCanvas, width, height);
	}

	/**
	 * 绘制最外面的边框
	 * 
	 * @param canvas
	 * @param width
	 * @param height
	 */
	private void drawBorder(Canvas canvas, final int width, final int height) {
		if (mBorderWidth == 0) {
			return;
		}
		final Paint mBorderPaint = new Paint();
		mBorderPaint.setStyle(Paint.Style.STROKE);
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(mBorderColor);
		mBorderPaint.setStrokeWidth(mBorderWidth);
		/**
		 * 坐标x：view宽度的一般 坐标y：view高度的一般 半径r：因为是view宽度的一半-border
		 */
		canvas.drawCircle(width >> 1, height >> 1, (width >> 1) - mBorderWidth,
				mBorderPaint);
		canvas = null;
	}

	/**
	 * 获取一个bitmap，目的是用来承载drawable;
	 * <p>
	 * 将这个bitmap放在canvas上面承载，并在其上面画一个椭圆(其实也是一个圆，因为width=height)来固定显示区域
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public Bitmap createOvalBitmap(final int width, final int height) {
		Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
		Bitmap localBitmap = Bitmap.createBitmap(width, height, localConfig);
		Canvas localCanvas = new Canvas(localBitmap);
		Paint localPaint = new Paint();
		final int padding = mBorderWidth > 2 ? mBorderWidth - 2 : 0;
		/**
		 * 设置椭圆的大小(因为椭圆的最外边会和border的最外边重合的，如果图片最外边的颜色很深，有看出有棱边的效果，所以为了让体验更加好，
		 * 让其缩进padding px)
		 */
		RectF localRectF = new RectF(padding, padding, width - padding, height
				- padding);
		localCanvas.drawOval(localRectF, localPaint);
		return localBitmap;
	}
}
