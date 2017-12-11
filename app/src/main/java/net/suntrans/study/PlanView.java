package net.suntrans.study;

import android.content.Context;
import android.graphics.Canvas;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Looney on 2017/11/27.
 * Des:
 */

public class PlanView extends SurfaceView implements SurfaceHolder.Callback {

    private PlanThread mThread;
    private WeakReference<PlanView> mThisWeakRef = new WeakReference<PlanView>(this);

    public PlanView(Context context) {
        this(context, null);
    }

    public PlanView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        mThread = new PlanThread(mThisWeakRef);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    static class PlanThread extends Thread {
        private WeakReference<PlanView> mThisWeakRef;
        private boolean mExited;

        public PlanThread(WeakReference<PlanView> mThisWeakRef) {
            this.mThisWeakRef = mThisWeakRef;
            this.mExited = false;
        }

        @Override
        public void run() {

        }
    }
}
