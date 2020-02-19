package com.cs4sample.authentication.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ImageViewCompat;

public class ProportionalImageView extends AppCompatImageView {

    public ProportionalImageView(Context context) {
        super(context);
    }

    public ProportionalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProportionalImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        try {
            Drawable d = getDrawable();
            int w = View.MeasureSpec.getSize(widthMeasureSpec);
            int h = w * d.getIntrinsicHeight() / d.getIntrinsicWidth();

            if (d != null) {
                setMeasuredDimension(w, h);
            }
        }catch (Exception e){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
