package com.silion.samples;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Lock Pattern view
 */
public class LockPatternView extends View {
    private Circle[][] mCircle = new Circle[3][3];
    private List<Circle> mCircleList = new ArrayList<>();

    private float mOffsetsX;
    private float mOffsetsY;

    private Paint mPaint = new Paint();
    private boolean mIsInit = false;
    private boolean mIsSelect = false;
    private boolean mIsFinish = false;

    private Circle mMovingCircle;

    public OnPatternChangeListener mOnPatternChangeListener;

    public LockPatternView(Context context) {
        super(context);
    }

    public LockPatternView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockPatternView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mIsInit) {
            initCircle();
        }
        circle2Canvas(canvas);
        if (mCircleList != null && mCircleList.size() > 1) {
            Circle startCircle;
            Circle endCircle = null;
            for (int i = 1; i < mCircleList.size(); i++) {
                startCircle = mCircleList.get(i - 1);
                endCircle = mCircleList.get(i);
                line2Canvas(canvas, startCircle, endCircle);
            }

            if (mMovingCircle != null) {
                line2Canvas(canvas, endCircle, mMovingCircle);
            }
        }
    }

    /**
     * draw line 2 canvas
     *
     * @param canvas used to draw
     */
    private void line2Canvas(Canvas canvas, Circle startCircle, Circle endCircle) {
        if (startCircle.mState == Circle.STATE_PRESSED) {
            mPaint.setColor(getResources().getColor(R.color.x));
            mPaint.setStrokeWidth(20f);
            canvas.drawLine(startCircle.x, startCircle.y, endCircle.x, endCircle.y, mPaint);
        } else if (endCircle.mState == Circle.STATE_ERROR) {
            mPaint.setColor(getResources().getColor(R.color.w));
            mPaint.setStrokeWidth(20f);
            canvas.drawLine(startCircle.x, startCircle.y, endCircle.x, endCircle.y, mPaint);
        }
        mPaint.reset();
    }

    /**
     * draw circle 2 canvas
     *
     * @param canvas used to draw
     */
    public void circle2Canvas(Canvas canvas) {
        for (int i = 0; i < mCircle.length; i++) {
            for (int j = 0; j < mCircle[i].length; j++) {
                Circle circle = mCircle[i][j];
                if (circle.mState == Circle.STATE_PRESSED) {
                    mPaint.setColor(getResources().getColor(R.color.x));
                    canvas.drawCircle(circle.x, circle.y, circle.r, mPaint);
                } else if (circle.mState == Circle.STATE_ERROR) {
                    mPaint.setColor(getResources().getColor(R.color.w));
                    canvas.drawCircle(circle.x, circle.y, circle.r, mPaint);
                } else if (circle.mState == Circle.STATE_NORMAL) {
                    mPaint.setColor(getResources().getColor(R.color.g));
                    canvas.drawCircle(circle.x, circle.y, circle.r, mPaint);
                }
            }
        }
    }

    /**
     * init points
     */
    public void initCircle() {
        int lenght;

        // 1. get layout width and height
        int width = getWidth();
        int height = getHeight();

        // 2. calculator offsets
        if (width > height) {
            mOffsetsX = (width - height) / 2;
            lenght = height;
        } else {
            mOffsetsY = (height - width) / 2;
            lenght = width;
        }

        // 3. calculator point x, y
        mCircle[0][0] = new Circle(mOffsetsX + lenght / 4, mOffsetsY + lenght / 4, 65f);
        mCircle[0][1] = new Circle(mOffsetsX + lenght / 2, mOffsetsY + lenght / 4, 65f);
        mCircle[0][2] = new Circle(mOffsetsX + (lenght - lenght / 4), mOffsetsY + lenght / 4, 65f);

        mCircle[1][0] = new Circle(mOffsetsX + lenght / 4, mOffsetsY + lenght / 2, 65f);
        mCircle[1][1] = new Circle(mOffsetsX + lenght / 2, mOffsetsY + lenght / 2, 65f);
        mCircle[1][2] = new Circle(mOffsetsX + (lenght - lenght / 4), mOffsetsY + lenght / 2, 65f);

        mCircle[2][0] = new Circle(mOffsetsX + lenght / 4, mOffsetsY + (lenght - lenght / 4), 65f);
        mCircle[2][1] = new Circle(mOffsetsX + lenght / 2, mOffsetsY + (lenght - lenght / 4), 65f);
        mCircle[2][2] = new Circle(mOffsetsX + (lenght - lenght / 4), mOffsetsY + (lenght - lenght / 4), 65f);

        // 4. init index
        int index = 1;
        for (Circle[] circles : mCircle) {
            for (Circle circle : circles) {
                circle.mIndex = index++;
            }
        }

        mIsInit = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Circle touchCircle = null;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mIsFinish = false;
                resetCircleList();
                touchCircle = checkSelectCircle(x, y);
                if (touchCircle != null) {
                    mIsSelect = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mIsSelect) {
                    touchCircle = checkSelectCircle(x, y);
                    if (touchCircle == null) {
                        mMovingCircle = new Circle(x, y);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (mIsSelect) {
                    mIsFinish = true;
                    mIsSelect = false;
                    mMovingCircle = null;
                }
                break;
            }
            default:
                break;
        }
        // avoid repested select circle2Canvas
        if (mIsSelect && !mIsFinish && touchCircle != null) {
            if (repeatedCircle(touchCircle)) {
                mMovingCircle = touchCircle;
            } else {
                touchCircle.mState = Circle.STATE_PRESSED;
                mCircleList.add(touchCircle);
            }
        }

        //finish
        if (mIsFinish) {
            if (mCircleList.size() < 4) {
                errorPattern();
                mOnPatternChangeListener.onPatternChange(null);
            } else {
                String pattern = "";
                for (Circle circle : mCircleList) {
                    pattern = pattern + circle.mIndex;
                }
                mOnPatternChangeListener.onPatternChange(pattern);
            }
        }

        //update view
        postInvalidate();
        return true;
    }

    public boolean repeatedCircle(Circle circle) {
        if (mCircleList.contains(circle)) {
            return true;
        } else {
            return false;
        }
    }

    public void resetCircleList() {
        if (mCircleList == null || mCircleList.size() == 0) {
            return;
        }
        for (Circle circle : mCircleList) {
            circle.mState = Circle.STATE_NORMAL;
        }
        mCircleList.clear();
    }

    public void errorPattern() {
        for (Circle circle : mCircleList) {
            circle.mState = Circle.STATE_ERROR;
        }
    }

    public Circle checkSelectCircle(float movingX, float movingY) {
        for (int i = 0; i < mCircle.length; i++) {
            for (int j = 0; j < mCircle[i].length; j++) {
                Circle circle = mCircle[i][j];
                if (circle.inCircle(movingX, movingY)) {
                    return circle;
                }
            }
        }
        return null;
    }

    /**
     * Circle
     */
    public static class Circle {
        public static int STATE_NORMAL = 0;
        public static int STATE_PRESSED = 1;
        public static int STATE_ERROR = 2;

        public float x, y, r;
        public int mIndex = 0;
        public int mState = 0;

        public Circle() {
        }

        public Circle(float x, float y) {
            this(x, y, 0f);
        }

        public Circle(float x, float y, float r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        public boolean inCircle(float movingX, float movingY) {
            double d = Math.sqrt((x - movingX) * (x - movingX) + (y - movingY) * (y - movingY));
            return d <= r;
        }
    }

    /**
     * Pattern Listener
     */
    public interface OnPatternChangeListener {
        void onPatternChange(String pattern);
    }

    /**
     * set pattern listener
     * @param listener
     */
    public void setOnPatternChangeListener(OnPatternChangeListener listener) {
        if (listener != null) {
            mOnPatternChangeListener = listener;
        }
    }
}
