package net.suntrans.study;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import net.suntrans.study.data.PlanData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Looney on 2017/12/11.
 * Des:
 */

public class PlanView2 extends View {
    private static final String TAG = "PlanView2";

    private List<PlanData> mDatas;


    private int mScreenWidth;
    private int mScreenHeight;
    private Paint mPaint;


    private Bitmap contentBitmap;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;

    private float mScaleFactor = 1.0f;


    public PlanView2(Context context) {

        this(context, null);
    }

    public PlanView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlanView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), mScaleGestureListener);
        mGestureDetector = new GestureDetector(getContext(), mGestureListener);

        mDatas = new ArrayList<>();

        DisplayMetrics mMetrics = getContext().getResources().getDisplayMetrics();
        mScreenWidth = mMetrics.widthPixels;
        mScreenHeight = mMetrics.heightPixels;
        Log.i(TAG, "width=" + mScreenWidth);
        Log.i(TAG, "height=" + mScreenHeight);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean retVal = mScaleGestureDetector.onTouchEvent(event);
        retVal = mGestureDetector.onTouchEvent(event) || retVal;

//        switch (event.getActionMasked()){
//            case MotionEvent.ACTION_DOWN:
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
        return retVal || super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        if (contentBitmap == null) {
            return;
        }
        if (mDatas == null) {
            return;
        }
        if (mDatas.size() == 0) {
            return;
        }
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);

        //获取底图大小
        int contentWidth = contentBitmap.getWidth();
        int contentHeight = contentBitmap.getHeight();

        //获取控件实际大小
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();


        //根据底图大小和控件大小计算比率
        float widthRadio = (float) measuredWidth / (float) contentWidth;
        float heightRadio = (float) measuredHeight / (float) contentHeight;

        Rect src1 = new Rect(0, 0, contentBitmap.getWidth(), contentBitmap.getHeight());
        RectF dust1 = new RectF(0, 0, measuredWidth, measuredHeight);
        canvas.drawBitmap(contentBitmap, src1, dust1, mPaint);


        for (PlanData d :
                mDatas) {
            float left = d.x * widthRadio;
            float top = d.y * heightRadio;
            float right = (d.x + d.width) * widthRadio;
            float bottom = (d.y + d.height) * heightRadio;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), d.resID);

            Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            RectF dust = new RectF(left, top, right, bottom);

            RectF temp = new RectF(dust);
            d.rectF = temp;
            canvas.drawBitmap(bitmap, src, dust, mPaint);
        }
        canvas.restore();
    }

    public List<PlanData> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<PlanData> mDatas) {
        this.mDatas = mDatas;
        invalidate();
    }

    public Bitmap getContentBitmap() {
        return contentBitmap;
    }

    public void setContentBitmap(Bitmap contentBitmap) {
        this.contentBitmap = contentBitmap;
    }


    private final GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            if (mDatas == null)
                return true;
            float x = e.getX();
            float y = e.getY();
            PlanData data = null;
            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).rectF.contains(x, y)){
                    data = mDatas.get(i);
                    if (elementsClickListener!=null){
                        elementsClickListener.onElementClick(data);
                    }
                  return true;
                }
            }

            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    };

    private final ScaleGestureDetector.SimpleOnScaleGestureListener mScaleGestureListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            System.out.println("onScale="+mScaleFactor);
            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();
            return true;
        }

    };

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }


    private onElementsClickListener elementsClickListener;

    public onElementsClickListener getElementsClickListener() {
        return elementsClickListener;
    }

    public void setElementsClickListener(onElementsClickListener elementsClickListener) {
        this.elementsClickListener = elementsClickListener;
    }

    public interface onElementsClickListener {
        void onElementClick(PlanData data);
    }

}
