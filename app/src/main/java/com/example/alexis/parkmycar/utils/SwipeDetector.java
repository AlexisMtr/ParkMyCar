package com.example.alexis.parkmycar.utils;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class SwipeDetector implements View.OnTouchListener
{
    public static enum Action {
        LR, // Left to Right
        RL, // Right to Left
        TB, // Top to bottom
        BT, // Bottom to Top
        None // when no action was detected
    }

    private static final String logTag = "SWIPE";
    private static final int HORIZONTAL_MIN_DISTANCE = 100, VERTICAL_MIN_DISTANCE = 500;
    private float downX, downY, upX, upY;
    private Action mSwipeDetected = Action.None;

    private View v;
    protected LinearLayout leftLayout, rightLayout, mainLayout;

    public SwipeDetector() {}
    public SwipeDetector(View v)
    {
        this();
        this.v = v;
    }
    public SwipeDetector(View v, LinearLayout leftLayout, LinearLayout rightLayout)
    {
        this(v);
        this.leftLayout = leftLayout;
        this.rightLayout = rightLayout;
    }

    public SwipeDetector(View v, LinearLayout leftLayout, LinearLayout rightLayout, LinearLayout mainLayout)
    {
        this(v, leftLayout, rightLayout);
        this.mainLayout = mainLayout;
    }

    public void setLeftLayout(LinearLayout layout) { this.leftLayout = layout; }

    public void setRightLayout(LinearLayout layout) { this.rightLayout = layout; }

    public boolean swipeDetected() {
        return mSwipeDetected != Action.None;
    }

    public Action getAction() {
        return mSwipeDetected;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                mSwipeDetected = Action.None;
                return true; // allow other events like Click to be processed
            }
            case MotionEvent.ACTION_MOVE: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // horizontal swipe detection
                if (Math.abs(deltaX) > HORIZONTAL_MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        Log.i(logTag, "Swipe Left to Right");
                        mSwipeDetected = Action.LR;
                        actionLeftToRight();
                        return true;
                    }
                    if (deltaX > 0) {
                        Log.i(logTag, "Swipe Right to Left");
                        mSwipeDetected = Action.RL;
                        actionRightToLeft();
                        return true;
                    }
                }
                else
                {
                    // vertical swipe detection
                    if (Math.abs(deltaY) > VERTICAL_MIN_DISTANCE)
                    {
                        // top or down
                        if (deltaY < 0)
                        {
                            Log.i(logTag, "Swipe Top to Bottom");
                            mSwipeDetected = Action.TB;
                            return false;
                        }
                        if (deltaY > 0)
                        {
                            Log.i(logTag, "Swipe Bottom to Top");
                            mSwipeDetected = Action.BT;
                            return false;
                        }
                    }
                }
                return true;
            }
            default:
                return true;
        }
    }

    protected void actionLeftToRight()
    {
        if(this.rightLayout != null && this.rightLayout.getVisibility() == View.VISIBLE)
        {
            this.rightLayout.setVisibility(View.GONE);
            this.mainLayout.setVisibility(View.VISIBLE);
            return;
        }

        if(this.leftLayout == null)
            return;

        this.leftLayout.setVisibility(View.VISIBLE);
        this.mainLayout.setVisibility(View.GONE);
    }

    protected void actionRightToLeft()
    {
        if(this.leftLayout != null && this.leftLayout.getVisibility() == View.VISIBLE)
        {
            this.leftLayout.setVisibility(View.GONE);
            this.mainLayout.setVisibility(View.VISIBLE);
            return;
        }

        if(this.rightLayout == null)
            return;

        this.rightLayout.setVisibility(View.VISIBLE);
        this.mainLayout.setVisibility(View.GONE);
    }

    protected void displayHiddenItem_Right()
    {
        if(this.leftLayout == null)
            return;

        this.leftLayout.setVisibility(View.VISIBLE);
    }

    protected void hideVisibleItem_Right()
    {
        if(this.leftLayout == null)
            return;

        this.leftLayout.setVisibility(View.GONE);
    }
}
