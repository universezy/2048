package com.example.agentzengyu.zy2048.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.agentzengyu.zy2048.app.ZY2048Application;
import com.example.agentzengyu.zy2048.entity.Square;

import java.util.ArrayList;

/**
 * Created by Agent ZengYu on 2017/6/9.
 */

/**
 * 方块界面视图
 */
public class SquareView extends View {
    private ZY2048Application application = null;
    private ArrayList<Square> squares = new ArrayList<>();
    private int colorOutside = Color.parseColor("#ffffff");
    private int colorBackground = Color.parseColor("#9a9a9a");
    private final int padding = 20;
    private final int textStrokeWidth = 3;
    private float downX = 0, downY = 0;

    public SquareView(Context context){
        super(context);
        application = (ZY2048Application) context.getApplicationContext();
        setBackgroundColor(colorBackground);
    }

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        application = (ZY2048Application) context.getApplicationContext();
        setBackgroundColor(colorBackground);
    }

    public SquareView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        application = (ZY2048Application) context.getApplicationContext();
        setBackgroundColor(colorBackground);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        RectF rectF = new RectF();

        float width = this.getWidth();
        float height = this.getHeight();
        float viewSide = Math.min(width, height);
        float squareSide = (viewSide - 5 * padding) / 4;
        float startX = (width - viewSide) / 2;
        float startY = height - viewSide;

        //整个视图背景
        rectF.set(0, 0, width, height);
        paint.setColor(colorOutside);
        canvas.drawRect(rectF, paint);

        //方块界面背景
        rectF.set(startX, startY, startX + viewSide, startY + viewSide);
        paint.setColor(colorBackground);
        canvas.drawRect(rectF, paint);

        //小方块
        if (squares.size() == 16) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    Square square = squares.get(y * 4 + x);
                    int number = square.getNumber();

                    //background
                    rectF.left = startX + padding * (x + 1) + x * squareSide;
                    rectF.right = rectF.left + squareSide;
                    rectF.top = startY + padding * (y + 1) + y * squareSide;
                    rectF.bottom = rectF.top + squareSide;
                    paint.setColor(square.getBackgroundColor());
                    canvas.drawRect(rectF, paint);

                    //text
                    if (number == 0) continue;
                    String text = String.valueOf(number);
                    float textHeight = squareSide / 3;
                    float textWidth = new Paint().measureText(text);
                    paint.setTextSize(textHeight);
                    paint.setStrokeWidth(textStrokeWidth);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(square.getTextColor());
                    canvas.drawText(text, rectF.left + (squareSide - textWidth) / 2 - textWidth, rectF.top + (squareSide + textHeight) / 2, paint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (squares.size() != 16) return false;
        int eventCode = event.getAction();
        switch (eventCode) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getX();
                float upY = event.getY();
                float deltaX = upX - downX;
                float deltaY = upY - downY;
                //水平方向
                if (Math.abs(deltaX) > 200 && Math.abs(deltaY) < 100) {
                    //向右
                    if (deltaX > 0) {
                        application.getService().onRight();
                    }
                    //向左
                    else if (deltaX < 0) {
                        application.getService().onLeft();
                    }
                }
                //垂直方向
                else if (Math.abs(deltaY) > 200 && Math.abs(deltaX) < 100) {
                    //向下
                    if (deltaY > 0) {
                        application.getService().onBottom();
                    }
                    //向上
                    else if (deltaY < 0) {
                        application.getService().onTop();
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 设置方块数据
     *
     * @param squares 方块集
     */
    public void setSquares(ArrayList<Square> squares) {
        if (squares == null) return;
        this.squares.clear();
        this.squares.addAll(squares);
    }
}
