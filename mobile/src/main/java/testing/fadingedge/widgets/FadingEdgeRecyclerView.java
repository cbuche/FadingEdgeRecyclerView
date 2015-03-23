package testing.fadingedge.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import testing.fadingedge.R;

/**
 * Double Encore
 * 3/23/15
 */
public class FadingEdgeRecyclerView extends RecyclerView {

    private int mFadingEdgeHeight;
    private Paint mGradientPaint;

    private Bitmap mFadingEdgeBitmap;
    private Canvas mFadingEdgeCanvas;

    private final int mOpaque = 0xFF000000;
    private final int mTransparent = Color.TRANSPARENT;

    public FadingEdgeRecyclerView(Context context) {
        this(context, null);
    }

    public FadingEdgeRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FadingEdgeRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if(attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FadingEdgeRecyclerView, 0, defStyle);
            mFadingEdgeHeight = array.getDimensionPixelSize(R.styleable.FadingEdgeRecyclerView_edgeHeight, 0);
            array.recycle();
        }

        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            updateBitmap();
            updateShader();
        }
    }

    private void updateBitmap() {
        if (mFadingEdgeHeight == 0) return;

        if (mFadingEdgeBitmap != null) mFadingEdgeBitmap.recycle();
        mFadingEdgeBitmap = null;

        mFadingEdgeBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mFadingEdgeBitmap.setHasAlpha(true);
        mFadingEdgeCanvas = new Canvas(mFadingEdgeBitmap);
    }

    private void updateShader() {
        Shader shader = new LinearGradient(0, Math.round(mFadingEdgeHeight * 0.2f), 0, mFadingEdgeHeight, mTransparent, mOpaque, Shader.TileMode.CLAMP);
        mGradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGradientPaint.setShader(shader);
        mGradientPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mFadingEdgeBitmap.eraseColor(Color.TRANSPARENT);
        super.dispatchDraw(mFadingEdgeCanvas);

        canvas.drawBitmap(mFadingEdgeBitmap, 0, 0, null);
        canvas.drawRect(0, 0, getWidth(), mFadingEdgeHeight, mGradientPaint);
    }
}
