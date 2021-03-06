package com.faisal.basemodule.util;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTouchListener implements View.OnTouchListener {
    public final GestureDetector gestureDetector;
    Context context;
    onSwipeListener onSwipeListener;
    public OnSwipeTouchListener(Context ctx, View mainView, onSwipeListener onSwipeListener) {
        gestureDetector = new GestureDetector(ctx, new GestureListener());
        mainView.setOnTouchListener(this);
        context = ctx;
        this.onSwipeListener=onSwipeListener;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
    public class GestureListener extends
            GestureDetector.SimpleOnGestureListener {
        public static final int SWIPE_THRESHOLD = 100;
        public static final int SWIPE_VELOCITY_THRESHOLD = 100;
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }
    void onSwipeRight() {
        //Toast.makeText(context, "Swiped Right", Toast.LENGTH_SHORT).show();
        this.onSwipeListener.swipeRight();
    }
    void onSwipeLeft() {
        //Toast.makeText(context, "Swiped Left", Toast.LENGTH_SHORT).show();
        this.onSwipeListener.swipeLeft();
    }
    void onSwipeTop() {
        // Toast.makeText(context, "Swiped Up", Toast.LENGTH_SHORT).show();
        this.onSwipeListener.swipeTop();
    }
    void onSwipeBottom() {
        //Toast.makeText(context, "Swiped Down", Toast.LENGTH_SHORT).show();
        this.onSwipeListener.swipeBottom();
    }
    public  interface onSwipeListener {
        void swipeRight();
        void swipeTop();
        void swipeBottom();
        void swipeLeft();
    }
}

