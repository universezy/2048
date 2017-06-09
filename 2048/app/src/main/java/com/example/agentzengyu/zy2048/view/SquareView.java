package com.example.agentzengyu.zy2048.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.agentzengyu.zy2048.entity.Square;

import java.util.ArrayList;

/**
 * Created by Agent ZengYu on 2017/6/9.
 */

public class SquareView extends View {
    private ArrayList<Square> squares = new ArrayList<>();
    private Paint paint;
    private RectF rectF;
    private int colorOutside = Color.parseColor("#ffffff");
    private int colorBackground = Color.parseColor("#9a9a9a");
    private final int padding = 10;
    private final int textStrokeWidth = 3;
    private int width, height;
    private int viewSide, squareSide;
    private int startX, startY;

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setBackgroundColor(colorBackground);
        setParams();
    }

    private void setParams() {
        paint = new Paint();
        paint.setAntiAlias(true);
        rectF = new RectF();
        width = this.getWidth();
        height = this.getHeight();
        viewSide = Math.min(width, height);
        squareSide = (viewSide - 5 * padding) / 4;
        startX = (width - viewSide) / 2;
        startY = height - viewSide;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectF.left = 0;
        rectF.right = width;
        rectF.top = 0;
        rectF.bottom = height;
        paint.setColor(colorOutside);
        canvas.drawRect(rectF, paint);

        if (squares.size() == 16) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    Square square = squares.get(y * 4 + x);
                    int number = square.getNumber();
                    if (number == 0) return;

                    //background
                    rectF.left = startX + padding * (x + 1) + x * squareSide;
                    rectF.right = rectF.left + squareSide;
                    rectF.top = startY + padding * (y + 1) + y * squareSide;
                    rectF.bottom = rectF.top + squareSide;
                    paint.setColor(square.getBackgroundColor());
                    canvas.drawRect(rectF, paint);

                    //text
                    String text = "" + number;
                    int textHeight = squareSide / 3;
                    int textWidth = (int) paint.measureText(text, 0, text.length());
                    paint.setTextSize(textHeight);
                    paint.setStrokeWidth(textStrokeWidth);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(square.getTextColor());
                    canvas.drawText(text, rectF.left + (squareSide - textWidth) / 2, rectF.top + (squareSide - textHeight) / 2, paint);
                }
            }
        }
    }

    public void setSquares(ArrayList<Square> squares) {
        if (squares == null) return;
        this.squares.clear();
        this.squares.addAll(squares);
        this.invalidate();
    }
}
