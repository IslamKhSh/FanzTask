package com.fanz.task.details;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.alexvasilkov.gestures.views.GestureImageView;

public class CustomImageView extends GestureImageView {

    private Camera camera;
    private Matrix matrix;
    private Canvas canvas;

    public CustomImageView(Context context) {
        super(context);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        camera = new Camera();
        matrix = new Matrix();
    }

    public void rotateView() {
        camera.save();
        //camera.rotateY(rotate);
        camera.getMatrix(matrix);
        matrix.preTranslate(0, -getHeight() / 2f);
        matrix.postTranslate(0, getHeight() / 2f);
        camera.restore();

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.concat(matrix);

        Bitmap bitmap = Bitmap.createBitmap(getDrawable().getIntrinsicWidth(),
                getDrawable().getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        canvas.drawBitmap(bitmap, matrix, null);

        canvas.restore();
    }
}


