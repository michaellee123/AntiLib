package dog.abcd.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * <b>Title</b><br>
 * Description
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/3/1 下午 16:41
 */
public class DemoCircleLayout extends RelativeLayout {
    public DemoCircleLayout(Context context) {
        super(context);
    }

    public DemoCircleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoCircleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight() / 4, Bitmap.Config.ARGB_8888);
        Canvas circleCanvas = new Canvas();
        circleCanvas.setBitmap(circleBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        circleCanvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2, paint);
        circleCanvas.save();
        circleCanvas.restore();
        canvas.drawBitmap(circleBitmap, 0, 0, null);
        canvas.drawCircle(getMeasuredWidth() / 7 * 2, getMeasuredHeight() / 5 * 2, getMeasuredWidth() / 15, paint);
        canvas.drawCircle(getMeasuredWidth() / 7 * 5, getMeasuredHeight() / 5 * 2, getMeasuredWidth() / 15, paint);
        canvas.drawRect(getMeasuredWidth() / 8 * 3, getMeasuredHeight() / 4 * 3, getMeasuredWidth() / 8 * 5, getMeasuredHeight() / 4 * 3 + 5, paint);
        canvas.save();
        canvas.restore();
    }
}
