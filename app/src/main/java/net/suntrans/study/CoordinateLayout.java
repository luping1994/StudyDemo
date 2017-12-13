package net.suntrans.study;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.OverScroller;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import net.suntrans.study.data.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Looney on 2017/12/13.
 * Des:
 */

public class CoordinateLayout extends FrameLayout implements View.OnClickListener {

    private String datas;
    private RequestManager imageLoader;
    private OverScroller mScroller;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ValueAnimator animator;

    public CoordinateLayout(@NonNull Context context) {
        this(context, null);
    }

    public CoordinateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoordinateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        imageLoader = Glide.with(context);
        mScroller = new OverScroller(context);
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), mScaleGestureListener);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        Data.ContainerBean container = (Data.ContainerBean) mContainerView.getTag();
        int containerHeight = container.height;
        int containerWidth = container.width;
        //根据底图大小和控件大小计算比率
        float widthRadio = (float) measuredWidth / (float) containerWidth;
        float heightRadio = (float) measuredHeight / (float) containerHeight;

        for (View v :
                mElements) {

            Data.ElementsBean e = (Data.ElementsBean) v.getTag();

            int elementWidth = (int) (e.width * widthRadio);
            int elementHeight = (int) (e.height * heightRadio);

            int eleSize = Math.max(elementWidth, elementHeight);

            LayoutParams imgLp = new LayoutParams(eleSize, eleSize);
            v.setLayoutParams(imgLp);

            int imgLeft = (int) (e.x * widthRadio);
            int imgTop = (int) (e.y * heightRadio);

            int imgRight = imgLeft + eleSize;
            int imgBottom = imgTop + eleSize;


            v.layout(imgLeft, imgTop, imgRight, imgBottom);
        }
    }

    public String getDatas() {
        return datas;
    }

    private View mContainerView;
    private List<View> mElements = new ArrayList<>();

    public void setDatas(String datas) {
        if (mElements.size() != 0) {
            removeAllViews();
        }
        this.datas = datas;
        addBgAndElements(datas);
    }

    private void addBgAndElements(String datas) {
        mElements.clear();
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        Data data = JSON.parseObject(datas, Data.class);
        Data.ContainerBean container = data.container;
        int containerHeight = container.height;
        int containerWidth = container.width;

        //根据底图大小和控件大小计算比率
        float widthRadio = (float) measuredWidth / (float) containerWidth;
        float heightRadio = (float) measuredHeight / (float) containerHeight;

        //放置底图
        ImageView containerImg = new ImageView(this.getContext());
        containerImg.setScaleType(ImageView.ScaleType.FIT_XY);

        imageLoader.load(container.bgImage)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Drawable drawable = new BitmapDrawable(getResources(), resource);
                        setBackground(drawable);
                    }
                });
        containerImg.setTag(container);
        mContainerView = containerImg;
        containerImg.setOnClickListener(this);
//        addView(containerImg);


        //放置元素
        List<Data.ElementsBean> elements = data.elements;
        for (Data.ElementsBean e :
                elements) {

//            int elementWidth = (int) (e.width * widthRadio);
//            int elementHeight = (int) (e.height * heightRadio);
//            int eleSize = Math.max(elementWidth, elementHeight);
//            LayoutParams imgLp = new LayoutParams(eleSize, eleSize);

            ImageView img = new ImageView(this.getContext());
            containerImg.setScaleType(ImageView.ScaleType.FIT_XY);


//            img.setLayoutParams(imgLp);
            if (e.status) {
                imageLoader.load(e.openUrl)
                        .into(img);
            } else {
                imageLoader.load(e.closeUrl)
                        .into(img);
            }

            img.setOnClickListener(this);
            img.setTag(e);
            mElements.add(img);
            addView(img);
        }
    }


    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof Data.ElementsBean) {

            if (elementsClickListener != null) {
                elementsClickListener.onElementClick((Data.ElementsBean) tag);
            }
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
        void onElementClick(Data.ElementsBean data);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean retVal = mScaleGestureDetector.onTouchEvent(event);
        return retVal || super.onTouchEvent(event);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {

    }

    private final ScaleGestureDetector.SimpleOnScaleGestureListener mScaleGestureListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            System.out.println(detector.getScaleFactor());
            if (detector.getScaleFactor()<1.01&&detector.getScaleFactor()>0.99){
                return true;
            }
            mScaleFactor *=detector.getScaleFactor();

            mScaleFactor = Math.max(1f, Math.min(mScaleFactor, 5.0f));

            System.out.println(mScaleFactor);
            // 缩放view
            setScaleX(mScaleFactor);// x方向上缩小
            setScaleY(mScaleFactor);// y方向上缩小
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            // 一定要返回true才会进入onScale()这个函数
            return true;
        }


    };

    private ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (float) animation.getAnimatedValue();
            setScaleX(value);
            setScaleY(value);
        }
    };

}
